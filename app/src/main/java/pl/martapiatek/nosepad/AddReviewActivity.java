package pl.martapiatek.nosepad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.martapiatek.nosepad.model.Review;

public class AddReviewActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button btnAddReview;
    private FirebaseUser user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_review:

                    return true;
                case R.id.navigation_show_review:
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnAddReview = (Button) findViewById(R.id.btnAddReview);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //     mDatabase.child("reviews/").child(user.getUid()).child(review.getBrand()).child(review.getFragrance()).child("description").setValue(review.getDescription());
                //   mDatabase.child("reviews/").child(user.getUid()).child(review.getBrand()).child(review.getFragrance()).child("rating").setValue(review.getRating());


                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("reviews");

                String key = mDatabase.push().getKey();

                Review review = new Review("Prada", "Candy", "Cytrynka", "Fajniutkie", 4);


                mDatabase.child(user.getUid()).child(key).setValue(review);

                review = new Review("Prada22", "Candy222", "Cytrynka222", "Fajniutkie222", 22);

                key = mDatabase.push().getKey();
                mDatabase.child(user.getUid()).child(key).setValue(review);

                //     Review review2 = new Review("Prada", "Candy2222","Cytrynk232323a","Fajniutki32323e", 2);


              /*  mDatabase.child("reviews/").child(user.getUid()).child(review2.getBrand()).child(review2.getFragrance()).child("description").setValue(review2.getDescription());
                mDatabase.child("reviews/").child(user.getUid()).child(review2.getBrand()).child(review2.getFragrance()).child("rating").setValue(review2.getRating())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddReviewActivity.this, "zapisano", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddReviewActivity.this, "błąd", Toast.LENGTH_SHORT).show();
                            }
                        });

*/

                final ArrayList<String> keys = new ArrayList<>();

                mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.i("REVIEW DS", "KEY: " + ds.getKey());
                            keys.add(ds.getKey());
                            Log.i("REVIEW", "keys size" + keys.size());
                        }

                        //Review reviewRead = dataSnapshot.getValue(Review.class);

                        //  Log.i("REVIEW", "User name: " + reviewRead.getBrand() + ", email " + reviewRead.getFragrance());
                        //   Log.i("REVIEW", "KEY: " + dataSnapshot.getKey());

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.i("REVIEW", "Failed to read value.", error.toException());
                    }
                }); // koniec onClick Litenera

                for (String s : keys) {
                    mDatabase.child(user.getUid()).child(s).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Review reviewRead = dataSnapshot.getValue(Review.class);

                            Log.i("REVIEW", "Brand: " + reviewRead.getBrand() + ", Fragrance " + reviewRead.getFragrance()
                                    + ", Description " + reviewRead.getDescription() + ", notes " + reviewRead.getNotes()
                                    + ", Rating " + reviewRead.getRating());
                            //   Log.i("REVIEW", "KEY: " + dataSnapshot.getKey());

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.i("REVIEW", "Failed to read value.", error.toException());
                        }
                    });
                }

            }
        });


    }

}

package pl.martapiatek.nosepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.martapiatek.nosepad.adapter.ReviewAdapter;
import pl.martapiatek.nosepad.adapter.ReviewSectionAdapter;
import pl.martapiatek.nosepad.model.Review;

public class AllReviewsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<Review> reviews;
    private ReviewAdapter adapter;
    private ListView listView;

    private ArrayList<Object> listItem;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_review:
                    Intent intentAddReview = new Intent(AllReviewsActivity.this, AddReviewActivity.class);
                    startActivity(intentAddReview);

                    return true;
                case R.id.navigation_show_review:
                    //    Intent intentShowReviews = new Intent(AllReviewsActivity.this, AllReviewsActivity.class);
                    //   startActivity(intentShowReviews);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("reviews").child(user.getUid());

        reviews = new ArrayList<>();
        listItem = new ArrayList<>();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("CHILD", "onChildAdded:" + dataSnapshot.getKey());
                Review review = dataSnapshot.getValue(Review.class);


                if (reviews.size() == 0 || !reviews.get(reviews.size() - 1).getBrand().equals(review.getBrand())) {
                    listItem.add(new String(review.getBrand()));
                }

                reviews.add(review);


                listItem.add(review);

                Log.i("CHILD", "Brand: " + review.getBrand() + ", Fragrance " + review.getFragrance()
                        + ", Description " + review.getDescription() + ", notes " + review.getNotes()
                        + ", Rating " + review.getRating());

                Log.i("CHILD", "size: " + reviews.size());
                Log.i("CHILD", "size object: " + listItem.size());

                listView = findViewById(R.id.review_list_view);
                //   adapter = new ReviewAdapter(AllReviewsActivity.this, reviews);
                //   listView.setAdapter(adapter);

                listView.setAdapter(new ReviewSectionAdapter(AllReviewsActivity.this, listItem));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("CHILD", "onChildChanged:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("CHILD", "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("CHILD", "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

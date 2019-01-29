package pl.martapiatek.nosepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.martapiatek.nosepad.model.Review;

public class AddReviewActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button btnAddReview;
    private FirebaseUser user;
    private AutoCompleteTextView autoCompleteBrand, autoCompleteFragrance;
    private EditText edtDescription;
    private RatingBar ratingBar;
    private MultiAutoCompleteTextView multiAutoCompleteNotes;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_review:
                    //    Intent intentAddReview = new Intent(MainActivity.this, AddReviewActivity.class);
                    //   startActivity(intentAddReview);

                    return true;
                case R.id.navigation_show_review:
                    Intent intentShowReviews = new Intent(AddReviewActivity.this, AllReviewsActivity.class);
                    startActivity(intentShowReviews);

                    return true;
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
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        autoCompleteBrand = (AutoCompleteTextView) findViewById(R.id.autoCompleteBrand);
        autoCompleteFragrance = (AutoCompleteTextView) findViewById(R.id.autoCompleteFragrance);
        multiAutoCompleteNotes = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteNotes);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("reviews").child(user.getUid());


        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = mDatabase.push().getKey();

                Review review = new Review(
                        autoCompleteBrand.getText().toString(),
                        autoCompleteFragrance.getText().toString(),
                        multiAutoCompleteNotes.getText().toString(),
                        edtDescription.getText().toString(),
                        ratingBar.getRating());

                mDatabase.child(key).setValue(review)
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

            }
        });


    }

}

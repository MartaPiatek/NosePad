package pl.martapiatek.nosepad;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Dialog dialog, dialog2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<Review> reviews;
    private ReviewAdapter adapter;
    private ListView listView;

    private ArrayList<Object> listItem;


    public AllReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllReviewsFragment newInstance(String param1, String param2) {
        AllReviewsFragment fragment = new AllReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_all_reviews, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("reviews").child(user.getUid());

        reviews = new ArrayList<>();
        listItem = new ArrayList<>();

        listView = view.findViewById(R.id.review_list_view);

        mDatabase.orderByChild("brand").addChildEventListener(new ChildEventListener() {
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


                //  adapter = new ReviewAdapter(AllReviewsActivity.this, reviews);
                //   listView.setAdapter(adapter);


                //  Collections.sort(listItem);

                listView.setAdapter(new ReviewSectionAdapter(getContext(), listItem));

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Toscik", Toast.LENGTH_SHORT).show();

                dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
                dialog.setContentView(R.layout.read_review);

                Button btnYes = dialog.findViewById(R.id.btnEditReview);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        // dialog.dismiss();
                        dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
                        dialog.setContentView(R.layout.edit_review);

                        dialog.show();
                    }
                });


                //   dialog.setCancelable(false);
                dialog.show();
            }
        });
        return view;
    }

}

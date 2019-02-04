package pl.martapiatek.nosepad;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import pl.martapiatek.nosepad.model.PerfumeData;
import pl.martapiatek.nosepad.model.Review;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button btnAddReview;
    private FirebaseUser user;
    private AutoCompleteTextView autoCompleteBrand, autoCompleteFragrance;
    private EditText edtDescription;
    private RatingBar ratingBar;
    private MultiAutoCompleteTextView multiAutoCompleteNotes;


    public AddReviewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddReviewFragment newInstance(String param1, String param2) {
        AddReviewFragment fragment = new AddReviewFragment();
        Bundle args = new Bundle();
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


        View view = inflater.inflate(R.layout.fragment_add_review, container, false);

        btnAddReview = view.findViewById(R.id.btnAddReview);
        edtDescription = view.findViewById(R.id.edtDescription);
        autoCompleteBrand = view.findViewById(R.id.autoCompleteBrand);
        autoCompleteFragrance = view.findViewById(R.id.autoCompleteFragrance);
        multiAutoCompleteNotes = view.findViewById(R.id.multiAutoCompleteNotes);
        ratingBar = view.findViewById(R.id.ratingBar);

        PerfumeData p = new PerfumeData();
        ArrayList<String> brands = p.getBrands();
        ArrayAdapter<String> adapterBrands = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, brands);

        autoCompleteBrand.setThreshold(1);
        autoCompleteBrand.setAdapter(adapterBrands);

        ArrayList<String> notes = p.getNotes();
        ArrayAdapter<String> adapterNotes = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, notes);

        multiAutoCompleteNotes.setThreshold(1);
        multiAutoCompleteNotes.setAdapter(adapterNotes);
        multiAutoCompleteNotes.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


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
                                //  Toast.makeText(AddReviewActivity.this, "zapisano", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //  Toast.makeText(AddReviewActivity.this, "błąd", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        return view;
    }


}

package pl.martapiatek.nosepad.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PerfumeData {
    private DatabaseReference mDatabase;


    public ArrayList<String> getNotes() {
        final ArrayList<String> notes = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("notes");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String b = dataSnapshot.getValue(String.class);
                notes.add(b);


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
        return notes;
    }

    public ArrayList<String> getBrands() {
        final ArrayList<String> brands = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("brands");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String b = dataSnapshot.getValue(String.class);
                brands.add(b);
                Log.i("TAAAAG", "size: " + brands.size());
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
        return brands;
    }
}

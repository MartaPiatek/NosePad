package pl.martapiatek.nosepad;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FirstActivity extends AppCompatActivity implements Runnable {

    private Button btnLog, btnGuest, btnClose;
    private Handler handler;
    private Dialog splashDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        showSplashScreen();
        handler = new Handler();
        AsyncTask.execute(this);

        btnClose = findViewById(R.id.btnClose);
        btnGuest = findViewById(R.id.btnGuest);
        btnLog = findViewById(R.id.btnLog);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intentLog);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //uploadBrands();
        //uploadNotes();

    }

    private void uploadBrands() {
        mDatabase = FirebaseDatabase.getInstance().getReference("brands");
        ArrayList<String> brands = new ArrayList<>();
        InputStream input = getResources().openRawResource(R.raw.brands);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(input)
        );
        String line = "";
        try {

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                brands.add(line);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("BRAND", "Size: " + brands.size());

        for (String brand : brands) {
            String key = mDatabase.push().getKey();
            mDatabase.child(key).setValue(brand);
            Log.i("BRAND", "name: " + brand);
        }
    }

    private void uploadNotes() {
        mDatabase = FirebaseDatabase.getInstance().getReference("notes");
        ArrayList<String> notes = new ArrayList<>();
        InputStream input = getResources().openRawResource(R.raw.notes);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(input)
        );
        String line = "";
        try {

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                notes.add(line);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("Notes", "Size: " + notes.size());

        for (String note : notes) {
            String key = mDatabase.push().getKey();
            mDatabase.child(key).setValue(note);
            Log.i("Notes", "name: " + note);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dismissSplashScreen();
    }

    private void showSplashScreen() {
        splashDialog = new Dialog(this, R.style.splash_screen);
        splashDialog.setContentView(R.layout.activity_splash);
        splashDialog.setCancelable(false);
        splashDialog.show();
    }

    private void dismissSplashScreen() {
        if (splashDialog != null) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }

    @Override
    public void run() {
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dismissSplashScreen();
                                }
                            }, 3000
        );
    }


    public ArrayList<String> readBrands(String filename) throws IOException {
        String file = filename;
        ArrayList<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }
}

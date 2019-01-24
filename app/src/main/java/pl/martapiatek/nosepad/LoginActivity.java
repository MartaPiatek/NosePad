package pl.martapiatek.nosepad;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements Runnable {

    private FirebaseAuth mAuth;
    private Button btnLogin;
    private EditText edtEmail, edtPassword;

    private Handler handler;
    private Dialog splashDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        showSplashScreen();
        handler = new Handler();
        AsyncTask.execute(this);


        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtEmail.setText("martusia.piatek@gmail.com");
                edtPassword.setText("marta123");

                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();


                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);


                                    //  btnLogin.setEnabled(true);
                                } else {

                                    //Toast.makeText(LoginActivity.this, "Błąd logowania", Toast.LENGTH_LONG).show();


                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast,
                                            (ViewGroup) findViewById(R.id.toast_layout_root));
                                    layout.setBackground(getDrawable(R.drawable.toast_background));


                                    TextView text = (TextView) layout.findViewById(R.id.text);
                                    text.setText("Błąd logowania! \nPodałeś nieprawidłowe dane!");
                                    text.setTextSize(20);

                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 20);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();

                                }


                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
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
}

package pl.martapiatek.nosepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogin;
    private EditText edtEmail, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        edtEmail.setText("martusia.piatek@gmail.com");
        edtPassword.setText("marta123");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



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

                                    Toast.makeText(LoginActivity.this, "Błąd logowania", Toast.LENGTH_LONG).show();


                               /*     LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast,
                                            (ViewGroup) findViewById(R.id.toast_layout_root));
                                    layout.setBackground(getDrawable(R.drawable.toast_background));



                                    MyToast myToast = new MyToast(getApplicationContext(), "Błąd logowania! \nPodałeś nieprawidłowe dane!", layout);
                                    myToast.show();
                                    /*
                                    TextView text = (TextView) layout.findViewById(R.id.text);
                                    text.setText("Błąd logowania! \nPodałeś nieprawidłowe dane!");
                                    text.setTextSize(20);

                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 20);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
*/
                                }


                            }
                        });
            }
        });

    }


}

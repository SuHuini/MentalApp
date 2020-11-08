package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText emailSignIn, passSignIn;
    Button signinBtn;


    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        emailSignIn = (TextInputEditText)findViewById(R.id.signInEmail);
        passSignIn = (TextInputEditText)findViewById(R.id.signInpassword);
        signinBtn = (Button)findViewById(R.id.signInBtn);

        signinBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                rootNode = FirebaseDatabase.getInstance();
                databaseSignIn = rootNode.getReference("client");

                final String email = emailSignIn.getText().toString();
                final String password = passSignIn.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                     //updateUI(user);

                                    Toast.makeText(SignInActivity.this, "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignInActivity.this, UserActivity.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the u
                                    //
                                    //
                                    // ser.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });


    }
    public void signUp(View v){
        Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(i);
    }
}

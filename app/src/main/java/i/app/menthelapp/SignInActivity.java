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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import i.app.menthelapp.CounsellorPkg.CounsellorUserActivity;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText emailSignIn, passSignIn;
    Button signinBtn;

    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseSignIn;
    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), UserActivity.class));
//            finish();
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        databaseSignIn = FirebaseDatabase.getInstance().getReference("client");

        emailSignIn = (TextInputEditText)findViewById(R.id.signInEmail);
        passSignIn = (TextInputEditText)findViewById(R.id.signInpassword);
        signinBtn = (Button)findViewById(R.id.signInBtn);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                databaseSignIn = rootNode.getReference("client");

                final String email = emailSignIn.getText().toString();
                final String password = passSignIn.getText().toString();
                mAuth.signInWithEmailAndPassword(emailSignIn.getText().toString(), passSignIn.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authresult) {
                        Log.d(TAG, "signInWithEmail:success");
                        //AuthResult authResult;
                        FirebaseUser user = mAuth.getInstance().getCurrentUser();
                        checkUserAccessLevel(authresult.getUser().getUid());

                        Toast.makeText(SignInActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void checkUserAccessLevel(String uid) {
                DocumentReference databaseClient = firestore.collection("Users").document(uid);
                databaseClient.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "signInWithEmail:success" + documentSnapshot.getData());

                        if (documentSnapshot.getString("IsAdmin") != null) {
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            finish();
                        }
                        if (documentSnapshot.getString("IsUser") != null) {
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
                            finish();
                        }
                        if (documentSnapshot.getString("IsCounsellor") != null) {
                            startActivity(new Intent(getApplicationContext(), CounsellorUserActivity.class));
                            finish();
                        }
                    }
                });
            }

        });
        }
        public void signUp(View v) {
            Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
              startActivity(i);
               }
    }



//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnSuccessListener((new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
//                                    AuthResult authResult;
//                                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
//                                    checkUserAccessLevel(.getUser().getUid());
//
//                                    Toast.makeText(SignInActivity.this, "Authentication successful.",
//                                            Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(SignInActivity.this, UserActivity.class);
//                                    startActivity(i);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    //updateUI(null);
//                                }
//
//                                // ...
//                            }
//                        });
//
//            }
//        });





//
//
//                                    if (user != null) {
//                                            // Name, email address, and profile photo Url
//                                            String name = user.getDisplayName();
//                                            String email = user.getEmail();
//                                            //Uri photoUrl = user.getPhotoUrl();
//
//                                            // Check if user's email is verified
//                                            boolean emailVerified = user.isEmailVerified();
//
//                                            // The user's ID, unique to the Firebase project. Do NOT use this value to
//                                            // authenticate with your backend server, if you have one. Use
//                                            // FirebaseUser.getIdToken() instead.
//
//                                            // String uid = user.getUid();
//                                            }
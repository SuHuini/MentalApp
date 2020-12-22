package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText fname, lname, uname, phone, emailclient, passclient;
    Button signUpButton;
    //ImageView img;

    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseClient;
    private FirebaseAuth mAuth;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        databaseClient = FirebaseDatabase.getInstance().getReference("client");
        fname =  (TextInputEditText)findViewById(R.id.clientFirstName);
        lname = (TextInputEditText) findViewById(R.id.clientLastName);
        uname =  (TextInputEditText)findViewById(R.id.clientUsername);
//        phone = (EditText) findViewById(R.id.clientPhoneNumber);
        emailclient = (TextInputEditText) findViewById(R.id.clientEmail);
        passclient = (TextInputEditText) findViewById(R.id.clientpassword);

        //img = (ImageView) findViewById(R.id.imageView);

        signUpButton = (Button) findViewById(R.id.signUpBtn);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                //databaseClient = rootNode.getReference("client");
                //addClient();
                //Get all the values
                final String clientFname = fname.getText().toString();
                final String clientLname = lname.getText().toString();
                final String clientUname = uname.getText().toString();
                final String email = emailclient.getText().toString();
                final String password = passclient.getText().toString();

                //check if all fields are filled
                if (TextUtils.isEmpty(clientFname) || TextUtils.isEmpty(clientLname) || TextUtils.isEmpty(clientUname) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Fill in all fields", Toast.LENGTH_LONG).show();
                } //check if email address is accurate
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Valid email required", Toast.LENGTH_LONG).show();
                }
                else {
                    //register user
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        String id = databaseClient.push().getKey();
//                                        Client client = new Client(clientFname, clientLname, clientUname, email, password);
//                                        databaseClient.child(id).setValue(client);
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        DocumentReference df = db.collection("Users").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("First Name", fname.getText().toString());
                                        userInfo.put("Last Name", lname.getText().toString());
                                        userInfo.put("User Name", uname.getText().toString());
                                        userInfo.put("Email", emailclient.getText().toString());
                                        userInfo.put("Password", passclient.getText().toString());
                                        userInfo.put("counName", " ");
                                        userInfo.put("counEmail", "");

                                        userInfo.put("IsUser", "1");

                                        df.set(userInfo);

                                        Intent i = new Intent(SignUpActivity.this, MatchActivity.class);
                                        startActivity(i);
                                        finish();
                                        //updateUI(null);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                    }
                                    // ...

                                }
                            });
                        }
            }
        });
    }
}












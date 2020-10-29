package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText fname, lname, uname, phone, emailclient, passclient;
    Button signUpButton;
    ImageView img;

    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseClient;
    private FirebaseAuth mAuth;



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

        img = (ImageView) findViewById(R.id.imageView);

        signUpButton = (Button) findViewById(R.id.signUpBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                databaseClient = rootNode.getReference("client");
                //addClient();
                //Get all the values
                final String clientFname = fname.getText().toString();
                final String clientLname = lname.getText().toString();
                final String clientUname = uname.getText().toString();
                final String email = emailclient.getText().toString();
                final String password = passclient.getText().toString();


                if (TextUtils.isEmpty(clientFname) || TextUtils.isEmpty(clientLname) || TextUtils.isEmpty(clientUname) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Fill in all fields", Toast.LENGTH_LONG).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Valid email required", Toast.LENGTH_LONG).show();
                }
                else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        String id = databaseClient.push().getKey();
                                        Client client = new Client(clientFname, clientLname, clientUname, email, password);
                                        databaseClient.child(id).setValue(client);
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

//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                rootNode = FirebaseDatabase.getInstance();
//                databaseClient = rootNode.getReference("client");
//                //addClient();
//
//                //Get all the values
//                final String clientFname = fname.getText().toString();
//                final String clientLname = lname.getText().toString();
//                final String clientUname = uname.getText().toString();
//               final  String clientEmail = email.getText().toString();
//                final String clientPassword = pass.getText().toString();
//
//        if (!TextUtils.isEmpty(clientFname) || !TextUtils.isEmpty(clientLname) || !TextUtils.isEmpty(clientUname) || !TextUtils.isEmpty(clientEmail)
//                || !TextUtils.isEmpty(clientPassword)) {
//            String id = databaseClient.push().getKey();
//            Client client = new Client(clientFname, clientLname, clientUname, clientEmail, clientPassword);
//            databaseClient.child(id).setValue(client);
//           Toast.makeText(getApplicationContext(), "Client added", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Fill in all fields", Toast.LENGTH_LONG).show();
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()){
//            Toast.makeText(getApplicationContext(), "Valid email required", Toast.LENGTH_LONG).show();
//        }
//            }
//        });
//
//        }






//            public void addClient() {
//                String clientFname = fname.getText().toString();
//                String clientLname = lname.getText().toString();
//                String clientUname = uname.getText().toString();
//                String clientEmail = email.getText().toString();
//                String clientPassword = pass.getText().toString();
//
//                if (!TextUtils.isEmpty(clientFname) || !TextUtils.isEmpty(clientLname) || !TextUtils.isEmpty(clientUname) ||
//                        !TextUtils.isEmpty(clientEmail) || !TextUtils.isEmpty(clientPassword)) {
//                    String id = databaseClient.push().getKey();
//                    Client client = new Client();
//                    databaseClient.child(id).setValue(client);
//                    Toast.makeText(this, "Client added", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "Fill in all fields", Toast.LENGTH_LONG).show();
//                }
//                if (!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()) {
//                    Toast.makeText(this, "Valid email required", Toast.LENGTH_LONG).show();
//                }
//            }













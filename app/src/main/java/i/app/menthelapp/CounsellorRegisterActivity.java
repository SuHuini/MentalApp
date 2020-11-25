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
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CounsellorRegisterActivity extends AppCompatActivity {
    TextInputEditText fname, lname, license, emailcoun, passcoun;
    Button signUpButton;
    CheckBox grief, anxiety, general, trauma, insomnia;
    //ImageView img;

    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseCoun;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        //databaseCoun = FirebaseDatabase.getInstance().getReference("client");
        fname =  (TextInputEditText)findViewById(R.id.clientFirstName);
        lname = (TextInputEditText) findViewById(R.id.clientLastName);
        license =  (TextInputEditText)findViewById(R.id.clientUsername);
//        phone = (EditText) findViewById(R.id.clientPhoneNumber);
        emailcoun = (TextInputEditText) findViewById(R.id.clientEmail);
        passcoun= (TextInputEditText) findViewById(R.id.clientpassword);

        grief = (CheckBox) findViewById(R.id.grief);
        anxiety = (CheckBox) findViewById(R.id.anxiety);
        general = (CheckBox) findViewById(R.id.general);
        insomnia = (CheckBox) findViewById(R.id.insomnia);
        trauma = (CheckBox) findViewById(R.id.trauma);

        //img = (ImageView) findViewById(R.id.imageView);

        signUpButton = (Button) findViewById(R.id.signUpBtn);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                //databaseClient = rootNode.getReference("client");
                //addClient();
                //Get all the values
                final String counFname = fname.getText().toString();
                final String counLname = lname.getText().toString();
                final String counLicense = license.getText().toString();
                final String email = emailcoun.getText().toString();
                final String password = passcoun.getText().toString();

                //check if all fields are filled
                if (TextUtils.isEmpty(counFname) || TextUtils.isEmpty(counLname) || TextUtils.isEmpty(counLicense) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(CounsellorRegisterActivity.this, "Fill in all fields", Toast.LENGTH_LONG).show();
                } //check if email address is accurate
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(CounsellorRegisterActivity.this, "Valid email required", Toast.LENGTH_LONG).show();
                }
                else {
                    //register user
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CounsellorRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        String id = databaseCoun.push().getKey();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(CounsellorRegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        DocumentReference df = db.collection("Users").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("First Name", fname.getText().toString());
                                        userInfo.put("Last Name", lname.getText().toString());
                                        userInfo.put("License No", license.getText().toString());
                                        userInfo.put("Email", emailcoun.getText().toString());
                                        userInfo.put("Password", passcoun.getText().toString());
                                        userInfo.put("IsCounsellor", "1");

                                        df.set(userInfo);

                                        Intent i = new Intent(CounsellorRegisterActivity.this, MatchActivity.class);
                                        startActivity(i);
                                        finish();
                                        //updateUI(null);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(CounsellorRegisterActivity.this, "Authentication failed.",
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
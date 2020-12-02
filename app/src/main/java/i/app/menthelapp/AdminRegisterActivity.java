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

public class AdminRegisterActivity extends AppCompatActivity {

    TextInputEditText fname, lname, emailadmin, passadmin;
    Button signUpButton;


    private static final String TAG = "EmailPassword";

    FirebaseDatabase rootNode;
    DatabaseReference databaseCoun;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();

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
        setContentView(R.layout.activity_admin_register);

        mAuth = FirebaseAuth.getInstance();

        //databaseCoun = FirebaseDatabase.getInstance().getReference("client");
        fname =  (TextInputEditText)findViewById(R.id.adminFirstName);
        lname = (TextInputEditText) findViewById(R.id.adminLastName);
        emailadmin = (TextInputEditText) findViewById(R.id.adminEmail);
        passadmin= (TextInputEditText) findViewById(R.id.adminPass);

        //img = (ImageView) findViewById(R.id.imageView);

        signUpButton = (Button) findViewById(R.id.adminBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Get all the values
                final String adminFname = fname.getText().toString();
                final String adminLname = lname.getText().toString();
                final String email = emailadmin.getText().toString();
                final String password = passadmin.getText().toString();


                //check if all fields are filled
                if (TextUtils.isEmpty(adminFname) || TextUtils.isEmpty(adminLname)  ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminRegisterActivity.this, "Fill in all fields", Toast.LENGTH_LONG).show();
                } //check if email address is accurate
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AdminRegisterActivity.this, "Valid email required", Toast.LENGTH_LONG).show();
                }
                else {
                    //register user
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AdminRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        //String id = databaseCoun.push().getKey();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(AdminRegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        DocumentReference dr = fStore.collection("Users").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("adminFName",fname.getText().toString());
                                        userInfo.put("adminSName", lname.getText().toString());
                                        userInfo.put("adminEmail", emailadmin.getText().toString());
                                        userInfo.put("adminPass", passadmin.getText().toString());
                                        userInfo.put("IsAdmin", "1");
                                        userInfo.put("adminId", dr.getId());

                                        dr.set(userInfo);

                                        Intent i = new Intent(AdminRegisterActivity.this, AdminsView.class);
                                        startActivity(i);
                                        finish();
                                        //updateUI(null);

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(AdminRegisterActivity.this, "Registration failed.",
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


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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    CheckBox depression, anxiety, general, drug;
    //ImageView img;

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
        setContentView(R.layout.activity_coun_register);
        mAuth = FirebaseAuth.getInstance();

        //databaseCoun = FirebaseDatabase.getInstance().getReference("client");
        fname =  (TextInputEditText)findViewById(R.id.adminFirstName);
        lname = (TextInputEditText) findViewById(R.id.adminLastName);
        license =  (TextInputEditText)findViewById(R.id.license);
//        phone = (EditText) findViewById(R.id.clientPhoneNumber);
        emailcoun = (TextInputEditText) findViewById(R.id.adminEmail);
        passcoun= (TextInputEditText) findViewById(R.id.adminPass);

        depression = (CheckBox) findViewById(R.id.depression);
        anxiety = (CheckBox) findViewById(R.id.anxiety);
        general = (CheckBox) findViewById(R.id.general);
        drug = (CheckBox) findViewById(R.id.drug_abuse);


        //img = (ImageView) findViewById(R.id.imageView);

        signUpButton = (Button) findViewById(R.id.signUpBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //rootNode = FirebaseDatabase.getInstance();
                //databaseClient = rootNode.getReference("client");
                //addClient();
                //Get all the values
                final String counFname = fname.getText().toString();
                final String counLname = lname.getText().toString();
                final String counLicense = license.getText().toString();
                final String email = emailcoun.getText().toString();
                final String password = passcoun.getText().toString();
               //String specialization;

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
                                        //String id = databaseCoun.push().getKey();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(CounsellorRegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                        DocumentReference dr = fStore.collection("Users").document(user.getUid());
                                        Map<String, Object> userInfo = new HashMap<>();
                                        userInfo.put("counFName",fname.getText().toString());
                                        userInfo.put("counSName", lname.getText().toString());
                                        userInfo.put("licenseNo", license.getText().toString());
                                        userInfo.put("counEmail", emailcoun.getText().toString());
                                        userInfo.put("counPassword", passcoun.getText().toString());
                                        userInfo.put("IsCounsellor", "1");
                                        userInfo.put("id", "");

                                        if(depression.isChecked()){
                                            userInfo.put("specialization", "Depression");
                                        }
                                        if(anxiety.isChecked()){
                                            userInfo.put("specialization", "Anxiety");
                                        }
                                        if(general.isChecked()){
                                            userInfo.put("specialization", "General");
                                        }
                                        if(drug.isChecked()){
                                            userInfo.put("specialization", "Drug Abuse Disorders");
                                        }
                                        dr.set(userInfo);

                                        //String name = counFname + "" + counLname;
                                        //writeSpecialization(name, email, specialization);


                                        Intent i = new Intent(CounsellorRegisterActivity.this, CounsellorView.class);
                                        startActivity(i);
                                        finish();
                                        //updateUI(null);

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(CounsellorRegisterActivity.this, "Registration failed.",
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
    private void writeSpecialization(String name, String email, String specialization) {
        //final String userId;
//        final String name = uname.getText().toString();
//        final String email = uemail.getText().toString();
//        final String password = pass.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        //DocumentReference df = db.collection("chatUsers").document(user.getUid());
        String userId = user.getUid();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("name", name);
        userInfo.put("email", email);
        //userInfo.put("password", password);
        userInfo.put("imageUrl", "default");

        //userInfo.put("IsUser", "1");
        fStore.collection("chatUsers").document(specialization)
                .collection(email)
                .add(userInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID" + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document");
                    }
                });



        Log.d(TAG, "saveuserdata");

    }

//public void onCheckboxClicked(View view){
//    String spec = null;
//    View view= null;
//    switch (view.getId()){
//        case R.id.depression:
//            spec = "depression";
//            break;
//        case anxiety.isChecked():
//            spec = "anxiety";
//            break;
//        case general.isChecked():
//            spec = "general";
//            break;
//        case R.id.radio_general:
//            spec = "drug abuse";
//            break;
//
//        default:
//            Toast.makeText(getBaseContext(),
//                    "Click a RadioButton", Toast.LENGTH_SHORT).show();
//            break;
//    }
//}
}

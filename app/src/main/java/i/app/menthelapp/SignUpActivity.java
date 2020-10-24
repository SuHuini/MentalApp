package i.app.menthelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText fname, lname, uname, phone, email, pass;
    Button signUpButton;

    FirebaseDatabase rootNode;
    DatabaseReference databaseClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseClient = FirebaseDatabase.getInstance().getReference("client");
        fname =  (TextInputEditText)findViewById(R.id.clientFirstName);
        lname = (TextInputEditText) findViewById(R.id.clientLastName);
        uname =  (TextInputEditText)findViewById(R.id.clientUsername);
//        phone = (EditText) findViewById(R.id.clientPhoneNumber);
        email = (TextInputEditText) findViewById(R.id.clientEmail);
        pass = (TextInputEditText) findViewById(R.id.clientpassword);

        signUpButton = (Button) findViewById(R.id.signUpBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                databaseClient = rootNode.getReference("client");

                //Get all the values
                String clientFname = fname.getText().toString();
                String clientLname = lname.getText().toString();
                String clientUname = uname.getText().toString();
                String clientEmail = email.getText().toString();
                String clientPassword = pass.getText().toString();


                Client client = new Client(clientFname, clientLname, clientUname, clientEmail, clientPassword);
                databaseClient.child(clientEmail).setValue(client);
            }
        });


    }

//    public void addClient () {
//        String clientFname = fname.getText().toString();
//        String clientLname = lname.getText().toString();
//        String clientUname = uname.getText().toString();
//        String clientEmail = email.getText().toString();
//
//        if (!TextUtils.isEmpty(clientFname) || !TextUtils.isEmpty(clientLname) || !TextUtils.isEmpty(clientUname) || !TextUtils.isEmpty(clientEmail)) {
//            String id = databaseClient.push().getKey();
//            Client client = new Client();
//            databaseClient.child(id).setValue(client);
//            Toast.makeText(this, "Client added", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_LONG).show();
//        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()){
//            Toast.makeText(this, "Valid email required", Toast.LENGTH_LONG).show();
//        }
//    }

}










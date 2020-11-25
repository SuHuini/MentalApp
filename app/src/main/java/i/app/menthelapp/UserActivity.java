package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserName";

    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;

    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        logOut = findViewById(R.id.button);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        final TextView name = (TextView) findViewById(R.id.textView);
        //loadUserInformation();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client = snapshot.getValue(Client.class);

                if (client != null){

                    String username = client.clientUName;

                    name.setText(username);
                }
            }

//            protected void onStart(){
//                super.onStart();
//                if(mAuth.Fireba            }
//seAuth.getInstance().getCurrentUser() == null)
//                {
//                    finish();
//                Intent i =  new Intent (UserActivity.this, SignInActivity);
//                startActivity(i);
//                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "get user failed");
                Toast.makeText(UserActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

    }
}

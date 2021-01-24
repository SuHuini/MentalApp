package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import i.app.menthelapp.chat.ChatActivity;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserName";

    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;

    TextView uname, uemail;
    CardView sess;
    Button logOut;

    private  FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        sess = findViewById(R.id.sessionsView);

        logOut = findViewById(R.id.button);

        uname = findViewById(R.id.user_name);
        uemail = findViewById(R.id.user_email);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();


        DocumentReference doc = fStore.collection("Users").document(user.getUid());
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                uname.setText(value.getString("User Name"));
                uemail.setText("Counsellor:" + value.getString("counName"));
                Log.d(TAG, "Retrieved!!!");
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        sess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SessionViewActivity.class));
            }
        });


    }
             public void chatMess(View v){
            Intent i = new Intent(UserActivity.this, ChatActivity.class);
            startActivity(i);

        }
//        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Client client = snapshot.getValue(Client.class);
//
//                if (client != null){
//
//                    String username = client.clientUName;
//
//                    name.setText(username);
//                }
//            }

//            protected void onStart(){
//                super.onStart();
//                if(mAuth.Fireba            }
//seAuth.getInstance().getCurrentUser() == null)
//                {
//                    finish();
//                Intent i =  new Intent (UserActivity.this, SignInActivity);
//                startActivity(i);
//                }


//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d(TAG, "get user failed");
//                Toast.makeText(UserActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
//            }
//        });


}

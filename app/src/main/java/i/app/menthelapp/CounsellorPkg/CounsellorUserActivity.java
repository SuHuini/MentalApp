package i.app.menthelapp.CounsellorPkg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import i.app.menthelapp.R;
import i.app.menthelapp.CounsellorPkg.CounsellorActivity;
import i.app.menthelapp.SignInActivity;
import i.app.menthelapp.chat.ChatActivity;

public class CounsellorUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();
    Button logOut;
    CardView clientsView, sessions, messaging;
    TextView name;
    final String TAG = "CounsellorUserActivity";

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_user);

        name = findViewById(R.id.user_name);

         clientsView = findViewById(R.id.clientsView);
         sessions = findViewById(R.id.sessionsView);
         messaging = findViewById(R.id.messagingView);


        logOut = findViewById(R.id.button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();
        uid = mAuth.getCurrentUser().getUid();

        fStore.collection("Users").document(user.getUid())
                .update("id", id);

        DocumentReference doc = fStore.collection("Users").document(user.getUid());
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //uname.setText(value.getString("clientName"));
                name.setText(value.getString("counFName"));
                String name = value.getString("counFName");
                Log.d(TAG, "Retrieved!!!" + name);
            }
        });

        clientsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getApplicationContext(), CounsellorActivity.class);
                startActivity(i);
            }
        });
        sessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SessionCounActivity.class);
                startActivity(i);
            }
        });
        messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        });


    }

}


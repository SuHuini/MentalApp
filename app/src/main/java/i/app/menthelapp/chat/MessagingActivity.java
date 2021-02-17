package i.app.menthelapp.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import i.app.menthelapp.Counsellor;
import i.app.menthelapp.MatchActivity;
import i.app.menthelapp.R;
import i.app.menthelapp.SessionActivity;

public class MessagingActivity extends AppCompatActivity {
        EditText countext;
        final String TAG = "MessagingActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private TextView counsellorName;

    private Context context;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        counsellorName = findViewById(R.id.counsellor_name);

        //user = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //uid = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();

        DocumentReference doc = fStore.collection("Users").document(user.getUid());
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                //uname.setText(value.getString("User Name"));
                //.setText("Counsellor:" + value.getString("counName"));
                email = value.getString("counEmail");
                                Log.d(TAG, "Retrieved!!!");
            }
        });
        fStore.collection("Users")
                .whereEqualTo( "IsCounsellor", 1 ).whereEqualTo("counEmail", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Counsellor coun = document.toObject(Counsellor.class);
                                //FirebaseUser user;
                               // User user1 = user.getUid();
                                String fname = coun.getCounFName();
                                String lname = coun.getCounSName();
                                final String id = coun.getId();

                                //String specialization  = coun.getSpecialization();
                                String data = "Name: " + fname + lname + "id" + id;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG,"Data"+ data);
                                String nm = fname + " " + lname;
                                counsellorName.setText(fname);
                                counsellorName.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        gotoChat(id);
                                    }
                                });






                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
    private void gotoChat(String id){

            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("userId", id);
            context.startActivity(intent);

    }

}

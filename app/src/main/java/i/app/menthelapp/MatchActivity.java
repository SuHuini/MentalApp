package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    String TAG = "DataRetrieved";
    Button next;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RadioGroup rg;
    RadioButton anxiety, trauma, general, grief;

    TextView issue;

    LinearLayout layout;
    TextView counName;
    TextView counEmail;
    Button sess;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;


    //private FirebaseAuth mAuth;

    private  FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onStart() {
        super.onStart();

        //hide views when
        layout.setVisibility(View.GONE);
        counName.setVisibility(View.GONE);
        counEmail.setVisibility(View.GONE);
        sess.setVisibility(View.GONE);

        next.setVisibility(View.GONE);
        issue.setVisibility(View.GONE);

        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
       // final FirebaseUser currentUser = mAuth.getCurrentUser();
        issue = (TextView) findViewById(R.id.issueText);
        rg = (RadioGroup)findViewById(R.id.rg_issue);
        anxiety =(RadioButton) findViewById(R.id.radio_anxiety);
        trauma = (RadioButton) findViewById(R.id.radio_trauma);
        general = (RadioButton) findViewById(R.id.radio_general);
        grief = (RadioButton)findViewById(R.id.radio_grief);

        next = (Button) findViewById(R.id.nextBtn);

        layout = findViewById(R.id.counDetails);
        counName = findViewById(R.id.match_counName);
        counEmail = findViewById(R.id.match_counEmail);
        sess = findViewById(R.id.session_activity);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();
        String issueU;


        anxiety.isChecked();
        trauma.isChecked();
        general.isChecked();
        grief.isChecked();
        rg.performClick();

        Button btn = (Button) findViewById(R.id.set_issue);
        btn.setOnClickListener(new View.OnClickListener() {
            //set issue to text
            @Override
            public void onClick(View view) {
                switch (rg.getCheckedRadioButtonId()){
                    case R.id.radio_anxiety:
                        issue.setText("Anxiety");
                        break;
                    case R.id.radio_grief:
                        issue.setText("Grief");
                        break;
                    case R.id.radio_general:
                        issue.setText("General");
                        break;
                    case R.id.radio_trauma:
                        issue.setText("Trauma");
                        break;
                    default:
                        Toast.makeText(getBaseContext(),
                        "Click a RadioButton", Toast.LENGTH_SHORT).show();
                        break;
                }
                issue.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }

        });

        // // //next = (Button) findViewById(R.id.nextBtn);

        next.setOnClickListener(new View.OnClickListener() {
            //perform match process
            @Override
            public void onClick(View view) {
                db.collection("Users")
                        .whereEqualTo( "specialization", issue.getText()).limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Counsellor coun = document.toObject(Counsellor.class);
                                        String fname = coun.getCounFName();
                                        String lname = coun.getCounSName();
                                        String email = coun.getCounEmail();
                                        //String specialization  = coun.getSpecialization();
                                        String data = "Name: " + fname + lname
                                                + "\nTitle: " + email + "\n Description: \n\n";
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG,"Data"+ data);
                                        String nm = fname + " " + lname;
                                        String em = email;

                                        String name = "Name:" + fname + " " + lname;
                                        String emailc = "Email:" + email;
                                        counName.setText(name);
                                        counEmail.setText(emailc);

                                        fStore.collection("Users").document(uid)
                                                .update("counName", nm,
                                                "counEmail", em);

                                        Log.d(TAG, "Retrieved!!!");
                                        layout.setVisibility(View.VISIBLE);
                                        counName.setVisibility(View.VISIBLE);
                                        counEmail.setVisibility(View.VISIBLE);
                                        sess.setVisibility(View.VISIBLE);
                                        sess.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent i = new Intent(MatchActivity.this, SessionActivity.class);
                                                startActivity(i);
                                            }
                                        });

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                                    }
                                }
        );


//
//       final DocumentReference doc = fStore.collection("Users").document(user.getUid());
//        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                Map<String, Object> userInfo = new HashMap<>();
//                userInfo.put("counName", counName.getText().toString());
//                doc.collection("Users").document(user.getUid()).set()
//                doc.set(userInfo);
//                Log.d(TAG, "Retrieved!!!");
//            }
//        });

    }

}

package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class MatchActivity extends AppCompatActivity {
    String TAG = "DataRetrieved";
    Button next;
            //coun, client;
//    private RecyclerView rec;
//    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RadioGroup rg;
    RadioButton anxiety, trauma, general, grief;
    //String issue;
    TextView issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        //coun = (Button)findViewById(R.id.counBtn);
        //client = (Button)findViewById(R.id.clientBtn);
        issue = findViewById(R.id.issueText);
        rg = findViewById(R.id.issue);
        anxiety = findViewById(R.id.anxiety);
        trauma = findViewById(R.id.trauma);
        general = findViewById(R.id.general);
        grief = findViewById(R.id.grief);

        anxiety.isChecked();
        trauma.isChecked();
        general.isChecked();
        grief.isChecked();

        issue.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                                            @Override
                                            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

                                                if (anxiety.isChecked()) {
                                                    issue.setText("Anxiety");
                                                }
                                                if (grief.isChecked()) {
                                                    issue.setText("Grief");
                                                }
                                                if (general.isChecked()) {
                                                    issue.setText("General");
                                                }
                                                if (trauma.isChecked()) {
                                                    issue.setText("Trauma");
                                                }

                                            }
                                        }

        );


        next = (Button) findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Users")
                        .whereEqualTo( "specialization", issue).limit(1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        Counsellor coun = document.toObject(Counsellor.class);
                                        //admin.setDocumentId(documentSnapshot.getId());
                                        //String id = document.getId();
                                        String fname = coun.getCounFName();
                                        String lname = coun.getCounSName();
                                        String email = coun.getCounEmail();
                                        //String specialization  = coun.getSpecialization();
                                        String data = "Name: " + fname + lname
                                                + "\nTitle: " + email + "\n Description: \n\n";
                                        //coun = document.getData();

                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        Log.d(TAG,"Data"+ data);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                Intent i = new Intent(MatchActivity.this, SessionActivity.class);
                startActivity(i);

                                    }
                                }
        );
    }

    public void buttonClicked(TextView text){
        issue = text;

        if (anxiety.isChecked()) {
            issue.setText("Anxiety");
        }
        if(grief.isChecked()){
            issue.setText("Grief");
        }
        if(general.isChecked()){
            issue.setText("General");
        }
        if(trauma.isChecked()){
            issue.setText("Trauma");
        }
    }


}

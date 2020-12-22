package i.app.menthelapp;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.LinkedBlockingDeque;

public class CounsellorView extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference databaseClient;
    private FirebaseAuth mAuth;
    //FirebaseFirestore db= FirebaseFirestore.getInstance();

    String TAG = "DataRetrieved";
    TextView counText;

    private RecyclerView counRecyclerView;
    private FirestoreRecyclerAdapter adapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference usersRef = db.collection("Users");
   // private DocumentReference ;
           // ApiFuture<> counRef = db.collection("Users").whereEqualTo("IsCounsellor", "1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //counText = findViewById(R.id.coun_text);
        counRecyclerView = findViewById(R.id.counRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CounsellorRegisterActivity.class));
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Query query = db.collection("Users")
                .whereEqualTo("IsCounsellor","1" );

        FirestoreRecyclerOptions<Counsellor> options = new FirestoreRecyclerOptions.Builder<Counsellor>()
                .setQuery(query, Counsellor.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Counsellor, CounsellorViewHolder>(options) {
            @NonNull
            @Override
            public CounsellorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
                return new CounsellorViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CounsellorViewHolder holder, int position, @NonNull Counsellor model) {
                holder.name.setText(model.getCounFName());
                holder.license.setText(model.getLicenseNo());
                holder.specialization.setText(model.getSpecialization());
                Log.d(TAG, "onBindViewHolderExecuted!!!");

            }
        };
        counRecyclerView.setHasFixedSize(true);
        counRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        counRecyclerView.setAdapter(adapter);
        }

        private class CounsellorViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView license;
        private TextView specialization;

        public CounsellorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            license = itemView.findViewById(R.id.userInfo1);
            specialization = itemView.findViewById(R.id.userInfo2);

            db.collection("Users")
                    .whereEqualTo("IsCounsellor","1" )
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Counsellor coun = document.toObject(Counsellor.class);
                                    //coun.setDocumentId(documentSnapshot.getId());
                                    String fname = coun.getCounFName();
                                    String lname = coun.getCounSName();
                                    String license = coun.getLicenseNo();
                                    String specialization  = coun.getSpecialization();
                                    String data = "Name: " + fname + lname
                                            + "\nLicense: " + license + "\n Specialization: " + specialization+ "\n\n";
                                    //coun = document.getData();

                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.d(TAG,"Data"+ data);
                                    //name.setText(data);

                                    //counText.setText(data);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onStart() {
        super.onStart();
        adapter.startListening();
        //query database



                //Firestore recycler options

//        counRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
//                if (e != null) {
//                    return;
//                }
//                String data = "";
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    Counsellor coun = documentSnapshot.toObject(Counsellor.class);
//                    //coun.setDocumentId(documentSnapshot.getId());#
//                    String fname = coun.getCounFName();
//                    String lname = coun.getCounSName();
//                    int license = coun.getLicenseNo();
//                    String specialization  = coun.getSpecialization();
//                    data += "Name: " + fname + lname
//                            + "\nTitle: " + license + "\n Description: " + specialization+ "\n\n";
//                }
//                textViewData.setText(data);
//            }
//        });
    }


}

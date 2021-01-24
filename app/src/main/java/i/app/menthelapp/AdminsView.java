package i.app.menthelapp;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class AdminsView extends AppCompatActivity {
    String TAG = "DataRetrieved";
    TextView counText;

    private RecyclerView rec;
    private FirestoreRecyclerAdapter adapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rec = findViewById(R.id.rec);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminRegisterActivity.class));
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Query query = db.collection("Users")
                .whereEqualTo("IsAAdmin","1" );

        FirestoreRecyclerOptions<Admin> options = new FirestoreRecyclerOptions.Builder<Admin>()
                .setQuery(query, Admin.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Admin, AdminsView.AdminsViewHolder>(options) {
            @NonNull
            @Override
            public AdminsView.AdminsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
                return new AdminsView.AdminsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AdminsView.AdminsViewHolder holder, int position, @NonNull Admin model) {
                //holder.name.setText(model.getAdmin());
                holder.adminFName.setText(model.getAdminFName());
                holder.adminSName.setText(model.getAdminSName());
                holder.adminEmail.setText(model.getAdminEmail());
                Log.d(TAG, "onBindViewHolderExecuted!!!");

            }
        };
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(adapter);
    }

    private class AdminsViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView adminFName;
        private TextView adminEmail;
        private TextView adminSName;

        public AdminsViewHolder(@NonNull View itemView) {
            super(itemView);
            //name = itemView.findViewById(R.id.userName);
            adminSName = itemView.findViewById(R.id.userName);
            adminFName = itemView.findViewById(R.id.userInfo1);
            adminEmail = itemView.findViewById(R.id.userInfo2);

            db.collection("Users")
                    .whereEqualTo("IsAdmin","1" )
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Admin admin = document.toObject(Admin.class);
                                    //admin.setDocumentId(documentSnapshot.getId());
                                   // String id = document.getId();
                                    String fname = admin.getAdminFName();
                                    String lname = admin.getAdminSName();
                                    String email = admin.getAdminEmail();
                                    //String specialization  = coun.getSpecialization();
                                    String data = "Name: " + fname + lname
                                            + "\nTitle: " + email + "\n Description:\n\n";
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
            }

}

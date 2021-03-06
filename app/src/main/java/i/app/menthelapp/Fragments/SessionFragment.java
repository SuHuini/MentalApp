package i.app.menthelapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import i.app.menthelapp.Adapters.SessionsAdapter;
import i.app.menthelapp.Counsellor;
import i.app.menthelapp.R;
import i.app.menthelapp.Session;

public class SessionFragment extends Fragment {
    String TAG = "Session";
    private RecyclerView recyclerView;
    private SessionsAdapter sessAdapter;
    private List<Session> mSessions;
    //private CollectionReference fStore;
    private FirebaseFirestore fStore;
    DocumentReference doc ;
    FirebaseAuth mAuth;
    String userEmail;
    //TextView uemail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSessions = new ArrayList<>();
        showSessions();
    return view;
    }

    public void showSessions(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userEmail = firebaseUser.getEmail();
        fStore.collection("session")
                .whereEqualTo("clientEmail",userEmail )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Session sess = document.toObject(Session.class);
                                //coun.setDocumentId(documentSnapshot.getId());
                                String clientName = sess.getClientName();
                                String counName = sess.getCounName();
                                String date = sess.getDate();
                                Session sess1 = new Session(clientName,counName, date);
                                mSessions.add(sess);
                                String data = "Name: " + clientName +
                                         "\ncounName: " + counName + "\n Date: " + date+ "\n\n";

                                //coun = document.getData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG,"Data"+ data);
                                //name.setText(data);

                                sessAdapter = new SessionsAdapter(getContext(), mSessions);
                                recyclerView.setAdapter(sessAdapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
//    public void readChatUsers(){
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseDatabase database;
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChatUsers");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUsers.clear();
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//                    mUsers.add(user);
//                    //assert user != null;
//                    //assert firebaseUser != null;
//                    //if(user.getId() != null && !user.getId().equals(firebaseUser.getUid())){         mUsers.add(user);                    }
//                    userAdapter = new UserAdapter(getContext(), mUsers);
//                    recyclerView.setAdapter(userAdapter);
//                }
//                //Log.d(TAG, user.getId() + " => " + document.getData())
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}

package i.app.menthelapp.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import i.app.menthelapp.Counsellor;
import i.app.menthelapp.R;



public class CounsellorFragment extends Fragment {
    String TAG = "Users";
    private RecyclerView recyclerView;
    private CounsellorAdapter counAdapter;
    private List<Counsellor> mCounsellors;
    private CollectionReference fireStore;
    DocumentReference doc ;
    FirebaseAuth mAuth;
    String email;
    //TextView uemail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counsellor, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        mCounsellors = new ArrayList<>();
        readUsers();
        return view;
    }

    public void readUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        //uid = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();
        //String email= null;
        DocumentReference doc = fStore.collection("Users").document(user.getUid());

        // User user;
        //DocumentReference doc = fStore.collection("chatUsers").document(user.getUid());
        fireStore = FirebaseFirestore.getInstance().collection("Users");
        fireStore.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mCounsellors.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Counsellor coun = document.toObject(Counsellor.class);
                                //String name = user.getName();
                                mCounsellors.add(coun);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //userAdapter = new UserAdapter(getContext(), mUsers);
                               // recyclerView.setAdapter(userAdapter);
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
    //}
}

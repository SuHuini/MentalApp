package i.app.menthelapp.chat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
//import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import i.app.menthelapp.R;
import i.app.menthelapp.User;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton sendButton;
    EditText userMessageInput;
    RecyclerView userMessagesList;

    TextView username;
    private static final String TAG = "UserName";
    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    ImageButton btn_send;
    EditText txt_send;
    Intent intent;
    RecyclerView recycler;
    private List<Chat> mChat;
    //private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //initializeFields();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ChatActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recycler = findViewById(R.id.message_recycler);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recycler.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.name);
        intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();



        toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            sendButton = findViewById(R.id.btn_send);
            userMessageInput = findViewById(R.id.txt_send);


//        reference = FirebaseDatabase.getInstance().getReference("ChatUsers").child(userId);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot){
//                User user = snapshot.getValue(User.class);
//                //User user = snapshot.get
//                //user.getName() != null;
//                String name = user.getName();
//                username.setText(name);
//                Log.d(TAG, "User id: " + name);
//                readMessage(firebaseUser.getUid(), userId);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // String userid = userId;
                    String msg = txt_send.getText().toString();
                    if (!msg.equals("")) {
                        sendMessage(user.getUid(), userId, msg);
                    } else {
                        Toast.makeText(ChatActivity.this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
                    }
                    txt_send.setText("");
                }
            });

    }
    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
        Log.d(TAG, "savechatdata");
    }



//    public void readMessage(final String myid, final String userid){
//        //// final String mid = myid;
//        //final String rid = userid;
//        mChat = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mChat.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Chat chat = snapshot.getValue(Chat.class);
//
//                    User user = new User();
//                    //FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                    //if(user.getId() != null && chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
//                            //user.getId() != null && chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
//                        mChat.add(chat);
//                        Log.d(TAG, "chat receiver" + userid);
//                    }
//                    messageAdapter = new MessageAdapter(ChatActivity.this, mChat);
//                    recycler.setAdapter(messageAdapter);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}

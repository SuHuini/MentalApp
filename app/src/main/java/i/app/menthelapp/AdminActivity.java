package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    Button logOut;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logOut = findViewById(R.id.button);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
    }


    public void admin(View v) {
        startActivity( new Intent(getApplicationContext(), AdminsView.class));

    }
    public void coun(View v) {
        //asynchronously retrieve multiple documents
//        ApiFuture<QuerySnapshot> future =
//                db.collection("cities").whereEqualTo("capital", true).get();
//// future.get() blocks on response
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (DocumentSnapshot document : documents) {
//            System.out.println(document.getId() + " => " + document.toObject(CounsellorView.class));
//        }
        startActivity( new Intent(getApplicationContext(), CounsellorView.class));
    }
    public void report(View v) {
        startActivity( new Intent(getApplicationContext(), ReportActivity.class));

    }
}

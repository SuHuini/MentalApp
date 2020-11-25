package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    Button logOut;

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
        startActivity( new Intent(getApplicationContext(), CounsellorView.class));
    }
}

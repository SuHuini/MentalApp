package i.app.menthelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void login(View v){
        Intent i = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(i);
    }
//    public void counlogin(View v){
//        Intent i = new Intent(MainActivity.this, CounsellorRegisterActivity.class);
//        startActivity(i);
//    }
}


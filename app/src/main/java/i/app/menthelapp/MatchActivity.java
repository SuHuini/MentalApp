package i.app.menthelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MatchActivity extends AppCompatActivity {
    Button next, coun, client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

//        coun = (Button)findViewById(R.id.counBtn);
//        client = (Button)findViewById(R.id.clientBtn);

//        next = (Button) findViewById(R.id.nextBtn);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MatchActivity.this, UserActivity.class);
//                startActivity(i);
//                                    }
//                                }
//        );
    }


}

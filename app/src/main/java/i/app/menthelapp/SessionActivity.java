package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SessionActivity extends AppCompatActivity {

    TextView date;
    TextView time;
    TextView dateTime;
    String TAG = "DATE AND TIME WORKING";
    Calendar calendar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private TextView name, email;
    String clName, couName;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid = user.getUid();

        //name = findViewById(R.id.name);
        //email = findViewById(R.id.email);

        //time = findViewById(R.id.time);
        dateTime = findViewById(R.id.date_time);

        btn = findViewById(R.id.session_activity);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SessionActivity.this, UserActivity.class);
                startActivity(i);
                finish();
            }
        });



///error code
//        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Client profile = snapshot.getValue(Client.class);
//
//                if (profile != null) {
//                    String fname = profile.clientFName;
//                    String lname = profile.clientSName;
//                    String tel = profile.clientEmail;
//
//                    name.setText(fname + " " + lname);
//                    email.setText(tel);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(SessionActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
//            }
//        });


        dateTime.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showDateT(dateTime);
                                        }
                                    });
}


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.signout:
//                userLogout();
//                break;
//            case R.id.personaldetails:
//                startActivity(new Intent(this, Client.class));
//                break;
//        }
//    }
//
//    private void userLogout() {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(this, MainActivity.class));
//    }


    private void showDateT(final TextView date){
        final Calendar cal = Calendar.getInstance();
        final int myear = cal.get(Calendar.YEAR);
        final int mmonth = cal.get(Calendar.MONTH) + 1;
        final int mday = cal.get(Calendar.DAY_OF_MONTH);
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if (month >=0){
                    month = month+1;
                }
                date.setText(day+ "/" + month +"/"+year);
                Log.d(TAG, "createUserWithEmail:success" + date);

//                TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hr, int min) {
//                        Calendar c = Calendar.getInstance();
//                       if(cal.getTimeInMillis() >= c.getTimeInMillis()) {
//                           time.setText(hr +":"+min);
//                           Log.d(TAG, "Time success");
//                       }
//                    }
//                }, hour, minute);
//                tpd.show();
            }
        }, myear, mmonth, mday);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dpd.show();
    }

}


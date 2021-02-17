package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SessionActivity extends AppCompatActivity {

    TextView date;
    TextView time;
    TextView dateTime;
    String TAG = "DATE AND TIME WORKING";
    Calendar calendar;

   // private FirebaseUser user;
    private DatabaseReference reference;
    private String uid;
    private TextView counname, username;
    String clName, couName;
    Button btn, save;
    String counId, counemail, userEmail;

    private  FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

       // user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
       // uid = user.getUid();

        dateTime = findViewById(R.id.date_time);

        save = findViewById(R.id.save_session);
        counname = findViewById(R.id.counname);
        username = findViewById(R.id.username);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
       FirebaseUser user = mAuth.getCurrentUser();
       // String userid = user.getUid();

        DocumentReference doc = fStore.collection("Users").document(user.getUid());
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username.setText("Client: "+ value.getString("clientFName"));
                userEmail = value.getString("clientEmail");
                counname.setText("Counsellor: " + value.getString("counName") );
                counemail =  value.getString("counEmail");
                Log.d(TAG, "Retrieved!!!" + username + "" + userEmail +
                        "" + counname + "" + counemail );
            }
        });
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateT(dateTime);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String counName = counname.getText().toString();
                String date = dateTime.getText().toString();
                writeNewSession(userName, counName,counemail, userEmail, date);
                Intent i = new Intent(SessionActivity.this, UserActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

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

            }
        }, myear, mmonth, mday);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        dpd.show();
    }
    private void writeNewSession(String username, String counname, String counemail, String userEmail, String time) {
        FirebaseUser user = mAuth.getCurrentUser();
        //DocumentReference df = db.collection("chatUsers").document(user.getUid());
        String userId = user.getUid();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("clientName", username);
        userInfo.put("counName",counname );
        userInfo.put("counEmail",counemail);
        userInfo.put("clientEmail", userEmail);
        userInfo.put("date", time);
        userInfo.put("status", "");
        //userInfo.put("notattended", "");
        fStore.collection("session")
                .add(userInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID" + documentReference.getId());
                        //nextActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document");
                    }
                });
        Log.d(TAG, "saveuserdata");
    }
}


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
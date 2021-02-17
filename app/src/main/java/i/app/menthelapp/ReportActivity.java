package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
//import android.support.v7.app.ActionBarActivity;
import androidx.appcompat.app.ActionBar;
import android.graphics.Color;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity{


    int sessionsNo;
    int sessionsAttended;
    int sessionsNotAttended;
    ArrayList data;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    final String TAG ="Report ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //dataSets = new ArrayList();
       // dataSets.add(barDataSet);

        fStore.collection("sesions").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            sessionsNo =task.getResult().size();
                            Log.d(TAG, "Sessions" + sessionsNo);
                        }
                    }
                });

        fStore.collection("sesions").whereEqualTo("status", "Attended").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            sessionsAttended =task.getResult().size();
                            Log.d(TAG, "Sessions" + sessionsAttended);

                        }
                    }
                });
        fStore.collection("sesions").whereEqualTo("status", "Not attended").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            sessionsNotAttended = task.getResult().size();
                            Log.d(TAG, "Sessions" + sessionsNotAttended);

                        }
                    }
                });

        BarChart chart = (BarChart) findViewById(R.id.bar_chart);

        //BarData data = new BarData(getXAxisValues(), getDataSet());
        //BarData data1 = new BarData(getDataSet(), getXAxisValues());
        // chart.setData(data);
        chart.getDescription().setText("App Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        //ArrayList dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList();
        BarEntry value = new BarEntry(0, 10);
        valueSet1.add(value);
        BarEntry value1 = new BarEntry( 1, 3 );
        valueSet1.add(value1);
        BarEntry value2 = new BarEntry( 2, 1);
        valueSet1.add(value2);

        BarDataSet barDataSet = new BarDataSet(valueSet1, "Sessions attendance");
        barDataSet.setColor(Color.rgb(0, 155,0));
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        chart.setData(barData);
        chart.setFitBars(true);

    }
    public void getReport(){

    }
    private ArrayList getDataSet (){
        ArrayList dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList();
        BarEntry value = new BarEntry(50, 0);
        valueSet1.add(value);
        BarEntry value1 = new BarEntry(20, 1);
        valueSet1.add(value1);
        BarEntry value2 = new BarEntry(30, 2);
        valueSet1.add(value2);

        BarDataSet barDataSet = new BarDataSet(valueSet1, "Sessions attendance");
        barDataSet.setColor(Color.rgb(0, 155,0));
        dataSets = new ArrayList();
        dataSets.add(barDataSet);
        return dataSets;
        //BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Sessions Not Attended");
        //barDataSet1.setColor(Color.rgb(0, 155,0));

    }
    private ArrayList getXAxisValues(){
        ArrayList xAxis = new ArrayList();
        xAxis.add("Sessions");
        xAxis.add("Sessions Not Attended");
        xAxis.add("Sessions Attended");
        return xAxis;
    }



}

package i.app.menthelapp.CounsellorPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import i.app.menthelapp.Adapters.SessionsAdapter;
import i.app.menthelapp.Fragments.SessionFragment;
import i.app.menthelapp.R;
import i.app.menthelapp.Session;
import i.app.menthelapp.SessionActivity;
import i.app.menthelapp.SessionViewActivity;

public class SessionCounActivity extends AppCompatActivity {
    List<Session> mSessions;
    private RadioGroup rg;
    RadioButton attending, notattending;
    String attend;
    Button btn;
    Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_coun);

//
//        btn1= findViewById(R.id.btn_att);
//        btn2 = findViewById(R.id.btn_att1);
//
////
////        FloatingActionButton fab = findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                startActivity(new Intent(getApplicationContext(), SessionActivity.class));
////                finish();
////            }
////        });R
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        rg = findViewById(R.id.rg_attending);
//        attending = findViewById(R.id.radio_attended);
//        notattending = findViewById(R.id.radio_NotAttended);


        SessionCounAdapter sessionsAdapter = new SessionCounAdapter(getApplicationContext(), mSessions);
        SessionCounActivity.ViewPagerAdapter viewPagerAdapter = new SessionCounActivity.ViewPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewPager);


        viewPagerAdapter.addFragment(new CounsellorSessionFragment(), "Sessions");
        viewPager.setAdapter(viewPagerAdapter);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            //set issue to text
//            @Override
//            public void onClick(View view) {
//                switch (rg.getCheckedRadioButtonId()) {
//                    case R.id.radio_attended:
//                        attend = "Attended";
//                        break;
//                    case R.id.radio_NotAttended:
//                        attend = "Not Attended";
//
//                        break;
//
//                    default:
//                        Toast.makeText(getBaseContext(),
//                                "Click a RadioButton", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//
//            }
//        });




    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> titles;
        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();

        }

        public void addFragment( Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }


    }
}

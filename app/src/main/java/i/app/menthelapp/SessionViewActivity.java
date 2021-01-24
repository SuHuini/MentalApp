package i.app.menthelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import i.app.menthelapp.Adapters.SessionsAdapter;
import i.app.menthelapp.Fragments.SessionFragment;
import i.app.menthelapp.R;

public class SessionViewActivity extends AppCompatActivity {
    List<Session> mSessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SessionActivity.class));
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(linearLayoutManager);
        SessionsAdapter sessionsAdapter = new SessionsAdapter(SessionViewActivity.this, mSessions);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewPager);


        viewPagerAdapter.addFragment(new SessionFragment(), "Sessions");
        //viewPagerAdapter.addFragment(new UsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);
        //tabLayout.setupWithViewPager(viewPager);


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

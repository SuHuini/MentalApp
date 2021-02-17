package i.app.menthelapp.CounsellorPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


import i.app.menthelapp.Client;

import i.app.menthelapp.R;


public class CounsellorActivity extends AppCompatActivity {
    List<Client> mClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor);


        ClientAdapter clientAdapter = new ClientAdapter(getApplicationContext(), mClients);
        CounsellorActivity.ViewPagerAdapter viewPagerAdapter = new CounsellorActivity.ViewPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewPager);


        viewPagerAdapter.addFragment(new ClientViewFragment(), "Clients");
        viewPager.setAdapter(viewPagerAdapter);

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

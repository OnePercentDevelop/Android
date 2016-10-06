package com.onepercent.sumus.onepercent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        mContext = this;
        startActivity(new Intent(this, SplashActivity.class)); // 스플래쉬

        FragmentSetting();


    }


    // Fragment adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new com.onepercent.sumus.onepercent.Fragment.SettingFragment();
                case 1:
                    return new com.onepercent.sumus.onepercent.Fragment.MainFragment();
                case 2:
                    return new com.onepercent.sumus.onepercent.Fragment.QuestionFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "설정";
                case 1:
                    return "메인";
                case 2:
                    return "질문";
            }
            return null;
        }

    }

    void FragmentSetting(){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mViewPager.setCurrentItem(1);
    }

}

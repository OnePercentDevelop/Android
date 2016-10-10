package com.onepercent.sumus.onepercent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
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

//        MySharedPreference pref = new MySharedPreference(this);
//        pref.removePreferences("kakaoID");
//        Log.d("SUN","MainActivity # kakao : "+ pref.getPreferences("kakaoID"));

        FragmentSetting();
        InitWidget();

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
                    return new com.onepercent.sumus.onepercent.Fragment.MainFragment();
                case 1:
                    return new com.onepercent.sumus.onepercent.Fragment.PrizeResultFragment();
                case 2:
                    return new com.onepercent.sumus.onepercent.Fragment.SettingFragment();

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
                    return "홈";
                case 1:
                    return "당첨자";
                case 2:
                    return "설정";
            }
            return null;
        }

    }

    void InitWidget() {
    }

    void FragmentSetting() {

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorWhite));

        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.home_btn));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.prize_btn));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.setting_btn));
        mViewPager.setCurrentItem(0);
    }


}
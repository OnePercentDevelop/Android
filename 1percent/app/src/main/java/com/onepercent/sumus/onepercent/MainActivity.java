package com.onepercent.sumus.onepercent;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.Fragment.PrizeResultFragment;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity{
    /*
    (f) InitWidget : 위젯 초기 설정
    (f) FragmentSetting : TabLayout & Viewpager 사용 위한 Fragment 설정
    (c) SectionsPagerAdapter : Viewpager 적용할 FragmentAdater
    */


    public static Context mContext;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    TabLayout tabLayout;


    public ProgressBar progresscircle ;
    public  BackThread thread;


    PrizeResultFragment prizeResultFragment;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        mContext = this;
        FragmentSetting();
        InitWidget();

        thread = new BackThread();
        thread.setDaemon(true);
        thread.start();

    }

    /*
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("SUN", "TAB layout " + String.valueOf(tabLayout.getHeight()));
    }*/


    // Fragment adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
                      super(fm);
        }

        public void webViewBack(){
            new com.onepercent.sumus.onepercent.Fragment.PrizeResultFragment().webVeiwBack();
        }
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new com.onepercent.sumus.onepercent.Fragment.SettingFragment();
                case 1:
                    return new com.onepercent.sumus.onepercent.Fragment.MainFragment();
                case 2:
                    return new com.onepercent.sumus.onepercent.Fragment.PrizeResultFragment();
                case 3:
                    return new  com.onepercent.sumus.onepercent.Fragment.QuestionFragment();
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
                    return "홈";
                case 2:
                    return "당첨자";
                case 3:
                    return "질문";
            }
            return null;
        }



    }

    void InitWidget() {
    }

    void FragmentSetting() {

        progresscircle = (ProgressBar) findViewById(R.id.progresscircle);
        fragmentManager = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorWhite));
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.setting_btn));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.select_home_btn));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.prize_btn));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorPoint));

        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){   }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0: // 설정
                        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.select_setting_btn));
                        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.home_btn));
                        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.prize_btn));
                        break;
                    case 1: // 홈
                        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.setting_btn));
                        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.select_home_btn));
                        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.prize_btn));
                        break;
                    case 2: // 당첨자
                        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.setting_btn));
                        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.home_btn));
                        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.select_prize_btn));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state)  { }
        });
    }

    int ThreadValue = 0;
    public  Boolean ThreadFlag = true;
    public Boolean RunFlag = true;
    class BackThread extends Thread{
        @Override
        public void run() {
            //Log.d("SUN","run ThreadFlag : " +ThreadFlag);
            while(RunFlag){
                try {

                    //Log.d("SUN","current page : " +mViewPager.getCurrentItem());
                    ThreadValue++;
                    if(ThreadFlag){
                        // Log.d("SUN","THREAD : " +ThreadValue);
                        handler.sendEmptyMessage(0);
                    }

                    if (ThreadValue >= 2) {
                        ThreadFlag = false;
                        handler.sendEmptyMessage(1);
                        ThreadValue = 0;
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    } // end class BackThread

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //Log.d("SUN","Message : " +msg.what);
            if(msg.what == 0 ){   // Message id 가 0 이면
                progresscircle.setVisibility(View.VISIBLE);
            }
            else if(msg.what == 1 )
                progresscircle.setVisibility(View.GONE);
        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode == KeyEvent.KEYCODE_BACK) &&   mViewPager.getCurrentItem() == 2 )
        {
//            prizeResultFragment = (PrizeResultFragment)fragmentManager.findFragmentById(mSectionsPagerAdapter.getItem(2).getId());
//            prizeResultFragment.webVeiwBack();
            new com.onepercent.sumus.onepercent.Fragment.PrizeResultFragment().webVeiwBack();
//            if( prizeResultFragment.mWebView.canGoBack())
//                prizeResultFragment.mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RunFlag = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RunFlag = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        RunFlag = false;
    }
}
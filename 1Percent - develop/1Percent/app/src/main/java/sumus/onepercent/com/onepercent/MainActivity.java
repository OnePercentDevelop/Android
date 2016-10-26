package sumus.onepercent.com.onepercent;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import sumus.onepercent.com.onepercent.Fragment.MainFragment;
import sumus.onepercent.com.onepercent.Fragment.MoreFragment;
import sumus.onepercent.com.onepercent.Fragment.PrizeFragment;
import sumus.onepercent.com.onepercent.Fragment.QuestionFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        InitActionBar();
        InitWidget();

    }


    void InitWidget() {
        // tab layout
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite)); // 비선택, 선택

    }

    void InitActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0,0,0,0);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0.0F);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        View actionView = getSupportActionBar().getCustomView();
        actionView.findViewById(R.id.action_moreBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation anim = null;
        anim = new AlphaAnimation(0, 1);
        animationStart(v.getId(), anim);

        switch (v.getId()){
            case R.id.action_moreBtn :
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment =  MainFragment.newInstance("hello", "world");
                    break;
                case 1:
                    fragment = QuestionFragment.newInstance("hello", "world");
                    break;
                case 2:
                    fragment = PrizeFragment.newInstance("hello", "world");
                    break;
                case 3:
                    fragment = MoreFragment.newInstance("hello", "world");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "홈";
                case 1:
                    return "질문";
                case 2:
                    return "당첨자";
                case 3:
                    return "더보기";
            }
            return null;
        }
    }

    // 애니메이션
    void animationStart(int id, Animation ani){
        Animation anim = ani;
        anim.setDuration(100);
        anim.setInterpolator(new LinearInterpolator());
        findViewById(id).startAnimation(anim);
    }
}



//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_contents, MainFragment.newInstance("hello","world"));
//        fragmentTransaction.commit();

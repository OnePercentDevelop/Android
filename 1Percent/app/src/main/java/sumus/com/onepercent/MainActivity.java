package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;

import sumus.com.onepercent.Fragment.MainFragment;
import sumus.com.onepercent.Fragment.VoteFragment;
import sumus.com.onepercent.Object.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
      (f) InitWidget : 위젯 초기 설정
      (f) InitActionBar : 액션바 초기 설정
      (f) AnimationStart : 클릭 애니메이션
      (f) FCMSetting : FCM 세팅 및 토큰
      */
    final static int REQUEST_CALENDER = 1000;
    public static Context mContext;

    // fragment
    public SectionsPagerAdapter mSectionsPagerAdapter;
    public PagerAdapter mPagerAdapter;
    public ViewPager mViewPager;
    TabLayout tabLayout;
    View actionView;

    // widget
    TextView action_titleTv;
    ImageButton action_settingBtn;

    // 변수
    public Boolean vote_possible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mContext= this;
        InitActionBar();
        InitWidget();
       // FCMSetting();

    }


    void InitWidget() {
        // tab layout

        mPagerAdapter = new sumus.com.onepercent.Object.PagerAdapter(this);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorWhite)); // 비선택, 선택

        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.mipmap.home_btn));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.mipmap.vote_btn));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.mipmap.prize_btn));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.mipmap.more_btn));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){   }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0: // 홈
                        action_titleTv.setText("홈");
                        action_settingBtn.setBackground(getResources().getDrawable(R.mipmap.timeinformation_btn));
                        break;
                    case 1: // 질문
                        action_titleTv.setText("질문");
                        action_settingBtn.setBackground(getResources().getDrawable(R.mipmap.calender_btn));
                        break;
                    case 2: // 당첨자
                        action_titleTv.setText("당첨자");
                        action_settingBtn.setBackground(getResources().getDrawable(R.mipmap.calender_btn));
                        break;
                    case 3: // 더보기
                        action_titleTv.setText("더보기");
                        action_settingBtn.setBackground(getResources().getDrawable(R.mipmap.setting_btn));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state)  { }
        });

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

        actionView = getSupportActionBar().getCustomView();
        actionView.findViewById(R.id.action_settingBtn).setOnClickListener(this);
        action_titleTv = (TextView) actionView.findViewById(R.id.action_titleTv);
        action_settingBtn = (ImageButton) actionView.findViewById(R.id.action_settingBtn);
        action_settingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Animation anim = null;
//        anim = new AlphaAnimation(0, 1);
        AnimationStart(v.getId());

        switch (v.getId()){
            case R.id.action_settingBtn :
                switch(mViewPager.getCurrentItem()){
                    case 0:
                        Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(getApplicationContext(),"question",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, CalenderActivity.class);
                        startActivityForResult(intent,REQUEST_CALENDER);
                        break;

                    case 2:
                        Toast.makeText(getApplicationContext(),"prize",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(getApplicationContext(),"more",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }


    void AnimationStart(int id){
        Animation anim =  new AlphaAnimation(0, 1);
        anim.setDuration(100);
        anim.setInterpolator(new LinearInterpolator());
        findViewById(id).startAnimation(anim);
    }

    void FCMSetting(){
        String deviceToken = FirebaseInstanceId.getInstance().getToken(); // Token - 디바이스 정보
        FirebaseMessaging.getInstance().subscribeToTopic("notice"); //  (notice)토픽명 그룹 전체 메세지 전송
        //Log.d("SUN","MainAcitivty # FCMSetting : " + deviceToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CALENDER:
                String select_date;
                if (resultCode == RESULT_OK) {
                    select_date = data.getStringExtra("select_date");
                    Toast.makeText(mContext, "select_date : " + select_date, Toast.LENGTH_SHORT).show();
                    VoteFragment.newInstance("calender","select_date");
                    //mSectionsPagerAdapter.getFragment(1).getFragmentManager().findFragmentById(R.id.)
                   // mSectionsPagerAdapter.getItem(1).getFragmentManager().beginTransaction().replace(R.id.main_contents, VoteFragment.newInstance(select_date,"world")).commit();


                } else {
                    long now = System.currentTimeMillis();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    select_date = df.format(now);
                   Toast.makeText(mContext, "select_date : " + select_date, Toast.LENGTH_SHORT).show();

                }

        }
    }
}

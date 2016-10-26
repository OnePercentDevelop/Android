package sumus.com.onepercent;

import android.support.design.widget.TabLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import sumus.com.onepercent.Object.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
      (f) InitWidget : 위젯 초기 설정
      (f) InitActionBar : 액션바 초기 설정
      (f) AnimationStart : 클릭 애니메이션
      (f) FCMSetting : FCM 세팅 및 토큰
      */


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    View actionView;

    TextView action_titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        InitActionBar();
        InitWidget();
        FCMSetting();

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
                        break;
                    case 1: // 질문
                        action_titleTv.setText("질문");
                        break;
                    case 2: // 당첨자
                        action_titleTv.setText("당첨자");
                        break;
                    case 3: // 더보기
                        action_titleTv.setText("더보기");
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
        actionView.findViewById(R.id.action_moreBtn).setOnClickListener(this);
        action_titleTv = (TextView) actionView.findViewById(R.id.action_titleTv);
    }

    @Override
    public void onClick(View v) {
        Animation anim = null;
        anim = new AlphaAnimation(0, 1);
        AnimationStart(v.getId(), anim);

        switch (v.getId()){
            case R.id.action_moreBtn :
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    void AnimationStart(int id, Animation ani){
        Animation anim = ani;
        anim.setDuration(100);
        anim.setInterpolator(new LinearInterpolator());
        findViewById(id).startAnimation(anim);
    }

    void FCMSetting(){
        String deviceToken = FirebaseInstanceId.getInstance().getToken(); // Token - 디바이스 정보
        FirebaseMessaging.getInstance().subscribeToTopic("notice"); //  (notice)토픽명 그룹 전체 메세지 전송
        Log.d("SUN","MainAcitivty # FCMSetting");
    }
}

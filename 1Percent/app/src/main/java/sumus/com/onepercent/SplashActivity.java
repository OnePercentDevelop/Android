package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.text.SimpleDateFormat;

import sumus.com.onepercent.Object.MySharedPreference;

public class SplashActivity extends AppCompatActivity {
    public static Context mContext;
    MySharedPreference pref;
    public String today_YYYYMMDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = this;
        pref = new MySharedPreference(mContext);

        long nowdate = System.currentTimeMillis(); // 현재시간
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        today_YYYYMMDD = df.format(nowdate);

        if(!pref.getPreferences("oneday","today").equals(today_YYYYMMDD))
            pref.removeAllPreferences("oneday"); // 데이터 초기화

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), MainActivity.class) );
                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 2000);
    }
}

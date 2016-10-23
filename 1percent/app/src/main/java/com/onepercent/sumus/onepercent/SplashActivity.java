package com.onepercent.sumus.onepercent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.onepercent.sumus.onepercent.Object.MySharedPreference;

public class SplashActivity extends AppCompatActivity {
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = this;


//        MySharedPreference pref = new MySharedPreference(this);
//        final String kakaoID =  pref.getPreferences("kakao","kakaoID");
//        Log.d("SUN","SplashActivity #  kakao : "+ kakaoID);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                /*
                if(kakaoID.equals("") == true) // 저장 값이 없을 떄
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class) );
                else
                    startActivity(new Intent(getApplicationContext(), MainActivity.class) );
*/
                startActivity(new Intent(getApplicationContext(), MainActivity.class) );
                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 2000);
    }
}

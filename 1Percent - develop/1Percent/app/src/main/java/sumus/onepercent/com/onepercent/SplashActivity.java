package sumus.onepercent.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mContext = this;

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

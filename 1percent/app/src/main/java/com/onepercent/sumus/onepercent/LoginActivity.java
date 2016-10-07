package com.onepercent.sumus.onepercent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton login_loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(this, SplashActivity.class)); // 스플래쉬
        login_loginBtn = (ImageButton)findViewById(R.id.login_loginBtn);
        login_loginBtn.setOnClickListener(this);


        // FCM
        String Token = FirebaseInstanceId.getInstance().getToken(); // Token - 디바이스 정보
        Log.d("SUN",Token);
        FirebaseMessaging.getInstance().subscribeToTopic("notice"); //  (notice)토픽명 그룹 전체 메세지 전송
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_loginBtn:
                login_loginBtn.setImageDrawable(getResources().getDrawable(R.mipmap.login_after));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}

package com.onepercent.sumus.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.onepercent.sumus.onepercent.Kakao.KakaoSignupActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    /*
    (f) InitWidget : 위젯 초기 설정
    (f) KakaoSetting : 카카오톡 로그인 연동 위한 설정
    (f) FCMSetting : FCM push 위한 설정
    */

    ImageButton login_loginBtn;
    EditText login_idEt;

    public String AUTH_KEY_FCM = "AIzaSyCxE2vBXE_-3aWxKO62K9-caos_6iauXTk";
    public String  API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public String  REG_ID_FCM = "dSnl0LXOBAA:APA91bH30JEu6csMBroqVgZXrX_aovOBW9kZJJ8QMJH1X95iaqrmpozxw3LPUKdsFOKvVCHHLx0jVUwjjzSp1z6RYbN1SCFtcLO68BVHq-hV5hoVmYIABArA7DbZOVdRrwEfQ4FJIXr9";

    Context mContext;

    SessionCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mContext = this;

        InitWidget();
        KakaoSetting();
        FCMSetting();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_loginBtn:
                break;
        }
    }

    void InitWidget(){
        login_loginBtn = (ImageButton)findViewById(R.id.login_loginBtn);
        login_loginBtn.setOnClickListener(this);
        login_idEt = (EditText)findViewById(R.id.login_idEt);
    }

    void KakaoSetting(){
        /*
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩 ~
            }
        });
*/
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    void FCMSetting(){
        // FCM
        String deviceToken = FirebaseInstanceId.getInstance().getToken(); // Token - 디바이스 정보
        FirebaseMessaging.getInstance().subscribeToTopic("notice"); //  (notice)토픽명 그룹 전체 메세지 전송
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}

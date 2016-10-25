package com.onepercent.sumus.onepercent.Kakao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;
import com.onepercent.sumus.onepercent.LoginActivity;
import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;

public class KakaoSignupActivity extends AppCompatActivity {
     /*
    (f) requestMe : 유저의 정보를 받아오는 함수
    (f) redirectMainActivity : kakao 로그인 성공시 호출
    (f) redirectLoginActivity : kakao 로그인 실패시 호출
    */

    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_kakao_signup);
        requestMe();
    }

    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                Log.d("SUN","KakaoSignupActivity # UserProfile : " + userProfile);

                MySharedPreference pref = new MySharedPreference(getApplicationContext());

                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                String profileImagePath = userProfile.getProfileImagePath();     // Nickname 값을 가져옴

                pref.setPreferences("kakao","kakaoID", kakaoID);
                pref.setPreferences("kakao","kakaoNickname", kakaoNickname);
                pref.setPreferences("kakao","kakaoProfileImage", profileImagePath);

               // Log.d("SUN","KakaoSignupActivity #  kakaoID : " + pref.getPreferences("kakao","kakaoID") + " , kakaoNick : " + pref.getPreferences("kakao","kakaoNickname"));
                redirectMainActivity(); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}

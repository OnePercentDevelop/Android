package com.onepercent.sumus.onepercent.FCM;

/**
 * Created by MINI on 2016-10-07.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;


/*
사용자 기기별 token을 생성하는 클래스 입니다.
나중에 push 알림을 특정 타겟에게 보낼 때 사용되는 고유 키 값 입니다.
이 토큰값을 용도에 맞게 사용하시면 됩니다
*/
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("SUN","FirebaseInstanceIDService : "+token);

    }

    /* token 서버로 전송 소스
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        //request
        Request request = new Request.Builder()
                .url("http://localhost:8010/FCMserver/fcm.jsp")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
       */
}
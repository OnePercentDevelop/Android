package com.onepercent.sumus.onepercent.FCM;

/**
 * Created by MINI on 2016-10-07.
 */

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;
import com.onepercent.sumus.onepercent.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;


/*
사용자 기기별 token을 생성하는 클래스 입니다.
나중에 push 알림을 특정 타겟에게 보낼 때 사용되는 고유 키 값 입니다.
이 토큰값을 용도에 맞게 사용하시면 됩니다
*/
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    Context mContext ;
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        mContext = ((SplashActivity)SplashActivity.mContext);
        // Get updated InstanceID token.
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("SUN","FirebaseInstanceIDService # deviceToken : "+deviceToken);

        MySharedPreference pref = new MySharedPreference(mContext);
        pref.setPreferences("fcm","device",deviceToken);
        pref.setPreferences("fcm","push","yes");

       // setDeviceToken_Server(deviceToken);

    }

    /*************** deviceToken 전송  *********************/
    void setDeviceToken_Server(String token) {
        RequestParams params  = new RequestParams();
        params.put("",token);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "FirebaseInstanceIDService # setDeviceToken_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/votenumber.do",params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                //  Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("vote_result") + "";


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN", "e : " + e.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

}
package com.onepercent.sumus.onepercent.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onepercent.sumus.onepercent.LoginActivity;
import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;
import com.onepercent.sumus.onepercent.R;
import com.onepercent.sumus.onepercent.SplashActivity;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MINI on 2016-10-05.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
     /*
    (f) InitWidget : 위젯 초기 설정
    */

    Context mContext;
    View views;
    Button setting_logoutBtn;
    ImageView setting_profileImg;
    TextView setting_nameTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views =  inflater.inflate(R.layout.fragment_setting, container, false);
        mContext = getContext();
        InitWidget();
        getImage_Server();
        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.setting_logoutBtn:
                MySharedPreference pref = new MySharedPreference(getContext());
                pref.removeAllPreferences("kakao");
                pref.removeAllPreferences("oneday");
                Toast.makeText(((SplashActivity)SplashActivity.mContext),"로그아웃",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    void InitWidget(){
        setting_logoutBtn = (Button)views.findViewById(R.id.setting_logoutBtn);
        setting_logoutBtn.setOnClickListener(this);

        setting_profileImg = (ImageView)views.findViewById(R.id.setting_profileImg);
        setting_nameTv = (TextView) views.findViewById(R.id.setting_nameTv);
    }


    /***************  image 가져오기  *********************/

    public Bitmap byteArrayToBitmap(byte[] byteArray ) {  // byte -> bitmap 변환 및 반환
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
    }


    void getImage_Server() {
        MySharedPreference pref = new MySharedPreference(mContext);
        String url = pref.getPreferences("kakao","kakaoProfileImage");
        String nickname = pref.getPreferences("kakao","kakaoNickname");
        setting_nameTv.setText(nickname+"님");


        AsyncHttpClient client = new AsyncHttpClient();

        Log.d("SUN", "getImage_Server()");
        client.get(url,  new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // byteArrayToBitmap 를 통해 reponse로 받은 이미지 데이터 bitmap으로 변환
                Bitmap bitmap = byteArrayToBitmap(response);
                setting_profileImg.setImageBitmap(bitmap);

               // Log.d("SUN", "statusCode : " + statusCode + " , response : " +  new String(response));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {    }
        });
    }

}

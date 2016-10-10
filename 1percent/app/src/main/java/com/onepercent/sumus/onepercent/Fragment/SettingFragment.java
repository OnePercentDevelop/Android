package com.onepercent.sumus.onepercent.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.LoginActivity;
import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;
import com.onepercent.sumus.onepercent.R;
import com.onepercent.sumus.onepercent.SplashActivity;

/**
 * Created by MINI on 2016-10-05.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
     /*
    (f) InitWidget : 위젯 초기 설정\
    */

    View views;
    Button setting_logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views =  inflater.inflate(R.layout.fragment_setting, container, false);
        InitWidget();

        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.setting_logoutBtn:
                MySharedPreference pref = new MySharedPreference(getContext());
                pref.removeAllPreferences("kakao");
                Toast.makeText(getContext(),"로그아웃",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    void InitWidget(){
        setting_logoutBtn = (Button)views.findViewById(R.id.setting_logoutBtn);
        setting_logoutBtn.setOnClickListener(this);
    }
}

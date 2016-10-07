package com.onepercent.sumus.onepercent.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.R;

/**
 * Created by MINI on 2016-10-05.
 */

public class MainFragment extends Fragment  implements View.OnClickListener {
    View views;
    ImageButton main_leftBtn,main_rightBtn;
    Button pushBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_main, container, false);
        InitWidget();
        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.main_leftBtn:
                ((MainActivity)MainActivity.mContext).mViewPager.setCurrentItem(0);
                break;
            case R.id.main_rightBtn:
                ((MainActivity)MainActivity.mContext).mViewPager.setCurrentItem(2);
                break;
            case R.id.pushBtn:
                try {
                    ((MainActivity)MainActivity.mContext).PushNoti("ezSahN6sdMQ:APA91bEaof8JSBH98ty9_od2vSlTzERov0Ud2LW2V4ZqWDefVPSsVPJCnynOEBfICLuq3kt6rqGE5-ZsT-0LLKAFmGrtVf-_FaoKshcgtzuIo8LkkiP-dj_TGTYMoMC0iAhk4uTtZ4ly");
                    Toast.makeText(getContext(),"push !!",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("SUN",  e.getMessage().toString());
                }
                break;
        }
    }

    void InitWidget(){
        main_rightBtn = (ImageButton) views.findViewById(R.id.main_rightBtn);
        main_rightBtn.setOnClickListener(this);
        main_leftBtn = (ImageButton) views.findViewById(R.id.main_leftBtn);
        main_leftBtn.setOnClickListener(this);
        pushBtn = (Button) views.findViewById(R.id.pushBtn);
        pushBtn.setOnClickListener(this);
    }
}

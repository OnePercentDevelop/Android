package com.onepercent.sumus.onepercent.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.R;

/**
 * Created by MINI on 2016-10-05.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    View views;
   ImageButton setting_rightBtn;
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
            case R.id.setting_rightBtn:
                ((MainActivity)MainActivity.mContext).mViewPager.setCurrentItem(1);
                break;
        }
    }

    void InitWidget(){
        setting_rightBtn = (ImageButton) views.findViewById(R.id.setting_rightBtn);
        setting_rightBtn.setOnClickListener(this);
    }
}

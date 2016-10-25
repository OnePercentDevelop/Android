package com.onepercent.sumus.onepercent.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.R;

/**
 * Created by MINI on 2016-10-07.
 */

public class QuestionFragment  extends Fragment implements View.OnClickListener {
    /*
    (f) InitWidget : 위젯 초기 설정\
    */

    // fragment
    View views;
    Context mContext;
    Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_question, container, false);

        mContext = getContext();
        mActivity = getActivity();

        InitWidget();

        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
        }
    }

    void InitWidget(){

    }
}
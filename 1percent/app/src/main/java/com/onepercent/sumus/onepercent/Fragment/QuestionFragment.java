package com.onepercent.sumus.onepercent.Fragment;

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
    View views;
    ImageButton question_leftBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_question, container, false);
        InitWidget();

        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.question_leftBtn:
                ((MainActivity)MainActivity.mContext).mViewPager.setCurrentItem(1);
                break;
        }
    }

    void InitWidget(){
        question_leftBtn = (ImageButton) views.findViewById(R.id.question_leftBtn);
        question_leftBtn.setOnClickListener(this);
    }
}
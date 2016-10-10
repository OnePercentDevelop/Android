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
 * Created by edu on 2016-10-10.
 */

public class PrizeResultFragment extends Fragment implements View.OnClickListener {
    View views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_prize_result, container, false);
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

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

        }
    }

    void InitWidget(){
    }
}

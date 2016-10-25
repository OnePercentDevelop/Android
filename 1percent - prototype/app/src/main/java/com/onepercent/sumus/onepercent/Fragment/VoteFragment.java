package com.onepercent.sumus.onepercent.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onepercent.sumus.onepercent.R;

/**
 * Created by MINI on 2016-10-23.
 */

public class VoteFragment extends Fragment {

    // fragment
    public View views;
    public Context mContext;
    public Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_vote, container, false);
        mContext = getContext();
        mActivity = getActivity();
        InitWidget();

        return views;

    }

    public void InitWidget(){

    }

}

package com.onepercent.sumus.onepercent.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.R;

/**
 * Created by edu on 2016-10-10.
 */

public class PrizeResultFragment extends Fragment implements View.OnClickListener{
   /*
    (f) InitWidget : 위젯 초기 설정
    (c) WebViewClientClass : 오늘의 data 서버 연
    */

    // frgment
    View views;
   public static Context mContext;
   public static Activity mActivity;

    // Widget
    public WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_prize_result, container, false);

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
        mWebView = (WebView) views.findViewById(R.id.prize_webVeiw);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://52.78.88.51:8080/OnePercentServer/timer2.do");
        mWebView.setWebViewClient(new WebViewClientClass());
    }


    public  void webVeiwBack(){
        mWebView = (WebView) views.findViewById(R.id.prize_webVeiw);
        if(mWebView.canGoBack()) {

            Toast.makeText(mActivity, "back", Toast.LENGTH_SHORT).show();
            mWebView.goBack();
        }
    }

    public class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}

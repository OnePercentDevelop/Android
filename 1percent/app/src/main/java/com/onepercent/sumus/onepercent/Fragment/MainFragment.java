package com.onepercent.sumus.onepercent.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.onepercent.sumus.onepercent.MainActivity;
import com.onepercent.sumus.onepercent.Object.MySharedPreference;
import com.onepercent.sumus.onepercent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MINI on 2016-10-05.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    /*
    (f) InitWidget : 위젯 초기 설정
    (f) getMain_Server : 오늘의 data 서버 연동
    (f) getVoteNumber_Server : 현재 투표자수 서버 연동
    (f) getMain_Reload : 오늘의 data 가 이미 있으면 저장된 값을 로드
    */

    public View views;
    public Context mContext;
    LinearLayout main_exampleLayout;
    TextView main_gifticonTv, main_voterCountTv, main_questionTv, main_beforePrizeTv, main_clockTv;
    ScrollView scrollView;

    int scrollY = 0;

    String today_YYYYMMDD;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getContext();
        InitWidget();


        long nowdate = System.currentTimeMillis(); // 현재시간
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String now = df.format(nowdate);
        today_YYYYMMDD = now;

        ClockSet();

        MySharedPreference pref = new MySharedPreference(mContext);
        String today = pref.getPreferences("oneday","today");

       if(now.equals(today)) // oneday data 이미
           getMain_Reload();
        else // oneday data 아직
            getMain_Server();

        getVoteNumber_Server();
        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void ClockSet(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date base_date = null;
        long base_time, now_time, gap_time;

        try {
            base_date = df.parse(today_YYYYMMDD+" 22:00:00"); // 기준시간
            base_time =  base_date.getTime(); // data 시간을 long 형으로
            now_time = System.currentTimeMillis(); // 현재시간

            gap_time = (base_time -now_time) / 1000; // 기준 - 현재 시간  (초단위)


            long hourGap = gap_time / 60 / 60 ;
            long reminder = ((long)(gap_time / 60)) % 60;
            long minGap = reminder;
            long secGap = gap_time % 60;

            String resultTime = String.format("%02d", hourGap) + ":" +  String.format("%02d", minGap) + ":" +  String.format("%02d", secGap);
            //Log.d("SUN", "MainFragment # resultTime : " +resultTime);
            main_clockTv.setText(resultTime);


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    void InitWidget() {
        mContext = getContext();
        main_gifticonTv = (TextView) views.findViewById(R.id.main_gifticonTv);
        main_voterCountTv = (TextView) views.findViewById(R.id.main_voterCountTv);
        main_questionTv = (TextView) views.findViewById(R.id.main_questionTv);
        main_beforePrizeTv = (TextView) views.findViewById(R.id.main_beforePrizeTv);
        main_clockTv = (TextView) views.findViewById(R.id.main_clockTv);

        main_exampleLayout = (LinearLayout) views.findViewById(R.id.main_exampleLayout);
        scrollView = (ScrollView) views.findViewById(R.id.scrollView);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int scrollViewPos = scrollView.getScrollY();
                if(scrollViewPos <= 0) {
                    int action = event.getAction();
                    switch (action) {
                        case DragEvent.ACTION_DRAG_STARTED:

                            ((MainActivity)MainActivity.mContext).progresscircle.setVisibility(View.VISIBLE);
                            getVoteNumber_Server();
                            ClockSet();
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            break;
                        case DragEvent.ACTION_DROP:
                            break;
                        case DragEvent.ACTION_DRAG_ENDED:
                            break;
                        default:
                            break;
                    }
                }
                    return false;
            }
        });

    }

    void getMain_Reload(){
        MySharedPreference pref = new MySharedPreference(mContext);
        main_gifticonTv.setText( pref.getPreferences("oneday","gift"));
        main_questionTv.setText(pref.getPreferences("oneday","question"));
        main_beforePrizeTv.setText( pref.getPreferences("oneday","winner"));

        for (int z = 1; z <= 4; z++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            layoutParams.gravity= Gravity.CENTER;


            Button exampleBtn = new Button(mContext);
            exampleBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            exampleBtn.setText((z) + ". " +pref.getPreferences("oneday","ex"+z));
            exampleBtn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            exampleBtn.setTextSize(25/mContext.getResources().getDisplayMetrics().scaledDensity); // PX -> SP로 변환
            exampleBtn.setLayoutParams(layoutParams);


            main_exampleLayout.addView(exampleBtn);

            if(z<4) {
                LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(   0,     LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.gravity= Gravity.CENTER;
                ImageView line = new ImageView(mContext);
                line.setBackground(getResources().getDrawable(R.mipmap.btn_line));
                line.setLayoutParams(layoutParams);
                main_exampleLayout.addView(line);
            }

        }
    }

    void getMain_Server() {
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getMain_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/main.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

               // Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("main_result") + "";
                    JSONArray arr = new JSONArray(objStr);
                    for (int i = 0; i < arr.length(); i++) {

                        MySharedPreference pref = new MySharedPreference(mContext);

                        JSONObject obj = (JSONObject) arr.get(i);

                        String gift = (String) obj.get("gift");
                        String winner = (String) obj.get("winner");
                        String question = (String) obj.get("question");
                        String today = (String) obj.get("today");

                        main_gifticonTv.setText(gift);
                        main_questionTv.setText(question);
                        main_beforePrizeTv.setText(winner);


                        pref.setPreferences("oneday","today", today);
                        pref.setPreferences("oneday","gift", gift);
                        pref.setPreferences("oneday","winner", winner);
                        pref.setPreferences("oneday","question", question);


                        JSONArray exArr = (JSONArray) obj.get("example");
                        for (int z = 1; z <= 4; z++) {
                            JSONObject exObj = (JSONObject) exArr.get(0);
                            String ex = (String) exObj.get(z + "");

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                            layoutParams.gravity= Gravity.CENTER;


                            Button exampleBtn = new Button(mContext);
                            exampleBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            exampleBtn.setLayoutParams(layoutParams);
                            exampleBtn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                            exampleBtn.setTextSize(25/mContext.getResources().getDisplayMetrics().scaledDensity); // PX -> SP로 변환
                            exampleBtn.setText((z) + ". " + ex);

                            pref.setPreferences("oneday","ex"+z, ex);


                            main_exampleLayout.addView(exampleBtn);
                            if(z<4) {
                                LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(   1,     LinearLayout.LayoutParams.WRAP_CONTENT);

                                layoutParams.gravity= Gravity.CENTER;
                                ImageView line = new ImageView(mContext);
                                line.setBackground(getResources().getDrawable(R.mipmap.btn_line));
                                line.setLayoutParams(layoutParams);
                                main_exampleLayout.addView(line);
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN", "e : " + e.toString());
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }


    void getVoteNumber_Server() {

        ((MainActivity)MainActivity.mContext).progresscircle.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getVoteNumber_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/votenumber.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

              //  Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("vote_result") + "";

                    JSONArray arr = new JSONArray(objStr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        int number = (int) obj.get("number");
                        main_voterCountTv.setText(number + "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN", "e : " + e.toString());
                }

                ((MainActivity)MainActivity.mContext).progresscircle.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

}

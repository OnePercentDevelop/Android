package com.onepercent.sumus.onepercent.Fragment;

import android.content.Context;
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
import com.onepercent.sumus.onepercent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MINI on 2016-10-05.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    public View views;
    public Context mContext;
    LinearLayout main_exampleLayout;
    TextView main_gifticonTv, main_voterCountTv, main_questionTv, main_beforePrizeTv;
    ScrollView scrollView;

    int scrollY = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_main, container, false);
        InitWidget();
        getMain_Server();
        getVoteNumber_Server();
        return views;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    void InitWidget() {
        mContext = getContext();
        main_gifticonTv = (TextView) views.findViewById(R.id.main_gifticonTv);
        main_voterCountTv = (TextView) views.findViewById(R.id.main_voterCountTv);
        main_questionTv = (TextView) views.findViewById(R.id.main_questionTv);
        main_beforePrizeTv = (TextView) views.findViewById(R.id.main_beforePrizeTv);
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
                            getVoteNumber_Server();
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


    void getMain_Server() {

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "getMain_Server()");
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

                        JSONObject obj = (JSONObject) arr.get(i);

                        String gifticon = (String) obj.get("gift");
                        String winner = (String) obj.get("winner");
                        String question = (String) obj.get("question");
                        String today = (String) obj.get("today");
                        Log.d("SUN"," today : "+today);


                        main_gifticonTv.setText(gifticon);
                        main_questionTv.setText(question);
                        main_beforePrizeTv.setText(winner);


                        JSONArray exArr = (JSONArray) obj.get("example");
                        for (int z = 1; z <= 4; z++) {
                            JSONObject exObj = (JSONObject) exArr.get(0);
                            String ex = (String) exObj.get(z + "");

                            LinearLayout layout = new LinearLayout(mContext);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            layout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            layout.setGravity(Gravity.CENTER);
                            Button exampleBtn = new Button(mContext);
                            exampleBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            exampleBtn.setText((z) + ". " + ex);

                            layout.addView(exampleBtn);

                            if(z<4) {
                                ImageView line = new ImageView(mContext);
                                line.setBackground(getResources().getDrawable(R.mipmap.btn_line));
                                layout.addView(line);
                            }
                            main_exampleLayout.addView(layout);
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

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "getVoteNumber_Server()");
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

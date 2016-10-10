package com.onepercent.sumus.onepercent.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class MainFragment extends Fragment  implements View.OnClickListener {
    public View views;
    public  Context mContext;
    public LinearLayout main_exampleLayout;
    public TextView main_gifticonTv, main_voterCountTv, main_questionTv, main_beforePrizeTv;


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
        switch (v.getId())
        {

        }
    }

    void InitWidget(){
        mContext = getContext();
        main_gifticonTv = (TextView)views.findViewById(R.id.main_gifticonTv);
        main_voterCountTv = (TextView)views.findViewById(R.id.main_voterCountTv);
        main_questionTv = (TextView)views.findViewById(R.id.main_questionTv);
        main_beforePrizeTv = (TextView)views.findViewById(R.id.main_beforePrizeTv);
        main_exampleLayout = (LinearLayout)views.findViewById(R.id.main_exampleLayout);
    }



    void getMain_Server() {

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "getMain_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/main.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {         }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                Log.d("SUN", "statusCode : " + statusCode + " , response : " +  new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr =  object.get("main_result") + "";
                    Log.d("SUN", "object : " +object);
                    JSONArray arr = new JSONArray(objStr);
                    for(int i=0; i<arr.length(); i++ ) {

                        JSONObject obj = (JSONObject)arr.get(i);

                        String gifticon  = (String)obj.get("gift");
                        String winner  = (String)obj.get("winner");
                        String question  = (String)obj.get("question");


                        main_gifticonTv.setText(gifticon);
                        main_questionTv.setText(question);
                        main_beforePrizeTv.setText(winner);


                        /*
                        String exStr =  object.get("example")+ "";
                       Log.d("SUN", "exStr : " +exStr);

                        JSONArray exArr = new JSONArray(exStr);

                        for(int z=0; z<exArr.length(); z++ ){
                            JSONObject exObj = (JSONObject)exArr.get(i);
                            String ex =  (String)exObj.get("ex");

                            LinearLayout layout = new LinearLayout(mContext);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            Button exampleBtn = new Button(mContext);
                            exampleBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            exampleBtn.setText((z+1)+". "+ex);
                            layout.addView(exampleBtn);
                            main_exampleLayout.addView(layout);
                        }
*/



                        for(int z=0; z<4; z++ ) // 버튼
                        {
                            LinearLayout layout = new LinearLayout(mContext);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            Button exampleBtn = new Button(mContext);
                            exampleBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            exampleBtn.setText((z+1)+". ");
                            layout.addView(exampleBtn);

                            main_exampleLayout.addView(layout);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN",  "e : " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {          }
        });
    }


    void getVoteNumber_Server() {

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "getVoteNumber_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/votenumber.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {         }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                Log.d("SUN", "statusCode : " + statusCode + " , response : " +  new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr =  object.get("vote_result") + "";
                    Log.d("SUN", "object : " +object);

                    JSONArray arr = new JSONArray(objStr);

                    for(int i=0; i<arr.length(); i++ ) {
                        JSONObject obj = (JSONObject)arr.get(i);
                        int number  = (int)obj.get("number");
                        main_voterCountTv.setText(number+"");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN",  "e : " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "onFailure // statusCode : " + statusCode + " , headers : " + headers.toString() + " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {          }
        });
    }

}

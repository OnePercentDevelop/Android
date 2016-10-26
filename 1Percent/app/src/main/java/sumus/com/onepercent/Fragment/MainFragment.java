package sumus.com.onepercent.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import sumus.com.onepercent.Object.MySharedPreference;
import sumus.com.onepercent.R;


public class MainFragment extends Fragment implements View.OnClickListener {
     /*
    (f) InitWidget : 위젯 초기 설정
    (f) getMain_Server : 오늘의 data 서버 연동
    (f) getVoteNumber_Server : 현재 투표자수 서버 연동
    (f) getMain_Reload : 오늘의 data 가 이미 있으면 저장된 값을 로드
    (f) ClockSet : 남은 시간 계산
    (c) TimerThread : 남은 시간 스레드 + TimerHandler
    */


    // frgment init data
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    // fragment
    public View views;
    public Context mContext;
    public Activity mActivity;

    //widget
    TextView main_gifticonTv, main_voterCountTv, main_questionTv, main_beforePrizeTv, main_clockTv;
    Button main_exBtn[] = new Button[5];//main_ex1Btn, main_ex2Btn, main_ex3Btn, main_ex4Btn;

    // Timer
    public TimerThread thread;


    // 변수
    String today_YYYYMMDD;
    MySharedPreference pref;
    public Boolean RunFlag = true; // timer thread flag
    int exBtnArray[] = {R.id.main_ex1Btn, R.id.main_ex2Btn, R.id.main_ex3Btn, R.id.main_ex4Btn};

    public MainFragment() {     }


    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_main, container, false);
        mContext = getContext();
        mActivity = getActivity();
        //Log.d("SUN", "MainFragment # "+ mParam1 + " , "+ mParam2);
        InitWidget();

        long nowdate = System.currentTimeMillis(); // 현재시간
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String now = df.format(nowdate);
        today_YYYYMMDD = now; // 오늘날짜 계산 및 변환
        ClockSet();

        pref = new MySharedPreference(mContext);
        String today = pref.getPreferences("oneday","today"); // 오늘 데이터 유무 확인


        if(now.equals(today)) // oneday data 이미
            getMain_Reload();
        else                  // oneday data 아직
            getMain_Server();


        getVoteNumber_Server(); // 투표자수 갱신

        return views;
    }

    void InitWidget() {
        mContext = getContext();
        main_clockTv = (TextView) views.findViewById(R.id.main_clockTv);
        main_gifticonTv = (TextView) views.findViewById(R.id.main_gifticonTv);
        main_voterCountTv = (TextView) views.findViewById(R.id.main_voterCountTv);
        main_questionTv = (TextView) views.findViewById(R.id.main_questionTv);
        main_beforePrizeTv = (TextView) views.findViewById(R.id.main_beforePrizeTv);

        main_exBtn[1] = (Button) views.findViewById(R.id.main_ex1Btn);
        main_exBtn[2] = (Button) views.findViewById(R.id.main_ex2Btn);
        main_exBtn[3] = (Button) views.findViewById(R.id.main_ex3Btn);
        main_exBtn[4] = (Button) views.findViewById(R.id.main_ex4Btn);


    }

    void getMain_Reload(){
        MySharedPreference pref = new MySharedPreference(mContext);
        main_gifticonTv.setText( pref.getPreferences("oneday","gift"));
        main_questionTv.setText(pref.getPreferences("oneday","question"));
        main_beforePrizeTv.setText( pref.getPreferences("oneday","winner"));

        for (int z = 1; z <= 4; z++) {
          //  Log.d("SUN","MainFragment # " +pref.getPreferences("oneday","ex"+z));
            String ex = pref.getPreferences("oneday","ex"+z);
            main_exBtn[z].setText(ex);
        }
    }

    void getMain_Server() {
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getMain_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/main.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {    }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                //Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));
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
                            pref.setPreferences("oneday","ex"+z, ex);
                            main_exBtn[z].setText(ex+"");
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
            public void onRetry(int retryNo) {  }
        });
    }


    void getVoteNumber_Server() {
        //((MainActivity)MainActivity.mContext).progresscircle.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getVoteNumber_Server()");
        client.get("http://52.78.88.51:8080/OnePercentServer/votenumber.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {   }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

               // Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

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
            public void onRetry(int retryNo) {  }
        });
    }

    @Override
    public void onClick(View v) {


    }


    class TimerThread extends Thread{
        @Override
        public void run() {
            while(RunFlag){
                try {
                   // Log.d("SUN","MainFragment # run TimerThread : " +ints++);
                    TimerHandler.sendEmptyMessage(0);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler TimerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //Log.d("SUN","Message : " +msg.what);
            ClockSet();
        }
    };

    public void ClockSet(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date base_date = null;
        long base_time, now_time, gap_time;

        try {
            base_date = df.parse(today_YYYYMMDD+" 22:00:00"); // 기준시간
            base_time =  base_date.getTime(); // date형 기준 시간을 long 변환

            now_time = System.currentTimeMillis(); // 현재시간

            gap_time = (base_time -now_time) / 1000; // 기준 시간 - 현재 시간 (초)

            long hourGap = gap_time / 60 / 60 ; // 시간
            long minGap = ((long)(gap_time / 60)) % 60; // 분
            long secGap = gap_time % 60; // 초

            String resultTime = String.format("%02d", hourGap) + ":" +  String.format("%02d", minGap) + ":" +  String.format("%02d", secGap);
            main_clockTv.setText(resultTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onStart() {
        super.onStart();
//        RunFlag = true;
//        thread = new TimerThread();
//        thread.setDaemon(true);
//        thread.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        RunFlag = true;
        thread = new TimerThread();
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        RunFlag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RunFlag = false;
    }

}

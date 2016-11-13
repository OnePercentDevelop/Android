package sumus.com.onepercent.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;
import sumus.com.onepercent.FontBaseActvity;
import sumus.com.onepercent.JoinActivity;
import sumus.com.onepercent.LoginActivity;
import sumus.com.onepercent.MainActivity;
import sumus.com.onepercent.Object.MySharedPreference;
import sumus.com.onepercent.R;


public class VoteFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{
   /*
    (f) InitWidget : 위젯 초기 설정
    (f) InitData : 기본 데이터 로드하기
    (f) getMain_Server : 오늘의 data 서버 연동
    */

    // fragment init data
        private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // fragment
    public View views;
    Activity mActivity;

    // widget
    RadioGroup vote_radiogroup;
    RadioButton  vote_radio[] = new RadioButton[5];
    int radio[] = {0,R.id.vote_radio1, R.id.vote_radio2, R.id.vote_radio3, R.id.vote_radio4};
    ImageButton vote_voteBtn, vote_calenderBtn;
    TextView vote_dateTv, vote_questionTv;

    // 변수
    int vote_number = 0;
    MySharedPreference pref;
    String today_YYYYMMDD, day_YYYYMMDD;
    SimpleDateFormat df_circle = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat df_korean = new SimpleDateFormat("yyyy년MM월dd일");

    public VoteFragment() {    }

    public static VoteFragment newInstance(String param1, String param2) {
        VoteFragment fragment = new VoteFragment();
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
        views = inflater.inflate(R.layout.fragment_vote, container, false);
        mActivity = getActivity();
        InitWidet();
        InitData();
        InitVote();

        FontBaseActvity fontBaseActvity = new FontBaseActvity();
        fontBaseActvity.setGlobalFont(views);

        return views;
    }

    public void InitData() {
        String question = pref.getPreferences("oneday","question");
        if(question.equals(""))
            getMain_Server();
        else {
            vote_questionTv.setText(question);
            for (int z = 1; z <= 4; z++) {
                String ex = pref.getPreferences("oneday", "ex" + z);
                vote_radio[z].setText("" + ex);
            }
        }

    }

    public void InitVote(){
        if(pref.getPreferences("oneday","vote").equals(""))
            vote_radiogroup.setOnCheckedChangeListener(this);
        else{
            String index = pref.getPreferences("oneday","vote");
            vote_radiogroup.setEnabled(false);
            vote_radio[Integer.parseInt(index)].setChecked(true);
            vote_number = Integer.parseInt(index);
            for(int i=1; i<5; i++){
                vote_radio[i].setEnabled(false);
            }
        }
    }
    void InitWidet() {

        pref = new MySharedPreference(mActivity);
        vote_radiogroup = (RadioGroup) views.findViewById(R.id.vote_radiogroup);
        for(int i=1; i<5; i++)
            vote_radio[i] =(RadioButton) views.findViewById( radio[i]);

        long nowdate = System.currentTimeMillis(); // 현재시간
        today_YYYYMMDD = df_circle.format(nowdate).toString();
        day_YYYYMMDD = df_korean.format(nowdate).toString();

        vote_dateTv = (TextView)views.findViewById(R.id.vote_dateTv);
        vote_dateTv.setText(today_YYYYMMDD);
        vote_questionTv= (TextView)views.findViewById(R.id.vote_questionTv);
        vote_voteBtn = (ImageButton) views.findViewById(R.id.vote_voteBtn);
        vote_voteBtn.setOnClickListener(this);
        vote_calenderBtn = (ImageButton) views.findViewById(R.id.vote_calenderBtn);
        vote_calenderBtn.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        vote_voteBtn.setBackground(getResources().getDrawable(R.mipmap.vote_login_btn));
        switch (checkedId){
            case R.id.vote_radio1:
                vote_number = 1;
                break;
            case R.id.vote_radio2:
                vote_number = 2;
                break;
            case R.id.vote_radio3:
                vote_number = 3;
                break;
            case R.id.vote_radio4:
                vote_number = 4;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vote_voteBtn:
                if (((MainActivity) MainActivity.mContext).vote_possible == true) {
                    if (vote_number <= 0) { // 보기 선택 안했을때
                        Toast.makeText(mActivity, "보기를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    } else if (pref.getPreferences("oneday", "vote").equals("")) { // 투표 안했을 때
                        LayoutInflater inflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = inflate.inflate(R.layout.dialog_login, null);
                        Button dialog_loginCancleBtn = (Button) layout.findViewById(R.id.dialog_loginCancleBtn);
                        Button dialog_loginOkBtn = (Button) layout.findViewById(R.id.dialog_loginOkBtn);
                        TextView dialog_loginTv = (TextView) layout.findViewById(R.id.dialog_loginTv);

                        AlertDialog.Builder aDialog = new AlertDialog.Builder(mActivity);
                        aDialog.setView(layout);

                        final AlertDialog ad = aDialog.create();
                        ad.show();

                        dialog_loginCancleBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ad.cancel();
                            }
                        });

                        if (pref.getPreferences("user", "userPhone").equals("")) { // 로그인 안했을 때
                            dialog_loginTv.setText("로그인 하셔야 투표가능합니다. \n로그인하시겠습니까?");
                            dialog_loginOkBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mActivity, JoinActivity.class);
                                    startActivity(intent);
                                    ad.cancel();
                                }
                            });
                        }
                        else {
                            dialog_loginTv.setText(vote_number + "번으로 투표하시겠습니까?");
                            dialog_loginOkBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 1; i < 5; i++)
                                        vote_radio[i].setEnabled(false);
                                    vote_radio[vote_number].setChecked(true);
                                    setVote_Server( pref.getPreferences("user", "userPhone"),day_YYYYMMDD, vote_number+"");
                                    ad.cancel();
                                }
                            });
                        }
                    }
                    else
                        Toast.makeText(mActivity, "이미 투표 하셨습니다", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mActivity, "투표 가능한 시간이 아닙니다", Toast.LENGTH_SHORT).show();
                break;

            case R.id.vote_calenderBtn:
                LayoutInflater inflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflate.inflate(R.layout.dialog_calender, null);

                AlertDialog.Builder aDialog = new AlertDialog.Builder(mActivity);
                aDialog.setView(layout);
                final AlertDialog ad = aDialog.create();
                ad.show();

                Button calender_cancleBtn = (Button) layout.findViewById(R.id.calender_cancleBtn);
                Button calender_okBtn = (Button) layout.findViewById(R.id.calender_okBtn);
                final CalendarView calendarView = (CalendarView)layout.findViewById(R.id.calendarView);
                AnimationStart(R.id.vote_calenderBtn);
                calendarView.setMaxDate(System.currentTimeMillis());

                calender_okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long now = calendarView.getDate();
                        day_YYYYMMDD = df_korean.format(now);
                        vote_dateTv.setText(df_circle.format(now));
                        ad.cancel();
                    }
                });

                calender_cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long now = System.currentTimeMillis();
                        day_YYYYMMDD = df_korean.format(now);
                        vote_dateTv.setText(df_circle.format(now));
                        ad.cancel();
                    }
                });

                break;


        }
    }

    void getMain_Server() {
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getMain_Server()");
        client.get("http://onepercentserver.azurewebsites.net/OnePercentServer/main.do", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {    }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));
                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("main_result") + "";
                    JSONArray arr = new JSONArray(objStr);
                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject obj = (JSONObject) arr.get(i);

                        String question = (String) obj.get("question");
                        vote_questionTv.setText(question);

                        JSONArray exArr = (JSONArray) obj.get("example");
                        for (int z = 1; z <= 4; z++) {
                            JSONObject exObj = (JSONObject) exArr.get(0);
                            String ex = (String) exObj.get(z + "");
                            pref.setPreferences("oneday","ex"+z, ex);
                            vote_radio[z].setText(ex+"");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN", "e : " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "VoteFragment # getMain_Server # onFailure // statusCode : " + statusCode +  " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {  }
        });
    }


    void setVote_Server(String user_id, String vote_date, final String vote_answer) {
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);
        params.put("vote_date",vote_date);
        params.put("vote_answer",vote_answer);

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "MainFragment # getMain_Server()");
        client.get("http://onepercentserver.azurewebsites.net/OnePercentServer/insertVote.do",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {    }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));
                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("vote_result") + "";

                    JSONArray arr = new JSONArray(objStr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        String state = (String) obj.get("state");

                        if (state.equals("success")) {
                            Toast.makeText(mActivity, "투표 완료", Toast.LENGTH_SHORT).show();
                            pref.setPreferences("oneday","vote", vote_answer);

                        } else {
                            Toast.makeText(mActivity, "이미 투표 ", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SUN", "e : " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("SUN", "VoteFragment # setVote_Server # onFailure // statusCode : " + statusCode +  " , error : " + error.toString());
            }

            @Override
            public void onRetry(int retryNo) {  }
        });
    }


    void AnimationStart(int id){
        Animation anim =  new AlphaAnimation(0, 1);
        anim.setDuration(100);
        anim.setInterpolator(new LinearInterpolator());
        views.findViewById(id).startAnimation(anim);
    }
}

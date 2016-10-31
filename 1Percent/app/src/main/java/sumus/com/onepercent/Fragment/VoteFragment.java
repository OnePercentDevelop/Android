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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import sumus.com.onepercent.LoginActivity;
import sumus.com.onepercent.MainActivity;
import sumus.com.onepercent.Object.MySharedPreference;
import sumus.com.onepercent.R;


public class VoteFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{
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
    ImageButton vote_voteBtn;

    // 변수
    int vote_number = 0;
    MySharedPreference pref;

    public VoteFragment() {
    }

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

            if(mParam1.equals("calender")){
                Log.d("SUN", "calender select ~!!");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.fragment_vote, container, false);
        mActivity = getActivity();
        InitWidet();

        return views;

    }

    void InitWidet() {
        pref = new MySharedPreference(mActivity);
        vote_radiogroup = (RadioGroup) views.findViewById(R.id.vote_radiogroup);
        for(int i=1; i<5; i++){
            vote_radio[i] =(RadioButton) views.findViewById( radio[i]);
        }

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


        vote_voteBtn = (ImageButton) views.findViewById(R.id.vote_voteBtn);
        vote_voteBtn.setOnClickListener(this);

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
                if(vote_number<= 0){ // 보기 선택 안했을때
                    Toast.makeText(mActivity,"보기를 선택해 주세요",Toast.LENGTH_SHORT).show();
                }
                else if( pref.getPreferences("oneday","vote").equals(""))
                { // 투표 안했을 때
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

                    if(pref.getPreferences("user","userPhone").equals("")) { // 로그인 안했을 때
                        dialog_loginTv.setText("로그인 하셔야 투표가능합니다. \n로그인하시겠습니까?");
                        dialog_loginOkBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                ((MainActivity)MainActivity.mContext).finish();
                                ad.cancel();
                            }
                        });


                    }
                    else{
                        dialog_loginTv.setText( vote_number + "번으로 투표하시겠습니까?");
                        dialog_loginOkBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pref.setPreferences("oneday","vote",vote_number+"");

                                for(int i=1; i<5; i++){
                                    vote_radio[i].setEnabled(false);
                                }
                                vote_radio[vote_number].setChecked(true);
                                ad.cancel();
                            }
                        });

                    }
                }
                else{

                    Toast.makeText(mActivity,"이미 투표 하셨습니다",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void ChangeCalender(String select_date){
        Toast.makeText(mActivity, "ChangeCalender : "+ select_date, Toast.LENGTH_SHORT).show();
    }
}

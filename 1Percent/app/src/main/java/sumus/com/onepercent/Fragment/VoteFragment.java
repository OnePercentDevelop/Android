package sumus.com.onepercent.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import sumus.com.onepercent.Object.MySharedPreference;
import sumus.com.onepercent.R;


public class VoteFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // fragment
    public View views;
    Activity mActivity;

    // widget
    RadioGroup vote_radiogroup;
    RadioButton vote_radio1, vote_radio2, vote_radio3, vote_radio4;
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
        vote_radiogroup.setOnCheckedChangeListener(this);
        vote_radio1 = (RadioButton) views.findViewById(R.id.vote_radio1);
        vote_radio2 = (RadioButton) views.findViewById(R.id.vote_radio2);
        vote_radio3 = (RadioButton) views.findViewById(R.id.vote_radio3);
        vote_radio4 = (RadioButton) views.findViewById(R.id.vote_radio4);
        vote_voteBtn = (ImageButton) views.findViewById(R.id.vote_voteBtn);

        vote_voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vote_number<= 0){
                    Toast.makeText(mActivity,"보기를 선택해 주세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pref.getPreferences("user","userPhone").equals("")) {
                        Toast.makeText(mActivity, vote_number + "로그인하셔야 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mActivity, vote_number + "로 투표하시겠습니까?", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
}

package com.onepercent.sumus.onepercent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.Object.MySharedPreference;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText join_idEt, join_pwdEt, join_pwd1Et;
    TextView join_pwdCheckTv;
    Button join_joinBtn, join_cancleBtn;
    boolean ID_FLAG = true, PWD_FLAG= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        InitWidget();

    }
    void InitWidget(){
        join_idEt = (EditText)findViewById(R.id.join_idEt);
        join_pwd1Et = (EditText)findViewById(R.id.join_pwd1Et);
        join_pwdEt = (EditText)findViewById(R.id.join_pwdEt);
        join_pwdCheckTv = (TextView)findViewById(R.id.join_pwdCheckTv);
        join_cancleBtn = (Button)findViewById(R.id.join_cancleBtn);
        join_joinBtn = (Button)findViewById(R.id.join_joinBtn);

        join_joinBtn.setOnClickListener(this);
        join_pwdEt.addTextChangedListener(this);
        join_pwd1Et.addTextChangedListener(this);

        join_idEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ID_FLAG = false;
                ID_FLAG = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId())
        {
            case R.id.join_joinBtn:
                if(PWD_FLAG && ID_FLAG){

                    MySharedPreference pref = new MySharedPreference(getApplicationContext());
                    pref.setPreferences("user","userID",join_idEt.getText().toString());
                    pref.setPreferences("user","userPWD",join_pwdEt.getText().toString());

                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(!PWD_FLAG && ID_FLAG){
                    Toast.makeText(getApplicationContext(),"비밀번호를 다시 확인해 주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(PWD_FLAG && !ID_FLAG){
                    Toast.makeText(getApplicationContext(),"아이디 중확을 확인해 주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"입력값들을 확인해 주세요.",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.join_cancleBtn:

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String p = join_pwdEt.getText().toString();
        String p1 = join_pwd1Et.getText().toString();

        Log.d("SUN", "PWD "+join_pwdEt.getText() + " , "+ join_pwd1Et.getText());
        if(p.equals(p1)) {
            join_pwdCheckTv.setTextColor(getResources().getColor(R.color.colorWhite));
            join_pwdCheckTv.setText("비밀번호가 일치합니다.");
            join_pwdCheckTv.setVisibility(View.VISIBLE);
            PWD_FLAG= true;
        }
        else{
            join_pwdCheckTv.setText("비밀번호가 불일치합니다.");
            join_pwdCheckTv.setTextColor(getResources().getColor(R.color.colocRed));
            join_pwdCheckTv.setVisibility(View.VISIBLE);
            PWD_FLAG = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

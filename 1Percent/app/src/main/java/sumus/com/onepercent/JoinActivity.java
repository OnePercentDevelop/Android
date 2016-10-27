package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sumus.com.onepercent.Object.MySharedPreference;

public class JoinActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener{

    Context mContext;

    EditText join_phoneEt,join_pwd1Et,join_pwd2Et;
    TextView join_pwokTv;
    Button join_cancleBtn, join_okBtn;

    Boolean phone_flag= true;
    Boolean pwd_flag= false;

    MySharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mContext = this;
        InitWidget();
    }

    void InitWidget(){
        join_phoneEt = (EditText)findViewById(R.id.join_phoneEt);
        join_pwd1Et = (EditText)findViewById(R.id.join_pwd1Et);
        join_pwd2Et = (EditText)findViewById(R.id.join_pwd2Et);
        join_pwokTv = (TextView)findViewById(R.id.join_pwokTv);

        join_pwd1Et.addTextChangedListener(this);
        join_pwd2Et.addTextChangedListener(this);

        join_cancleBtn = (Button) findViewById(R.id.join_cancleBtn);
        join_okBtn = (Button) findViewById(R.id.join_okBtn);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(join_pwd1Et.getText().toString().equals(join_pwd2Et.getText().toString())) {
            join_pwokTv.setText("비밀번호가 일치합니다");
            pwd_flag = true;
        }
        else {
            join_pwokTv.setText("비밀번호가 일치하지 않습니다.");
            pwd_flag = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.join_okBtn:
                if(pwd_flag && phone_flag)
                {
                    pref.setPreferences("user","userPwd", join_phoneEt.getText().toString());
                    pref.setPreferences("user","userPhone",join_pwd2Et.getText().toString() );
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(!phone_flag && pwd_flag)
                {
                    Toast.makeText(mContext,"중복확인을 해주세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mContext,"다시한번 확인 해주세요",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.join_cancleBtn:

                break;
        }
    }
}

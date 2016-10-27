package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sumus.com.onepercent.Object.MySharedPreference;

public class LoginActivity extends AppCompatActivity {
    EditText login_phoneEt, longin_pwdEt;
    TextView login_joinTv;
    Button login_loginBtn;
    Context mContext;

    MySharedPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        InitWidget();
        pref = new MySharedPreference(mContext);
    }

    void InitWidget(){
        login_phoneEt = (EditText) findViewById(R.id.login_phoneEt);
        longin_pwdEt = (EditText) findViewById(R.id.longin_pwdEt);
        login_joinTv = (TextView) findViewById(R.id.login_joinTv);
        login_joinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_loginBtn = (Button)findViewById(R.id.login_loginBtn);
        login_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = login_phoneEt.getText().toString();
                String pwd = login_phoneEt.getText().toString();
                if(isPhoneNumber(phone) && isPassword(phone)){ // + 서버 연동
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    pref.setPreferences("user","userPwd", pwd);
                    pref.setPreferences("user","userPhone",phone );
                    startActivity(intent);
                }
                else{
                    Toast.makeText(mContext,"다시한번 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isPhoneNumber(String str){
        try{
            Integer.parseInt(str);
            if(str.length() == 11)
                return true;
            else
                return false;
        }catch (Exception e){
                  return false;
        }

    }

    boolean isPassword(String str){
        if(str.length()<= 4 && str.length()<=10)
            return true;
        else
            return false;
    }
}

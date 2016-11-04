package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import sumus.com.onepercent.Object.MySharedPreference;

public class LoginActivity extends FontBaseActvity {
    EditText login_phoneEt, longin_pwdEt;
    TextView login_joinTv;
    Button login_loginBtn;
    Context mContext;

    MySharedPreference pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                String pwd = longin_pwdEt.getText().toString();
                if(isPhoneNumber(phone) && isPassword(pwd)){ // + 서버 연동
                    getLogin_Server(phone,pwd);
                }
                else{
                    Toast.makeText(mContext,"다시한번 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        longin_pwdEt.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        longin_pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        if( 4 <= str.length() && str.length()<=10)
            return true;
        else
            return false;
    }

    void getLogin_Server(final String id, final String pwd) {
        //((MainActivity)MainActivity.mContext).progresscircle.setVisibility(View.VISIBLE);
        final RequestParams params = new RequestParams();
        params.put("user_id",id);
        params.put("user_password",pwd);
        Log.d("SUN","LoginActivity # getLogin_Server"+ id + " "+ pwd);
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "LoginActivity # getLogin_Server()");
        client.get("http://onepercentserver.azurewebsites.net/OnePercentServer/login.do",params,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("login_result") + "";

                    JSONArray arr = new JSONArray(objStr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        String state = (String) obj.get("state");

                        if (state.equals("success")) {
                            Toast.makeText(mContext, "로그인 완료", Toast.LENGTH_SHORT).show();
                            pref.setPreferences("user", "userPhone",id + "");
                            pref.setPreferences("user", "userPwd", pwd + "");

                            Intent intent = getIntent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
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

}

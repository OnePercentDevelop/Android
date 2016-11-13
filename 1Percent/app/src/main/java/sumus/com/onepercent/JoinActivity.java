package sumus.com.onepercent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import sumus.com.onepercent.Object.MySharedPreference;

public class JoinActivity extends FontBaseActvity implements TextWatcher, View.OnClickListener {


    // Activity
    Context mContext;
    final static int REQUEST_LOGIN = 1000;

    // Widget
    EditText join_phoneEt, join_pwd1Et, join_pwd2Et;
    TextView join_pwokTv;
    Button join_okBtn, join_loginBtn;

    Boolean phone_flag = true;
    Boolean pwd_flag = false;

    MySharedPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        InitWidget();
    }

    void InitWidget() {
        pref = new MySharedPreference(mContext);
        join_phoneEt = (EditText) findViewById(R.id.join_phoneEt);
        join_pwd1Et = (EditText) findViewById(R.id.join_pwd1Et);
        join_pwd2Et = (EditText) findViewById(R.id.join_pwd2Et);
        join_pwokTv = (TextView) findViewById(R.id.join_pwokTv);

        join_pwd1Et.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        join_pwd1Et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        join_pwd2Et.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        join_pwd2Et.setTransformationMethod(PasswordTransformationMethod.getInstance());

        join_pwd1Et.addTextChangedListener(this);
        join_pwd2Et.addTextChangedListener(this);

        join_okBtn = (Button) findViewById(R.id.join_okBtn);
        join_okBtn.setOnClickListener(this);

        join_loginBtn = (Button) findViewById(R.id.join_loginBtn);
        join_loginBtn.setOnClickListener(this);


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (join_pwd1Et.getText().toString().equals(join_pwd2Et.getText().toString())) {
            join_pwokTv.setText("비밀번호가 일치합니다");
            pwd_flag = true;
        } else {
            join_pwokTv.setText("비밀번호가 일치하지 않습니다.");
            pwd_flag = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_okBtn:

                String id = join_phoneEt.getText().toString();
                String pwd = join_pwd2Et.getText().toString();


                if (pwd_flag && phone_flag && (id.length()==11) ) {
                  // Toast.makeText(mContext, "회원가입 및 로그인이 되었습니다.", Toast.LENGTH_SHORT).show();


                   // FCMSetting();
                    long nowdate = System.currentTimeMillis(); // 현재시간
                    SimpleDateFormat df = new SimpleDateFormat("yyyy년MM월dd일");
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    setJoin_Server(id,pwd,deviceToken,df.format(nowdate).toString());

                } else if (!phone_flag && pwd_flag) {
                    Toast.makeText(mContext, "중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "다시한번 확인 해주세요", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.join_loginBtn:

                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivityForResult(intent,REQUEST_LOGIN);
                break;
        }
    }


    void setJoin_Server(final String id, final String pwd, String token, String date) {

        JSONObject json = new JSONObject();
        StringEntity entitiy = null;
        try{
            json.put("user_id",id);
            json.put("user_password",pwd);
            json.put("user_token",token);
            json.put("sign_date", date); // URLEncoder.encode(date, "UTF-8")
            entitiy = new StringEntity(json.toString());
            Log.d("SUN","JoinActivity # json.toString : "+json.toString());
        }catch (JSONException e){

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("SUN", "JoinActivity # getJoin_Server()");
        client.post(mContext,"http://onepercentserver.azurewebsites.net/OnePercentServer/insertUser.do",entitiy,  "application/json;",new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                Log.d("SUN", "statusCode : " + statusCode + " , response : " + new String(response));

                String res = new String(response);
                try {
                    JSONObject object = new JSONObject(res);
                    String objStr = object.get("sign_result") + "";

                    JSONArray arr = new JSONArray(objStr);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        String state = (String) obj.get("state");

                        if (state.equals("success")) {
                            Toast.makeText(mContext, "회원가입 완료", Toast.LENGTH_SHORT).show();
                            pref.setPreferences("user", "userPhone",id + "");
                            pref.setPreferences("user", "userPwd", pwd + "");

                            finish();
                        } else {
                            Toast.makeText(mContext, "이미 존재하는 번호입니다.", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_LOGIN:
                String select_date;
                if (resultCode == RESULT_OK) {
                    finish();
                } else {

                }

        }
    }

    void FCMSetting(){
        String deviceToken = FirebaseInstanceId.getInstance().getToken(); // Token - 디바이스 정보
        FirebaseMessaging.getInstance().subscribeToTopic("notice"); //  (notice)토픽명 그룹 전체 메세지 전송
        //Log.d("SUN","MainAcitivty # FCMSetting : " + deviceToken);
    }
}

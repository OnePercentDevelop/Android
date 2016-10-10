package com.onepercent.sumus.onepercent.FCM;

/**
 * Created by MINI on 2016-10-07.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onepercent.sumus.onepercent.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by on 2016-04-05.
 */
public class HttpUtil extends AsyncTask<String, String, String> {
    private Context context;

    public HttpUtil(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // 호출 전
        System.out.println("************************************************* 서버 호출 선행" );
    }


    @Override
    public String doInBackground(String... params) {

        Log.d("SUN","************************************************* 서버 호출");
        String paramString = "";
        String url = params[0];
        String param1Key = params[1].split(":")[0];
        String param1Value = params[1].split(":")[1];
        String param2Key = params[2].split(":")[0];
        String param2Value = params[2].split(":")[1];

        Log.d("SUN","************************************************* 서버 호출 url : " + url);
        paramString = param1Key  + "=" + param1Value + "&" + "REG"  + "=" + "dSnl0LXOBAA:APA91bH30JEu6csMBroqVgZXrX_aovOBW9kZJJ8QMJH1X95iaqrmpozxw3LPUKdsFOKvVCHHLx0jVUwjjzSp1z6RYbN1SCFtcLO68BVHq-hV5hoVmYIABArA7DbZOVdRrwEfQ4FJIXr9" ;
        Log.d("SUN","paramString :" + paramString);
        try {
            URL obj = new URL(url + "?" + paramString);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write( outputInBytes );
            os.close();

            int retCode = conn.getResponseCode();

            Log.d("SUN","************************************************* 서버 호출 결과 코드 : " + retCode );
            if (retCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                char[] buff = new char[512];
                int len = -1;
                String line;
                StringBuffer response = new StringBuffer();
                while((len = br.read(buff)) != -1) {
                    response.append(new String(buff, 0, len));
                }
                br.close();
                Log.d("SUN","************************************************* 서버 호출 결과 text : " + response.toString());

                return response.toString();
            }else{
                Log.d("SUN","************************************************* 서버 호출 실패 code : " + retCode );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 호출이 끝난 후
    @Override
    protected void onPostExecute(String result) {
        JSONObject object = null;
        try {
            object = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("SUN","************************************************* 서버 호출 후행");
        String rtn = "";
        try {
            // return 받은 Json 데이터
            rtn = URLDecoder.decode(object.getString("DATA"), "UTF-8");
            Toast.makeText( ((MainActivity)MainActivity.mContext),rtn,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}


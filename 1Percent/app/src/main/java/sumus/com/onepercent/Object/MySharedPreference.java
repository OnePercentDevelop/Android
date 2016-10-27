package sumus.com.onepercent.Object;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by MINI on 2016-10-09.
 */

public class MySharedPreference {
     /*
        oneday : 하루에 한번만 동기화 필요한 데이터            gift,  question, winner, ex1~4, giftImg
        fcm : fcm push 위한 데이터                          device, push
        user : 회원 로그인 관련 데이터                       userPhone, userPwd
     */

    static Context mContext;

    public MySharedPreference(Context c) {  mContext = c; }

    // 값 불러오기
    public String getPreferences(String preName, String key){
        SharedPreferences pref = mContext.getSharedPreferences(preName, Activity.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 값 저장하기
    public void setPreferences(String preName,String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences(preName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removePreferences(String preName, String key){
        SharedPreferences pref = mContext.getSharedPreferences(preName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(String preName){
        Log.d("SUN","MySharedPreference # "+preName + " is removeAll");
        SharedPreferences pref = mContext.getSharedPreferences(preName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}






   /* public static void put(Context mContext ,String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences("1%",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static String getValue(Context mContext, String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences("1%",
                Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public static void deleteValue(Context mContext ,String key){
        SharedPreferences pref = mContext.getSharedPreferences("1%",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove(key);
        editor.commit();
    }
    */

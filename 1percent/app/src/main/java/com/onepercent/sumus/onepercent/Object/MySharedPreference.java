package com.onepercent.sumus.onepercent.Object;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MINI on 2016-10-09.
 */

public class MySharedPreference {

    public final  String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
    public final static String PREF_MAIN_VALUE = "PREF_MAIN_VALUE";

    static Context mContext;

    public MySharedPreference(Context c) {  mContext = c; }

    // 값 불러오기
    public String getPreferences(String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 값 저장하기
    public void setPreferences(String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public void removePreferences(String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public void removeAllPreferences(){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Activity.MODE_PRIVATE);
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

package com.example.pruebatecnica.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MySettings {
    static SharedPreferences.Editor edit;

    public static SharedPreferences getSharedPref(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences("settingsClaro", Context.MODE_PRIVATE);
        return pref;
    }

    public static void setPrefVal(Context mContext, String key, String value) {
        if(key!=null){
            edit = getSharedPref(mContext).edit();
            edit.putString(key, value);
            edit.commit();
        }
    }

    public static void setIntPrefVal(Context mContext, String key, int value) {
        if(key!=null){
            edit = getSharedPref(mContext).edit();
            edit.putInt(key, value);
            edit.commit();
        }
    }

    public static void setDoublePrefVal(Context mContext, String key, String value) {
        if(key!=null){
            edit = getSharedPref(mContext).edit();
            edit.putString(key, value);
            edit.commit();
        }
    }

    public static void setLongPrefVal(Context mContext, String key, Long value) {
        if(key!=null){
            edit = getSharedPref(mContext).edit();
            edit.putLong(key, value);
            edit.commit();
        }
    }

    public static void setBooleanPrefVal(Context mContext, String key, boolean value) {
        if(key!=null){
            edit = getSharedPref(mContext).edit();
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public static String getPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        String val = "";
        try {
            if (pref.contains(key))
                val = pref.getString(key, "");
            else
                val = "";
        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public static int getIntPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        int val = 0;
        try {
            if(pref.contains(key)) val = pref.getInt(key, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public static Double getDoublePrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        String val = "0";
        try {
            if (pref.contains(key))
                val = pref.getString(key, "");
            else
                val = "0";
        }catch (Exception e){
            e.printStackTrace();
        }

        if( val.equals("")){
            val = "0";
        }
        return Double.parseDouble(val);
    }

    public static Long getLongPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        Long val = null;
        try{
            if(pref.contains(key)) val = pref.getLong(key, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public static boolean getBooleanPrefVal(Context mContext, String key) {
        SharedPreferences pref = getSharedPref(mContext);
        boolean val = false;
        try{
            if(pref.contains(key)) val = pref.getBoolean(key, false);

        }catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public static boolean containkey(Context mContext,String key)
    {
        SharedPreferences pref = getSharedPref(mContext);
        return pref.contains(key);
    }

    public static void clearData(Context mContext){
        getSharedPref(mContext).edit().clear().commit();
        MySettings.setBooleanPrefVal(mContext,"firstTime",true);
    }

    public static String getIconBase64(){
        String base64 = "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";
        return base64;
    }

}

package com.monster.monsterweather.MonApplication;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by ${MonsetrQiao} on 2016/11/3.
 * Describe:
 */
public class MonApplication extends Application{
    private String TAG = "MonsterTag";
    @Override
    public void onCreate() {
        ApiStoreSDK.init(this,"a2121ccc0e75f25e920efa3a3de9a262");
        super.onCreate();
    }
}

package com.monster.monsterweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.monster.monsterweather.Data.DataCity;
import com.monster.monsterweather.Tools.FromGson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuickActivity extends AppCompatActivity {
    public LocationClient locationClient =null;
    public BDLocationListener locationListener = new MyLocationListener();
    private String cityName,cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);

        initLocation();
        locationClient.start();//开始定位

    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            cityName = location.getCity();

            Log.e("MonsterTag","Location___cityName=="+cityName);
            cityName = cityName.replace("市","");
            Log.e("MonsterTag","Location___cityName=="+cityName);

            Parameters param = new Parameters();
            param.put("cityname", cityName);
            ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/cityinfo",
                    ApiStoreSDK.GET,
                    param,
                    new ApiCallBack() {
                        @Override
                        public void onSuccess(int i, String s) {
                            super.onSuccess(i, s);

                            DataCity dataCity = new DataCity();
                            FromGson fromGson = new FromGson();
                            dataCity = fromGson.getTianqi(s, dataCity.getClass());
                            cityCode = dataCity.retData.cityCode;
                            Intent intent = new Intent();
                            intent.setClass(QuickActivity.this,IndexActivity.class);
                            intent.putExtra("cityName", cityName);
                            intent.putExtra("cityCode", cityCode);
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                            QuickActivity.this.finish();

                        }

                        @Override
                        public void onError(int i, String s, Exception e) {
                            super.onError(i, s, e);

                        }

                        @Override
                        public void onComplete() {
                            super.onComplete();
                        }
                    });



        }
    }

}

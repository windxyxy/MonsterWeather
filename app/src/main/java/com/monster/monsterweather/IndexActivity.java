package com.monster.monsterweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.monster.monsterweather.Data.DataTianqi;
import com.monster.monsterweather.Tools.FromGson;

public class IndexActivity extends AppCompatActivity {
    private String TAG = "MonsterTag";
    private TextView tv_cityName, tv_tempreture, tv_aqi, tv_type, tv_today, tv_todayType, tv_todayTem, tv_gm, tv_fs, tv_yd;
    private int requestCode = 0x11;
    private String cityName;

    private void initView() {
        tv_cityName = (TextView) findViewById(R.id.tv_cityName);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_tempreture = (TextView) findViewById(R.id.tv_temprature);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_todayType = (TextView) findViewById(R.id.tv_todayType);
        tv_todayTem = (TextView) findViewById(R.id.tv_todayTem);
        tv_gm = (TextView) findViewById(R.id.tv_gm);
        tv_fs = (TextView) findViewById(R.id.tv_fs);
        tv_yd = (TextView) findViewById(R.id.yd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initView();

        Parameters param = new Parameters();
        Log.e(TAG,"cityName first= "+cityName);
        param.put("cityname", cityName);
        param.put("cityid", "101010100");
        ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/recentweathers",
                ApiStoreSDK.GET,
                param,
                new ApiCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        DataTianqi tianqi = new DataTianqi();
                        FromGson fromGson = new FromGson();
                        tianqi = fromGson.getTianqi(s, tianqi.getClass());

                        tv_cityName.setText(tianqi.retData.city);//城市
                        tv_tempreture.setText(tianqi.retData.today.curTemp);//温度
                        tv_aqi.setText("污染指数：" + tianqi.retData.today.aqi);//污染指数
                        tv_type.setText(tianqi.retData.today.type);//天气类型

                        tv_gm.setText(tianqi.retData.today.index.get(0).index);
                        tv_fs.setText(tianqi.retData.today.index.get(1).index);
                        tv_yd.setText(tianqi.retData.today.index.get(3).index);

                        tv_today.setText("今天");
                        tv_todayType.setText(tianqi.retData.today.type);
                        tv_todayTem.setText(tianqi.retData.today.hightemp + "/" + tianqi.retData.today.lowtemp);


                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                        Log.e("MonError", "Error Type = " + s);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    public void addCity(View view) {
        Intent intent = new Intent();
        intent.setClass(IndexActivity.this,AddCity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0x11:
                if (resultCode == RESULT_OK) {
                    String newCityName = data.getExtras().getString("cityName");
                    cityName = newCityName;
                    Log.e(TAG,"cityName result= "+cityName);

                    Parameters param = new Parameters();
                    Log.e(TAG,"cityName first= "+cityName);
                    param.put("cityname", cityName);
                    param.put("cityid", "101010100");
                    ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/recentweathers",
                            ApiStoreSDK.GET,
                            param,
                            new ApiCallBack() {
                                @Override
                                public void onSuccess(int i, String s) {
                                    super.onSuccess(i, s);
                                    DataTianqi tianqi = new DataTianqi();
                                    FromGson fromGson = new FromGson();
                                    tianqi = fromGson.getTianqi(s, tianqi.getClass());

                                    tv_cityName.setText(tianqi.retData.city);//城市
                                    tv_tempreture.setText(tianqi.retData.today.curTemp);//温度
                                    tv_aqi.setText("污染指数：" + tianqi.retData.today.aqi);//污染指数
                                    tv_type.setText(tianqi.retData.today.type);//天气类型

                                    tv_gm.setText(tianqi.retData.today.index.get(0).index);
                                    tv_fs.setText(tianqi.retData.today.index.get(1).index);
                                    tv_yd.setText(tianqi.retData.today.index.get(3).index);

                                    tv_today.setText("今天");
                                    tv_todayType.setText(tianqi.retData.today.type);
                                    tv_todayTem.setText(tianqi.retData.today.hightemp + "/" + tianqi.retData.today.lowtemp);


                                }

                                @Override
                                public void onError(int i, String s, Exception e) {
                                    super.onError(i, s, e);
                                    Log.e("MonError", "Error Type = " + s);
                                }

                                @Override
                                public void onComplete() {
                                    super.onComplete();
                                }
                            });
                }
        }
    }
}

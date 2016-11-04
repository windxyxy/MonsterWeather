package com.monster.monsterweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.monster.monsterweather.Data.DataCity;
import com.monster.monsterweather.Tools.FromGson;

public class AddCity extends AppCompatActivity {
    private EditText et_cityName;
    private String cityName, cityCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_citys);
        et_cityName = (EditText) findViewById(R.id.et_cityName);
    }

    public void addCityName(View view) {
        cityName = et_cityName.getText().toString().trim();
        if (cityName.equals("")){
            Toast.makeText(AddCity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Parameters param = new Parameters();
        param.put("cityname", cityName);
        Log.e("MonsterTag", "AddCity__cityName=" + cityName);
        ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/cityinfo",
                ApiStoreSDK.GET,
                param,
                new ApiCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        Log.e("MontserTag", "onSuccess   i = "+i);

                        DataCity dataCity = new DataCity();
                        FromGson fromGson = new FromGson();
                        dataCity = fromGson.getTianqi(s, dataCity.getClass());
                       try {
                            cityCode = dataCity.retData.cityCode;
                            Intent intent = new Intent();
                            intent.putExtra("cityName", cityName);
                            intent.putExtra("cityCode", cityCode);
                            AddCity.this.setResult(RESULT_OK, intent);
                            AddCity.this.finish();

                        } catch (Exception e){
                           Log.e("MonsterTag","抛出异常");
                             new AlertDialog.Builder(AddCity.this)
                                    .setTitle("提示")
                                    .setMessage("输入的地址有误，请重新输入！")
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                            return;
                        }


                    }

                    @Override
                    public void onError(int i, String s, Exception e) {
                        super.onError(i, s, e);
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCity.this)
                                .setTitle("提示")
                                .setMessage(s)
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }
}

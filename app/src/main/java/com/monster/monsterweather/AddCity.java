package com.monster.monsterweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCity extends AppCompatActivity {
    private EditText et_cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_citys);
        et_cityName = (EditText) findViewById(R.id.et_cityName);
        Intent intent = new Intent();

    }

    public void addCityName(View view) {
        Intent intent = new Intent();
        intent.setClass(AddCity.this,IndexActivity.class);
        String cityName = et_cityName.getText().toString().trim();
        intent.putExtra("cityName",cityName);
        AddCity.this.setResult(RESULT_OK,intent);
        AddCity.this.finish();
    }
}

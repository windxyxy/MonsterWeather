package com.monster.monsterweather.Data;

/**
 * Created by ${MonsetrQiao} on 2016/11/4.
 * Describe:输入城市名字查找城市编号数据
 */
public class DataCity {

    public int errNum;
    public String retMsg;
    public RetDataBean retData;

    public static class RetDataBean {
        public String cityName;
        public String provinceName;
        public String cityCode;
        public String zipCode;
        public String telAreaCode;
    }
}

package com.monster.monsterweather.Data;

import java.util.List;

/**
 * Created by ${MonsetrQiao} on 2016/11/3.
 * Describe:天气数据——带历史七天和未来四天
 */
public class DataTianqi {

    public int errNum;
    public String errMsg;
    public RetDataBean retData;

    public static class RetDataBean {
        public String city;
        public String cityid;
        public TodayBean today;
        public List<ForecastBean> forecast;
        public List<HistoryBean> history;

        public static class TodayBean {
            public String date;
            public String week;
            public String curTemp;
            public String aqi;
            public String fengxiang;
            public String fengli;
            public String hightemp;
            public String lowtemp;
            public String type;
            public List<IndexBean> index;

            public static class IndexBean {
                public String name;
                public String code;
                public String index;
                public String details;
                public String otherName;
            }
        }

        public static class ForecastBean {
            public String date;
            public String week;
            public String fengxiang;
            public String fengli;
            public String hightemp;
            public String lowtemp;
            public String type;
        }

        public static class HistoryBean {
            public String date;
            public String week;
            public String aqi;
            public String fengxiang;
            public String fengli;
            public String hightemp;
            public String lowtemp;
            public String type;
        }
    }
}

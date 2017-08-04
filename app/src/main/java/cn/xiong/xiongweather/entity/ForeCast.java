package cn.xiong.xiongweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ForeCast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String min;
        public String max;
    }

    public class More{

        @SerializedName("txt_d")
        public String info;
    }
}

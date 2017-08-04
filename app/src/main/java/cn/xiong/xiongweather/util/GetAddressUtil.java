package cn.xiong.xiongweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.xiong.xiongweather.db.City;
import cn.xiong.xiongweather.db.Country;
import cn.xiong.xiongweather.db.Province;
import cn.xiong.xiongweather.entity.Weather;

/**
 * Created by Administrator on 2017/8/3.
 */

public class GetAddressUtil {

    public static boolean getProvince(String json){
        if(!TextUtils.isEmpty(json)){
            try {
                JSONArray list = new JSONArray(json);
                for (int i = 0;i < list.length();i++){
                    JSONObject pro = list.getJSONObject(i);
                    Province province = new Province(pro.getString("name"),pro.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean getCountry(String json, int cityId){
        if(!TextUtils.isEmpty(json)){
            try {
                JSONArray list = new JSONArray(json);
                for (int i = 0;i < list.length();i++){
                    JSONObject pro = list.getJSONObject(i);
                    Country country = new Country(pro.getString("name"),pro.getString("weather_id"),cityId);
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean getCity(String json, int provinceId){
        if(!TextUtils.isEmpty(json)){
            try {
                JSONArray list = new JSONArray(json);
                for (int i = 0;i < list.length();i++){
                    JSONObject pro = list.getJSONObject(i);
                    City city = new City(pro.getString("name"),pro.getInt("id"),provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Weather getWeather(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weather = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weather,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package cn.xiong.xiongweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import cn.xiong.xiongweather.activity.WeatherActivity;
import cn.xiong.xiongweather.constant.Url;
import cn.xiong.xiongweather.entity.Weather;
import cn.xiong.xiongweather.util.GetAddressUtil;
import cn.xiong.xiongweather.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        updateWeather();
        updateBingPic();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        int updateTime = 8 * 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + updateTime;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,i,0);
        alarm.cancel(pendingIntent);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather",null);
        if(weatherString != null){
            Weather weather = GetAddressUtil.getWeather(weatherString);
            String url = Url.WEATHER + "?city="+weather.basic.weatherId+"&key=b876b342a6b64b098fc6a0ae20349257";
            HttpUtil.HttpGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    Weather weather = GetAddressUtil.getWeather(json);
                    if(weather != null && "ok".equals(weather.status)) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", json);
                        editor.apply();
                    }
                }
            });
        }
    }

    private void updateBingPic(){
        HttpUtil.HttpGet(Url.BINGPIC, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingpic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingpic);
                editor.apply();
            }
        });
    }
}

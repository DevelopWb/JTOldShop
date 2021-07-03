package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.bdmap.utils.DateUtil;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.WeatherBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends BaseActivity implements View.OnClickListener {
    private String province = "山东省";
    private String city = "临沂市", area = "河东区";
    private TextView address, date, time, realTimeTemp, dayTemp, skycon, aqi, windSpeed, windDir, hum;
    private ImageView back;
    private RelativeLayout weatherBg;
    public static final int SELECT_LOCATION = 9887;

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_weather;
    }

    @Override
    public void initView() {
        back = findViewById(R.id.weather_back);
        back.setOnClickListener(v -> {
            this.finish();
//            overridePendingTransition(R.anim.out_to_bottom,R.anim.out_to_bottom);
        });
        address = findViewById(R.id.weather_place);
        date = findViewById(R.id.weather_date);
        time = findViewById(R.id.weather_time);
        realTimeTemp = findViewById(R.id.real_time_temp);
        dayTemp = findViewById(R.id.day_temp);
        skycon = findViewById(R.id.skycon);
        weatherBg = findViewById(R.id.activity_bg);

        aqi = findViewById(R.id.aqi);
        windDir = findViewById(R.id.wind_dir);
        windSpeed = findViewById(R.id.wind_speed);
        hum = findViewById(R.id.humidity);
        getToolbar().setVisibility(View.GONE);
        address.setOnClickListener(this);

    }

    private void checkBg(String skycon) {
        if (DateUtil.getHour() < 18 && DateUtil.getHour() > 7) {
            switch (skycon) {
                case "CLEAR_DAY":
                    weatherBg.setBackgroundResource(R.drawable.sunny);
                    break;
                case "CLEAR_NIGHT":
                    weatherBg.setBackgroundResource(R.drawable.sunny_nightly);
                    break;
                case "PARTLY_CLOUDY_DAY":
                    weatherBg.setBackgroundResource(R.drawable.cloudy);
                    break;
                case "PARTLY_CLOUDY_NIGHT":
                    weatherBg.setBackgroundResource(R.drawable.cloudy_nightly);
                    break;
                case "CLOUDY":
                    weatherBg.setBackgroundResource(R.drawable.cloudy);
                    break;
                case "RAIN":
                    weatherBg.setBackgroundResource(R.drawable.rain);
                    break;
                case "SNOW":
                    weatherBg.setBackgroundResource(R.drawable.snow);
                    break;
                case "WIND":
                    weatherBg.setBackgroundResource(R.drawable.wind);
                    break;
                case "HAZE":
                    weatherBg.setBackgroundResource(R.drawable.haze);
                    break;
                default:
                    break;
            }
        } else {
            switch (skycon) {
                case "CLEAR_DAY":
                    weatherBg.setBackgroundResource(R.drawable.sunny_nightly);
                    break;
                case "CLEAR_NIGHT":
                    weatherBg.setBackgroundResource(R.drawable.sunny_nightly);
                    break;
                case "PARTLY_CLOUDY_DAY":
                    weatherBg.setBackgroundResource(R.drawable.cloudy_nightly);
                    break;
                case "PARTLY_CLOUDY_NIGHT":
                    weatherBg.setBackgroundResource(R.drawable.cloudy_nightly);
                    break;
                case "CLOUDY":
                    weatherBg.setBackgroundResource(R.drawable.cloudy_nightly);
                    break;
                case "RAIN":
                    weatherBg.setBackgroundResource(R.drawable.rain_nightly);
                    break;
                case "SNOW":
                    weatherBg.setBackgroundResource(R.drawable.snow_nightly);
                    break;
                case "WIND":
                    weatherBg.setBackgroundResource(R.drawable.wind_nightly);
                    break;
                case "HAZE":
                    weatherBg.setBackgroundResource(R.drawable.haze_nightly);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取天气信息
     */
    public void getWeather(String lat,String lon){
        AppNetModule.createrRetrofit()
                .weather(App.app.getAccount(),App.app.getUserToken(),lat,lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherBean>() {
                    @Override
                    public void accept(WeatherBean weatherBean) throws Exception {
                        //CLEAR_DAY,CLEAR_NIGHT,PARTLY_CLOUDY_DAY,PARTLY_CLOUDY_NIGHT,CLOUDY,RAIN,SNOW,WIND,HAZE
                        //int xb = getXb("SNOW");
                        int xb = getXb(weatherBean.getReturnValue().getResult().getSkycon());

                        date.setText(DateUtil.weekAndDate());
                        time.setText(DateUtil.timeAnd());
                        realTimeTemp.setText(String.valueOf(Math.round(weatherBean.getReturnValue().getResult().getTemperature())) + "℃");
                        aqi.setText("  " + switchPM25(weatherBean.getReturnValue().getResult().getAqi()));
                        skycon.setText(switchSkycon(weatherBean.getReturnValue().getResult().getSkycon()));
                        checkBg(weatherBean.getReturnValue().getResult().getSkycon());
                        windSpeed.setText("风速 "
                                + String.valueOf(Math.round(weatherBean.getReturnValue().getResult().getWind().getSpeed() / 3.6))
                                + "M/秒");
                        windDir.setText("风向 "
                                + switchWindDir(weatherBean.getReturnValue().getResult().getWind().getDirection()));
                        hum.setText("湿度 "
                                + (Math.round(weatherBean.getReturnValue().getResult().getHumidity() * 100))
                                + "%RH");

                        dayTemp.setText(String.valueOf(Math.round(weatherBean.getReturnValue().getResult().getTemperature()))
                                + "℃/"
                                + String.valueOf(Math.round(weatherBean.getReturnValue().getResult().getApparent_temperature()))
                                + "℃");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(throwable.toString());
                    }
                });
    }

    /**
     * 获取对应下标
     * @param skycon
     * @return
     */
    public int getXb(String skycon){
        if (skycon.indexOf("CLEAR") > -1 || skycon.indexOf("SUNNY") > -1){
            //晴
            return 0;
        }else if (skycon.indexOf("PARTLY_CLOUDY") > -1){
            //多云
            return 1;
        }else if (skycon.equals("CLOUDY")){
            //阴
            return 2;
        }else if (skycon.equals("RAIN")){
            //雨
            return 3;
        }else if (skycon.equals("SNOW")){
            //雪
            return 4;
        }else if (skycon.equals("WIND")){
            //风
            return 5;
        }else if (skycon.equals("HAZE") || skycon.equals("SMOG")){
            //雾霾沙尘
            return 6;
        }
        return 0;
    }

    private String switchPM25(int pm25) {
        if (pm25 < 51)
            return "空气优";
        else if (pm25 >= 51 && pm25 < 101)
            return "空气良";
        else if (pm25 >= 101 && pm25 < 151)
            return "空气轻度污染";
        else if (pm25 >= 151 && pm25 < 201)
            return "空气中度污染";
        else if (pm25 >= 201 && pm25 < 300)
            return "空气重度污染";
        else if (pm25 >= 300)
            return "空气严重污染";
        else
            return null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_LOCATION && resultCode == RESULT_OK) {
            String selectedProvince = data.getStringExtra("province");
            String selectedCity = data.getStringExtra("city");
            String selectedArea = data.getStringExtra("area");
            String lat = data.getStringExtra("lat");
            String lon = data.getStringExtra("lon");
            city = selectedCity;
            province = selectedProvince;
            area = selectedArea;
            getWeather(lat,lon);
            if ("无".equals(selectedArea)) {
                address.setText(selectedProvince + " " + selectedCity);
            } else
                address.setText(selectedCity + " " + selectedArea);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String switchSkycon(String skycon) {
        switch (skycon) {
            case "CLEAR_DAY":
                return "晴";
            case "CLEAR_NIGHT":
                return "晴";
            case "PARTLY_CLOUDY_DAY":
                return "多云";

            case "PARTLY_CLOUDY_NIGHT":
                return "多云";

            case "CLOUDY":
                return "阴";

            case "RAIN":
                return "雨";

            case "SNOW":
                return "雪";

            case "WIND":
                return "风";
            case "HAZE":
                return "雾霾沙尘";
            default:
                return "无";
        }
    }

    private String switchWindDir(double windDir) {

        if (windDir < 22.5)
            return "北风";
        else if (windDir < 67.5)
            return "东北风";
        else if (windDir < 112.5)
            return "东风";
        else if (windDir < 157.5)
            return "东南风";
        else if (windDir < 202.5)
            return "南风";
        else if (windDir < 247)
            return "西南风";
        else if (windDir < 292.5)
            return "西风";
        else if (windDir < 337.5)
            return "西北风";
        else
            return "北风";
    }


    @Override
    public void initData() {
//        Intent intent = getIntent();
        province = App.app.getBdLocation().getProvince();
        city = App.app.getBdLocation().getCity();
        area = App.app.getBdLocation().getDistrict();
        getWeather(String.valueOf(App.app.getBdLocation().getLatitude()),
                String.valueOf(App.app.getBdLocation().getLongitude()));
        address.setText(province + " " + city);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.in_from_bottom, R.anim.no_slide);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.weather_place) {
            startActivityForResult(new Intent(mContext, CitySelectActivity.class).putExtra("city", city), SELECT_LOCATION);
        }
    }
}

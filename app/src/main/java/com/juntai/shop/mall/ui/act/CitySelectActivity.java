package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.PlaceBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CitySelectActivity extends BaseActivity {
    Spinner first, second, third;
    TextView dangqiandiqu;
    ArrayAdapter firstAdapter;
    ArrayAdapter secondAdapter;
    ArrayAdapter thirdAdapter;
    ArrayList<PlaceBean.ReturnValueBean> dataProvince = new ArrayList<>();
    ArrayList<PlaceBean.ReturnValueBean> dataCity = new ArrayList<>();
    ArrayList<PlaceBean.ReturnValueBean> dataArea = new ArrayList<>();
    ArrayList<String> provinceList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> areaList = new ArrayList<>();

    @Override
    public int getLayoutView() {
        return R.layout.activity_city_select;
    }

    @Override
    public void initView() {
        setTitleName("地区选择");
        first = findViewById(R.id.firstspinner);
        second = findViewById(R.id.secondspinner);
        third = findViewById(R.id.thirdspinner);
        dangqiandiqu = findViewById(R.id.dangqiandiqu);
        findViewById(R.id.confirm).setOnClickListener(v -> {
            setResult(RESULT_OK, new Intent()
                    .putExtra("province", first.getSelectedItem().toString())
                    .putExtra("city", second.getSelectedItem().toString())
                    .putExtra("area", third.getSelectedItem().toString())
                    .putExtra("lat", String.valueOf(dataArea.get(third.getSelectedItemPosition()).getLatitude()))
                    .putExtra("lon", String.valueOf(dataArea.get(third.getSelectedItemPosition()).getLongitude())));
            finish();
        });
        /*---------------------------------------------------------------------------------------------*/
        Intent intent = getIntent();
        String place = intent.getStringExtra("city");
        dangqiandiqu.setText("[" + place + "]");
        /*---------------------------------------------------------------------------------------------*/
        firstAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item,provinceList);
        secondAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, cityList);
        thirdAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, areaList);
        getDate(0,0,firstAdapter,provinceList,dataProvince);
        first.setAdapter(firstAdapter);
        second.setAdapter(secondAdapter);
        third.setAdapter(thirdAdapter);
        first.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDate(1,dataProvince.get(position).getCityNum(),secondAdapter,cityList,dataCity);
                second.setVisibility(View.VISIBLE);
                third.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        second.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDate(2,dataCity.get(position).getCityNum(),thirdAdapter,areaList,dataArea);
                third.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 获取地址数据
     */
    public void getDate(int selector,long cityNum,ArrayAdapter arrayAdapter,ArrayList<String> stringArrayList,ArrayList<PlaceBean.ReturnValueBean> arrayList){
        Observable observable = null;
        switch (selector){
            case 0://省，直辖市
                observable = AppNetModule.createrRetrofit().getProvince();
                break;
            case 1://市
                //selectorProvince = pos;
                observable = AppNetModule.createrRetrofit().getCity(cityNum);
                break;
            case 2://县
                //selectorCity = pos;
                observable = AppNetModule.createrRetrofit().getArea(cityNum);
                break;
        }
        if (observable == null)
            return;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PlaceBean>(null) {
                    @Override
                    public void onSuccess(PlaceBean placeBean) {
                        stringArrayList.clear();
                        arrayList.clear();
                        arrayList.addAll(placeBean.getReturnValue());
                        for (PlaceBean.ReturnValueBean bean:placeBean.getReturnValue()) {
                            stringArrayList.add(bean.getName());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String msg) {
                    }
                });
    }
}

package com.juntai.shop.mall.ui.address.selector;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.promeg.pinyinhelper.Pinyin;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.PlaceBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 地址选择器
 * @aouther Ma
 * @date 2019/12/10
 */
public class AddressSelectorDialog extends BottomDialogFragment implements View.OnClickListener {
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerView;
    AddressSelectorAdapter selectorAdapter;
    //四级的数据(除了省，都是改变的)
    ArrayList<PlaceBean.ReturnValueBean> provinceList = new ArrayList<>();
    ArrayList<PlaceBean.ReturnValueBean> cityList = new ArrayList<>();
    ArrayList<PlaceBean.ReturnValueBean> countyList = new ArrayList<>();
    ArrayList<PlaceBean.ReturnValueBean> townList = new ArrayList<>();
    //省，市，县，镇(街道)
    int selectorProvince,selectorCity,selectorCounty,selectorTown;
    //当前是省，市，县，街道？
    int nowSelector = 0;
    //最后一个位置
    int lastPosition = -1;
    //完成选择
    boolean isSelected = false;
    //
    RadioButton radioProvince,radioCity,radioCounty,radioTown;
    PlaceBean.ReturnValueBean strProvince = new PlaceBean.ReturnValueBean(),strCity = new PlaceBean.ReturnValueBean(),strCounty = new PlaceBean.ReturnValueBean(),strTown = new PlaceBean.ReturnValueBean();
    //
    LinearLayout layoutCity,layoutCounty,layoutTown;
    ImageView image_city,image_county,image_town;
    //
    LinearSmoothScroller smoothScroller;
    @Override
    public int setView() {
        return R.layout.address_selector_layout;
    }

    @Override
    public void initView(View view) {
        smartRefreshLayout = view.findViewById(R.id.addressSmartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);
        image_city = view.findViewById(R.id.image_city);
        image_county = view.findViewById(R.id.image_county);
        image_town = view.findViewById(R.id.image_town);
        layoutCity = view.findViewById(R.id.layout_city);
        layoutCounty = view.findViewById(R.id.layout_county);
        layoutTown = view.findViewById(R.id.layout_town);
        recyclerView = view.findViewById(R.id.addressRecyclerView);
        radioProvince = view.findViewById(R.id.selector_province);
        radioCity = view.findViewById(R.id.selector_city);
        radioCounty = view.findViewById(R.id.selector_county);
        radioTown = view.findViewById(R.id.selector_town);

        radioProvince.setOnClickListener(this);
        radioCity.setOnClickListener(this);
        radioCounty.setOnClickListener(this);
        radioTown.setOnClickListener(this);
        view.findViewById(R.id.selector_close).setOnClickListener(v -> dismiss());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        selectorAdapter = new AddressSelectorAdapter(R.layout.item_address_selector,new ArrayList());
        recyclerView.setAdapter(selectorAdapter);
        smoothScroller = new LinearSmoothScroller(getActivity()){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        selectorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                nowSelector ++;
                isSelected = false;
                switch (nowSelector){
                    case 1://省列表点击
                        selectorProvince = position;
                        selectorCity = 0;
                        selectorCounty = 0;
                        selectorTown = 0;
                        strProvince = provinceList.get(position);
                        radioProvince.setText(strProvince.getName());
                        getDate(cityList);//请求市的列表
                        hideLayout(layoutCounty,image_county,radioCounty);
                        hideLayout(layoutTown,image_town,radioTown);
                        layoutCity.setVisibility(View.VISIBLE);
                        //获取界面显示的item个数
                        if (lastPosition == -1){
                            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                            lastPosition = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() - 2;
                        }
                        setRadioCheck(radioCity);
                        break;
                    case 2://市列表点击
                        selectorCity = position;
                        selectorCounty = 0;
                        selectorTown = 0;
                        strCity = cityList.get(position);
                        radioCity.setText(strCity.getName());
                        getDate(countyList);
                        image_city.setImageResource(R.drawable.ic_address_tag_center);
                        hideLayout(layoutCounty,image_county,radioCounty);
                        hideLayout(layoutTown,image_town,radioTown);
                        layoutCounty.setVisibility(View.VISIBLE);
                        setRadioCheck(radioCounty);
                        break;
                    case 3://区县列表点击
                        selectorCounty = position;
                        selectorTown = 0;
                        strCounty = countyList.get(position);
                        radioCounty.setText(strCounty.getName());
                        getDate(townList);
                        image_county.setImageResource(R.drawable.ic_address_tag_center);
                        hideLayout(layoutTown,image_town,radioTown);
                        layoutTown.setVisibility(View.VISIBLE);
                        setRadioCheck(radioTown);
                        break;
                    case 4://镇-街道列表点击
                        //只是记录最小级别的位置
                        selectorTown = position;
                        image_town.setImageResource(R.drawable.ic_address_tag_bottom);
                        strTown = townList.get(position);
                        radioTown.setText(strTown.getName());
                        setRadioCheck(radioTown);
                        isSelected = true;
                        nowSelector = 3;
                        goBack();
                        break;
                }
                //getDate(position);放在这里，最后一次点击街道item会触发
                recyclerView.scrollToPosition(0);
            }
        });

        if (isSelected){
            radioProvince.setText(strProvince.getName());
            radioCity.setText(strCity.getName());
            radioCounty.setText(strCounty.getName());
            radioTown.setText(strTown.getName());
            image_city.setImageResource(R.drawable.ic_address_tag_center);
            image_county.setImageResource(R.drawable.ic_address_tag_center);
            layoutCity.setVisibility(View.VISIBLE);
            layoutCounty.setVisibility(View.VISIBLE);
            layoutTown.setVisibility(View.VISIBLE);
            radioTown.setChecked(true);
            refreshData(townList);
        }else {
            nowSelector = 0;
            getDate(provinceList);
            //selectorProvince = 0;
            //selectorCity = 0;
            //selectorCounty = 0;
            //selectorTown = 0;
            //ortherList.clear();
        }
        //Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(getActivity())));
    }

    /**
     * 返回
     */
    public void goBack(){
        if (onSelectorListener != null){
            onSelectorListener.onSelector(strProvince,strCity,strCounty,strTown);
            dismiss();
        }
    }
    /**
     * 隐藏-清除
     * @param linearLayout
     * @param imageView
     * @param radioButton
     */
    public void hideLayout(LinearLayout linearLayout, ImageView imageView, RadioButton radioButton){
        radioButton.setChecked(false);
        radioButton.setText("请选择");
        imageView.setImageResource(R.drawable.ic_address_tag_bottom_sel);
        linearLayout.setVisibility(View.GONE);
    }

    /**
     * radiobutton状态清理
     * @param rbt
     */
    public void setRadioCheck(RadioButton rbt){
        radioCity.setChecked(false);
        radioCounty.setChecked(false);
        radioTown.setChecked(false);
        radioProvince.setChecked(false);
        //
        rbt.setChecked(true);
    }

    @Override
    public int dialogHeight() {
        return 0;
    }

    /**
     * 点击头部时刷新对应的数据
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.selector_province) {
            clickS(0,radioProvince,provinceList,selectorProvince);
        } else if (i == R.id.selector_city) {
            clickS(1,radioCity,cityList,selectorCity);
        } else if (i == R.id.selector_county) {
            clickS(2,radioCounty,countyList,selectorCounty);
        } else if (i == R.id.selector_town) {
            clickS(3,radioTown,townList,selectorTown);
        }
//        selectorAdapter.selectorPosition(0);
//        recyclerView.smoothScrollToPosition(0);
    }

    public void clickS(int now,RadioButton radio,ArrayList<PlaceBean.ReturnValueBean> list,int select){
        nowSelector = now;
        setRadioCheck(radio);
        selectorAdapter.replaceData(list);
        smoothScroller.setTargetPosition(select);
        selectorAdapter.selectorPosition(select);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (!isSelected){
            nowSelector --;
        }
    }

    /**
     * 刷新替换数据
     * @param list
     */
    private void refreshData(List<PlaceBean.ReturnValueBean> list){
        selectorAdapter.replaceData(list);
    }

    /**
     * 事件
     */
    OnSelectorListener onSelectorListener;

    public void setOnSelectorListener(OnSelectorListener onSelectorListener) {
        this.onSelectorListener = onSelectorListener;
    }

    public interface OnSelectorListener{
        void onSelector(PlaceBean.ReturnValueBean province, PlaceBean.ReturnValueBean city, PlaceBean.ReturnValueBean area, PlaceBean.ReturnValueBean street);
    }

    /**
     * 获取地址数据
     */
    private void getDate(ArrayList<PlaceBean.ReturnValueBean> arrayList){
        Observable observable = null;
        arrayList.clear();
        switch (nowSelector){
            case 0://省，直辖市
                if (provinceList.size() == 0){
                    observable = AppNetModule.createrRetrofit().getProvince();
                }else {
                    //selectorAdapter
                    refreshData(provinceList);
                    return;
                }
                break;
            case 1://市
                //selectorProvince = pos;
                observable = AppNetModule.createrRetrofit().getCity(strProvince.getCityNum());
                break;
            case 2://县
                //selectorCity = pos;
                observable = AppNetModule.createrRetrofit().getArea(strCity.getCityNum());
                break;
            case 3://街道
                //selectorCounty = pos;
                observable = AppNetModule.createrRetrofit().getStreet(strCounty.getCityNum());
                break;
        }
        if (observable == null)
            return;
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PlaceBean>() {
                    @Override
                    public void onSuccess(PlaceBean placeBean) {
                        if (nowSelector == 0){
                            provinceList.addAll(placeBean.getReturnValue());
                            pinyin(provinceList);
                            refreshData(provinceList);
                        }else {
                            arrayList.addAll(placeBean.getReturnValue());
                            pinyin(arrayList);
                            refreshData(arrayList);
                        }
                        //没有下一级，返回数据
                        if (placeBean.getReturnValue().size() == 0 && nowSelector == 3){//没有街道
                            strTown = null;
                            goBack();
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(App.app,msg);
                    }
                });
    }

    /**
     * 获取首字母-并排序
     * @param list
     */
    private void pinyin(ArrayList<PlaceBean.ReturnValueBean> list){
        for (PlaceBean.ReturnValueBean bean : list) {
            bean.setPinYin((Pinyin.toPinyin(bean.getName().charAt(0))));
            bean.setPinYin(bean.getPinYin().substring(0,1));
        }
        //字母排序
        Collections.sort(list, (o1, o2) -> (o1.getPinYin().compareTo(o2.getPinYin())));
    }
}

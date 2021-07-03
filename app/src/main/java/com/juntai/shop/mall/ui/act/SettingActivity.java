package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.view.View;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.MenuDialog;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.WeatherBean;
import com.juntai.shop.mall.ui.address.AddressListActivity;
import com.juntai.shop.mall.utils.CleanDataUtils;
import com.juntai.shop.mall.utils.UpdateUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @aouther Ma
 * @date 2019/3/6
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    MenuDialog menuDialog;
    String[] menu = new String[]{"确认要清除缓存吗？","确认","取消"};
    @Override
    public int getLayoutView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setTitleName("个人设置");
        findViewById(R.id.setting_pass).setOnClickListener(this);
        findViewById(R.id.setting_place).setOnClickListener(this);
        findViewById(R.id.setting_phone).setOnClickListener(this);
        findViewById(R.id.setting_clear).setOnClickListener(this);
        findViewById(R.id.setting_update).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        menuDialog = new MenuDialog();
        menuDialog.setOnItemClickListener(new MenuDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 1:
                        String clearSize = CleanDataUtils.getTotalCacheSize(Objects.requireNonNull(mContext));
                        CleanDataUtils.clearAllCache(Objects.requireNonNull(mContext));
                        ToastUtils.toast(mContext,"清理掉" + clearSize );
                        break;
                    case 2:
                        menuDialog.dismiss();
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_pass://修改密码
                App.app.activityTool.toPassChange(mContext,null,2);
                break;
            case R.id.setting_place://收货地址
                startActivity(new Intent(mContext, AddressListActivity.class));
                break;
            case R.id.setting_phone://修改手机号
                startActivity(new Intent(mContext,PhoneChangeActivity.class));
                break;
            case R.id.setting_clear://清理
                menuDialog.showMenu(getSupportFragmentManager(), Arrays.asList(menu));
                break;
            case R.id.setting_update://更新
                new UpdateUtils().update(mContext,true);
                break;
            case R.id.setting_about://关于
                startActivity(new Intent(mContext,AboutActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

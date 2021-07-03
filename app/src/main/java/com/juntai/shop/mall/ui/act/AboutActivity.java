package com.juntai.shop.mall.ui.act;

import android.widget.TextView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.utils.AppUtils;


/**
 * 关于
 * Created by Ma
 * on 2019/10/20
 */
public class AboutActivity extends BaseActivity {
    TextView textView,tvPhone;
    @Override
    public int getLayoutView() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        setTitleName("关于我们");
        textView = findViewById(R.id.version_text);
        tvPhone = findViewById(R.id.version_phone);
        textView.setText("当前版本：" + AppUtils.getVersionName(mContext));
//        tvPhone.setText("联系我们：" + App.app.customerServicePhone);
    }

    @Override
    public void initData() {

    }
}

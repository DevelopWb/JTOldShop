package com.juntai.shop.mall.ui.after_sales;

import android.view.View;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.ui.dialog.PromptDialog;
import com.juntai.shop.mall.utils.AppUtils;

/**
 * @aouther Ma
 * @date 2019/3/6
 */
public class AfterSalesActivity extends BaseActivity implements View.OnClickListener {
    int orderId;
    String shopAccount,name;
    PromptDialog promptDialog = new PromptDialog();
    @Override
    public int getLayoutView() {
        return R.layout.activity_after_sales;
    }

    @Override
    public void initView() {
        setTitleName("申请售后");
        orderId = getIntent().getIntExtra("id",-1);
        shopAccount = getIntent().getStringExtra("ryid");
        name = getIntent().getStringExtra("name");
        findViewById(R.id.after_sales_tui).setOnClickListener(this);
        findViewById(R.id.after_sales_huan).setOnClickListener(this);
        findViewById(R.id.after_sales_shang).setOnClickListener(this);
        findViewById(R.id.after_sales_kefu).setOnClickListener(this);
        promptDialog.setMessage(String.format("是否拨打%s", App.app.customerServicePhone),"取消","拨打");
        promptDialog.setOkClickListener(new PromptDialog.OnOkClickListener() {
            @Override
            public void onClick() {
                AppUtils.callPhone(mContext,App.app.customerServicePhone);
            }

            @Override
            public void cancle() {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.after_sales_tui://退货，退款
                App.app.activityTool.toReturnSelectActivity(mContext,getIntent());
                break;
            case R.id.after_sales_huan://换货
                App.app.activityTool.toReturnGoodsActivity(mContext,1,getIntent());
                break;
            case R.id.after_sales_shang://商家
                ModuleIm_Init.chat(mContext,shopAccount,name);
                break;
            case R.id.after_sales_kefu://客服
                if (App.app.customerServicePhone != null && !App.app.customerServicePhone.isEmpty()){
                    promptDialog.show(getSupportFragmentManager(),"call");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

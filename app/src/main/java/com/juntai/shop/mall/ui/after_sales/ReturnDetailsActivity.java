package com.juntai.shop.mall.ui.after_sales;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.mall.im.UserIM;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.bean.ReturnDetailsBean;
import com.juntai.shop.mall.ui.after_sales.adt.ReturnGoodsAdapter;
import com.juntai.shop.mall.ui.dialog.PromptDialog;
import com.juntai.shop.mall.utils.StringTools;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 售后详情
 * Created by Ma
 * on 2019/12/23
 */
public class ReturnDetailsActivity extends BaseActivity implements View.OnClickListener {
    TextView tvName,tvPrice,tvCase,tvPricc2,tvTime,tvNumber,tvCount,tvTime1,tvTime2,tvTime3,tvStatus;
    RecyclerView recyclerView;
    ReturnDetailsBean.ReturnValueBean valueBean;
    PromptDialog promptDialog = new PromptDialog();
    ReturnGoodsAdapter goodsAdapter;
    ImageView imageView1,imageView2;
    int returnid;
    @Override
    public int getLayoutView() {
        return R.layout.activity_return_details;
    }

    @Override
    public void initView() {
        setTitleName("退款详情");
        returnid = getIntent().getIntExtra("returnId",-1);
        if (returnid == -1)
            finish();

        tvName = findViewById(R.id.return_details_name);
        tvPrice = findViewById(R.id.return_details_price);
        tvCase = findViewById(R.id.return_details_case);
        tvPricc2 = findViewById(R.id.return_details_price2);
        tvTime = findViewById(R.id.return_details_time);
        tvNumber = findViewById(R.id.return_details_number);
        tvCount = findViewById(R.id.return_details_count);
        tvTime1 = findViewById(R.id.return_details_time1);
        tvTime2 = findViewById(R.id.return_details_time2);
        tvTime3 = findViewById(R.id.return_details_time3);
        tvStatus = findViewById(R.id.return_details_status);
        imageView1 = findViewById(R.id.return_details_image1);
        imageView2 = findViewById(R.id.return_details_image2);
        findViewById(R.id.return_details_im).setOnClickListener(this);
        findViewById(R.id.return_details_call).setOnClickListener(this);

        recyclerView = findViewById(R.id.returninfo_list);
        goodsAdapter = new ReturnGoodsAdapter(R.layout.item_return_goods,new ArrayList());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(goodsAdapter);
    }

    @Override
    public void initData() {
        AppNetModule.createrRetrofit()
                .returnInfo(MyApp.app.getAccount(), MyApp.app.getUserToken(),returnid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ReturnDetailsBean>() {
                    @Override
                    public void onSuccess(ReturnDetailsBean result) {
                        valueBean = result.getReturnValue();
                        goodsAdapter.addData(valueBean.getReturnCommodity());

                        tvName.setText(valueBean.getShopName());
                        tvPrice.setText(String.format("￥%s",valueBean.getReturnPrice()));
                        tvPricc2.setText(String.format("￥%s",valueBean.getReturnPrice()));
                        tvCase.setText(valueBean.getCauseName());
                        tvTime.setText(valueBean.getRefundTime());
                        tvNumber.setText(valueBean.getOrderFormNumber());
                        //申请退款时间
                        if (valueBean.getRefundTime() != null){
                            tvTime1.setText(String.format("申请退款\n%s",valueBean.getRefundTime()));
                        }
                        //受理时间
                        if (valueBean.getMerchantConfirmTime() != null){
                            tvTime2.setText(String.format("退款受理\n%s",valueBean.getMerchantConfirmTime()));
                            //商家是否同意退款状态（0同意；1拒绝）
                            imageView1.setImageResource(valueBean.getMerchantIsAgreeStatus() == 0
                                    ? R.mipmap.ic_return_details_center_success
                                    : R.mipmap.ic_return_details_right_error);
                            tvStatus.setText(valueBean.getMerchantIsAgreeStatus() == 0?"退款中":"申请失败");
                        }
                        //退款时间
                        if (valueBean.getUltimatelyTime() != null){
                            tvTime3.setText(String.format("卖家退款\n%s",valueBean.getUltimatelyTime()));
                            tvStatus.setText(valueBean.getUltimatelyStatus() == 0 ? "退款成功" : "退款失败");
                            //状态（0同意；1拒绝）
                            imageView2.setImageResource(valueBean.getUltimatelyStatus() == 0
                                    ? R.mipmap.ic_return_details_right_success
                                    : R.mipmap.ic_return_details_right_error);
                        }

                        int count = 0;
                        for (OrderCommodityListBean bean:valueBean.getReturnCommodity()) {
                            count += bean.getCommodityNumber();
                        }
                        tvCount.setText(String.valueOf(count));
                        //
                        ModuleIm_Init.setUser(new UserIM(valueBean.getShopAccount(),valueBean.getShopName() , StringTools.getImageForCrmInt(valueBean.getShopUserId())));
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (valueBean == null)return;
        switch (v.getId()){
            case R.id.return_details_im://im
                ModuleIm_Init.chat(mContext,valueBean.getShopAccount(),valueBean.getShopName());
                break;
            case R.id.return_details_call://电话
                if (valueBean.getPhone() != null && !valueBean.getPhone().isEmpty()){
                    promptDialog.setMessage("是否拨打\n"+valueBean.getPhone(),"取消","拨打");
                    promptDialog.show(getSupportFragmentManager(),"call");
                }
                break;
        }
    }
}

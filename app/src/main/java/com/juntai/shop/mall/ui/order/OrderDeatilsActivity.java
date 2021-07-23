package com.juntai.shop.mall.ui.order;

import android.view.View;
import android.widget.EditText;
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
import com.juntai.shop.mall.bean.OrderInfoBean;
import com.juntai.shop.mall.ui.dialog.PromptDialog;
import com.juntai.shop.mall.mine.adt.MyOrderGoodsAdapter;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.StringTools;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单详情
 * Created by Ma
 * on 2019/12/9
 */
public class OrderDeatilsActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    MyOrderGoodsAdapter myOrderGoodsAdapter;
    TextView tvStatus,tvName,tvPrice,tvPrice1,tvPrice2,tvTimeCreate,tvTimeFK,tvTimeFH,tvCount;
    EditText editNum;
    int orderid;
    HashMap<Integer, String> mapStr = new HashMap<>();
    OrderInfoBean.ReturnValueBean orderInfoBean;
    PromptDialog promptDialog = new PromptDialog();
    @Override
    public int getLayoutView() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(false).init();
        mBaseRootCol.setFitsSystemWindows(false);
        setTitleName("订单详情");
        setToolbarBg(R.mipmap.my_center_red_bg);
        orderid = getIntent().getIntExtra("orderId",-1);
        if (orderid == -1)
            finish();
        //
        tvStatus = findViewById(R.id.order_details_status);
        tvName = findViewById(R.id.order_details_name);
        tvPrice = findViewById(R.id.order_details_price);
//        tvPrice1 = findViewById(R.id.order_details_price1);
        tvPrice2 = findViewById(R.id.order_details_price2);
        editNum = findViewById(R.id.order_details_num);
        tvTimeCreate = findViewById(R.id.order_details_time_create);
        tvTimeFK = findViewById(R.id.order_details_time_fk);
        tvTimeFH = findViewById(R.id.order_details_time_fh);
        tvCount = findViewById(R.id.order_details_count);
        findViewById(R.id.order_details_im).setOnClickListener(this);
        findViewById(R.id.order_details_call).setOnClickListener(this);
        //
        recyclerView = findViewById(R.id.orderinfo_list);
        myOrderGoodsAdapter = new MyOrderGoodsAdapter(R.layout.item_myorder_goods,new ArrayList());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(myOrderGoodsAdapter);
        myOrderGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyApp.app.activityTool.toGoodsActivity(mContext,orderInfoBean.getShopId(),myOrderGoodsAdapter.getData().get(position).getCommodityId());
        });

        //(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）（8：全部订单）
        mapStr.put(0,"等待买家付款");
        mapStr.put(1,"买家已付款");
        mapStr.put(2,"卖家已发货");
        mapStr.put(3,"交易成功");
        mapStr.put(4,"退款中");
        mapStr.put(7,"退款成功");
        mapStr.put(5,"交易完成");
        mapStr.put(6,"订单已取消");

        promptDialog.setOkClickListener(new PromptDialog.OnOkClickListener() {
            @Override
            public void onClick() {
                AppUtils.callPhone(mContext,orderInfoBean.getContactsPhone());
            }

            @Override
            public void cancle() {

            }
        });

    }

    @Override
    public void initData() {
        AppNetModule.createrRetrofit()
                .orderInfo(MyApp.app.getAccount(), MyApp.app.getUserToken(),orderid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OrderInfoBean>() {
                    @Override
                    public void onSuccess(OrderInfoBean result) {
                        orderInfoBean = result.getReturnValue();

                        tvStatus.setText(mapStr.get(orderInfoBean.getStatus()));
                        tvName.setText(orderInfoBean.getShopName());
//                        tvPrice1.setText("￥" + orderInfoBean.getSumPackingCharges()+"元");
                        tvPrice2.setText("￥" + orderInfoBean.getTransportCharges()+"元");
                        tvPrice.setText("￥" + orderInfoBean.getFooting()+"元");
                        editNum.setText(orderInfoBean.getOrderFormNumber());
                        tvTimeCreate.setText(orderInfoBean.getEstablishTime());
                        tvTimeFK.setText(orderInfoBean.getPaymentTime());
                        tvTimeFH.setText(orderInfoBean.getShipmentsTime());

                        int count = 0;
                        if (orderInfoBean.getCommodityList() != null){
                            myOrderGoodsAdapter.addData(orderInfoBean.getCommodityList());
                            for (OrderCommodityListBean bean:orderInfoBean.getCommodityList()) {
                                count += bean.getCommodityNumber();
                            }
                        }
                        tvCount.setText(String.valueOf(count));
                        
                        //
//                        ModuleIm_Init.setUser(new UserIM(orderInfoBean.getShopAccount(),orderInfoBean.getShopName() , StringTools.getImageForCrmInt(orderInfoBean.getShopUserId())));
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (orderInfoBean == null)return;
        switch (v.getId()){
            case R.id.order_details_im://im

//                ModuleIm_Init.chat(mContext,orderInfoBean.getShopAccount(),orderInfoBean.getShopName());
                break;
            case R.id.order_details_call://电话
                if (orderInfoBean.getContactsPhone() != null && !orderInfoBean.getContactsPhone().isEmpty()){
                    promptDialog.setMessage("是否拨打\n"+orderInfoBean.getContactsPhone(),"取消","拨打");
                    promptDialog.show(getSupportFragmentManager(),"call");
                }
                break;
        }
    }
}

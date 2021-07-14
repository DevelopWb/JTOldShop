package com.juntai.shop.mall.ui.after_sales;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dxngxhl.imageselection.t2.Bean;
import com.dxngxhl.imageselection.t2.ImageSelectionView2;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.ProgressDialog;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.bean.ReturnReasonBean;
import com.juntai.shop.mall.ui.after_sales.adt.ReturnGoodsAdapter;
import com.juntai.shop.mall.utils.imageselect.ImageSelect;
import com.juntai.shop.mall.utils.imageselect.ImageSelectLoad;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 货物退换等--详情
 * Created by Ma
 * on 2019/12/20
 */
public class GoodsReturnActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ReturnGoodsAdapter goodsAdapter;
    //换货-1，退货-2，退款-3
    public static int type;
    TextView textView,tvStatus;
    //（1：未收到货）（2：已收到货）
    int status,orderId;
    double price;
    GoodsStatusDialog statusDialog = new GoodsStatusDialog();
    AfterSalesDialog afterSalesDialog = new AfterSalesDialog();
    ImageSelectionView2 imageSelectionView;
    //货物状态，退款原因
    LinearLayout layoutStatus,layoutReturn,layoutMoney;
    //原因，说明
    TextView tvReasontv,tvReason,tvDirections,tvMoney;
    EditText editText;
    ReturnReasonBean.ReturnValueBean valueBean;
    List<Integer> listIds = new ArrayList<>();
    @Override
    public int getLayoutView() {
        return R.layout.activity_goods_return;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type",-1);
        orderId = getIntent().getIntExtra("id",-1);
        if (type == -1 || orderId == -1)return;
        progressDialog = new ProgressDialog(mContext);
        textView = findViewById(R.id.goods_return_shopname);
        editText = findViewById(R.id.goods_return_edittext);
        layoutStatus = findViewById(R.id.goods_return_status_layout);
        layoutReturn = findViewById(R.id.goods_return_return_layout);
        layoutMoney = findViewById(R.id.goods_return_money_layout);
        tvReasontv = findViewById(R.id.goods_return_reason_tv);
        tvReason = findViewById(R.id.goods_return_reason);
        tvDirections = findViewById(R.id.goods_return_directions_tv);
        tvMoney = findViewById(R.id.goods_return_money);
        textView.setText(getIntent().getStringExtra("name"));
        tvReason.setOnClickListener(this);
        findViewById(R.id.goods_return_submit).setOnClickListener(this);
        switch (type){
            case 1://换货
                setTitleName("申请换货");
                tvReasontv.setText("换货原因");
                tvDirections.setText("换货说明");
                layoutMoney.setVisibility(View.GONE);
                break;
            case 2://退货
                setTitleName("申请退货");
                tvReasontv.setText("退货原因");
                tvDirections.setText("退货说明");
                break;
            case 3://退款
                setTitleName("申请退款");
                tvReasontv.setText("退款原因");
                tvDirections.setText("退款说明");
                layoutStatus.setVisibility(View.VISIBLE);
                break;
        }
        //
        tvStatus = findViewById(R.id.goods_return_status);
        imageSelectionView = findViewById(R.id.goods_return_imageselect);
        recyclerView = findViewById(R.id.goods_return_list);
        goodsAdapter = new ReturnGoodsAdapter(R.layout.item_return_goods, MyApp.app.goodsReturnBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(goodsAdapter);
        tvStatus.setOnClickListener(this);
        //
        imageSelectionView
                .setMaxCount(3)
                .setImageChoose(new ImageSelect(this))
                .setImageLoader(new ImageSelectLoad())
                .setNumColumn(3)
                .setImageAddResource(R.mipmap.ic_report_image)
                .setScaleType(ImageView.ScaleType.FIT_CENTER)
                .init();
        imageSelectionView.setBackgroundResource(R.color.content_layout);
        statusDialog.setOnSelectListener((arg,str) -> {
            status = arg;
            tvStatus.setText(str);
        });
        afterSalesDialog.setOnSelectorListener(selectBean -> {
            valueBean = selectBean;
            tvReason.setText(valueBean.getCauseName());
        });
        //

        listIds.clear();
        for (OrderCommodityListBean s: MyApp.app.goodsReturnBeans) {
            listIds.add(s.getId());
            price += s.getPrice() * s.getCommodityNumber();
        }
        tvMoney.setText(String.format("￥%s元",price));
    }

    RequestBody requestBody;
    public void setFormData() {
        requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", MyApp.app.getUserToken())
                .addFormDataPart("account", MyApp.app.getAccount())
                .addFormDataPart("orderId", String.valueOf(orderId))//订单id
                .addFormDataPart("causeId", String.valueOf(valueBean.getId()));//	原因id
        if (!editText.getText().toString().isEmpty()){
            builder.addFormDataPart("remark", editText.getText().toString());//	说明
        }
        //退款金额-状态都不传=换货
        //状态不传=退货
        //全传=退款
        if (type != 1){//不是换货
            builder.addFormDataPart("returnPrice",String.valueOf(price));
            if (type == 3){
                builder.addFormDataPart("cargoStatus",String.valueOf(status));
            }
        }
        for (int i = 0; i < imageSelectionView.getImagePaths().size(); i++) {
            if (imageSelectionView.getImagePaths().get(i).getImagePath() == null){
                if (i == 1){
                    builder.addFormDataPart("photoOne", "photoOne",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }else if (i == 2){
                    builder.addFormDataPart("photoTwo", "photoTwo",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }else if (i == 3){
                    builder.addFormDataPart("photoThree", "photoThree",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }
            }
        }

        requestBody = builder.build();
    }
    @Override
    public void initData() {

    }

    boolean isSubmit = false;
    private void submit(){
        isSubmit = true;
        AppNetModule.createrRetrofit()
                .returnGoods(AppHttpPath.RETURNSTATUS,listIds,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext,result.msg);
                        //售后详情
//                        startActivity(new Intent());
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                        isSubmit = false;
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_return_status://获取状态
                statusDialog.show(getSupportFragmentManager(),"statusDialog");
                break;
            case R.id.goods_return_reason://获取原因
                afterSalesDialog.show(getSupportFragmentManager(),"reasonDialog");
                break;
            case R.id.goods_return_submit://提交
                if (type == 3 && status == 0){//退款--货物状态
                    ToastUtils.toast(mContext,"请选择货物状态");
                }else if (valueBean == null){//
                    ToastUtils.toast(mContext,"请选择原因");
                }else {
                    setFormData();
                    submit();
                }
                break;
        }
    }
    private ProgressDialog progressDialog;
    /**
     * 图片压缩
     * @param paths
     */
    public void imageCompress(List<String> paths) {
        Luban.with(mContext)
                .load(paths)
                .ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath())
                .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        progressDialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        imageSelectionView.addImagePath(new Bean(file.getPath(),false));
                        LogUtil.e("push-图片压缩"+file.getPath());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过程出现问题时调用
                        LogUtil.e("push-图片压缩失败");
                        progressDialog.dismiss();
                    }
                }).launch();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelect.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            LogUtil.d("选择图片" +"  =  " + Matisse.obtainPathResult(data));
            imageCompress(Matisse.obtainPathResult(data));
        }
    }
}

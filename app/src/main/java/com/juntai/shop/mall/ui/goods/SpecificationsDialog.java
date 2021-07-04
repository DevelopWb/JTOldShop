package com.juntai.shop.mall.ui.goods;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.GoodsB;
import com.juntai.shop.mall.view.CountView;
import com.wuhenzhizao.sku.bean.Sku;
import com.wuhenzhizao.sku.bean.SkuAttribute;
import com.wuhenzhizao.sku.view.OnSkuListener;
import com.wuhenzhizao.sku.view.SkuSelectScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品规格选择
 */
public class SpecificationsDialog extends DialogFragment {
    CountView countview;
    TextView tvPrice,tvTitle,tvSpecfication,tvKc;
    View view;
    int goodsId;
    SkuSelectScrollView selectScrollView;
    GoodsB.ReturnValueBean goodsB;
    boolean isSelectAll = false;
    GoodsB.ReturnValueBean.SkuBean nowSku;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_specifications,container,false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
        //window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(com.juntai.mall.base.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = MyApp.W - MyApp.W / 20;
//        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        getDialog().setOnDismissListener(dialog -> {
            if (onXXListener != null){
                onXXListener.onDismiss();
            }
        });
        if (wlp.height > MyApp.H / 2){
            wlp.height = MyApp.H / 2;
        }
    }
    public void initView(){
        selectScrollView = view.findViewById(R.id.skuSelectScrollView);
        tvPrice = view.findViewById(R.id.specfication_price);
        tvKc = view.findViewById(R.id.specfication_kucun);
        tvTitle = view.findViewById(R.id.specfication_title);
        tvSpecfication = view.findViewById(R.id.specfication_text);
        countview = view.findViewById(R.id.specfication_countview);
        view.findViewById(R.id.specfication_close).setOnClickListener(v -> dismiss());
        countview.setNumber(0);
        countview.setNumEdit(false);
//        tvPrice.setText(String.valueOf(price));
        countview.setOnCountChangeListener((count, type) -> {
            if (isSelectAll){
                //全选规格
                CartItemLocB cartItemLocB = new CartItemLocB(
                        0,
                        goodsB.getId(),
                        goodsB.getCommodityName(),
                        goodsB.getCommodityPicture().size() == 0?"":goodsB.getCommodityPicture().get(0).getImgUrl(),
                        count,
                        nowSku.getInventoryNum(),
                        nowSku.getPackingCharges(),
                        nowSku.getPrice(),nowSku.getAttributeId(),stringBuffer.toString());
                EventBus.getDefault().post(cartItemLocB);
            }else {

            }
        });
        selectScrollView.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                isSelectAll = false;
                countview.setNumEdit(false);
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
            }

            @Override
            public void onSkuSelected(Sku sku) {
                setCountData(sku);
            }
        });
        //
        if (goodsB == null || goodsB.getId() != goodsId){
//            Log.e("fffffffff333",selectScrollView.getEndBeanAll().size()+"");
            getData();
        }else {
            dataSet();
        }

    }

    public void setCountData(Sku sku){
        isSelectAll = true;
        SS ss = (SS) sku;
        nowSku = (GoodsB.ReturnValueBean.SkuBean) ss.bean;
        tvKc.setText(String.format("(库存:%s)",sku.getStockQuantity()));
        tvPrice.setText(String.valueOf(nowSku.getPrice()));
        countview.setNumber(0);
        countview.setNumEdit(true);
        countview.setMaxNumber(nowSku.getInventoryNum());
        for (CartItemLocB b: MyApp.app.getCartBeansForShop(ShopActivity.shopId)) {
            if (Integer.parseInt(sku.getId()) == b.getSpcId()){
                //购物车有
                countview.setNumber(b.getNum());
            }
        }
        stringBuffer = new StringBuffer();
        for (SkuAttribute bean:sku.getAttributes()) {
            stringBuffer.append(bean.getValue()+" ");
        }
        tvSpecfication.setText(stringBuffer.toString());
        //设置该规格最大库存
        if (countview != null){
            countview.setMaxNumber(nowSku.getInventoryNum());
        }
        if (onXXListener != null){
            onXXListener.selectedComplete(nowSku.getAttributeId());
        }
    }

    //已选规格名称
    StringBuffer stringBuffer;

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 规格选择
     */
    OnXXListener onXXListener;
    public void setOnXXListener(OnXXListener listener) {
        this.onXXListener = listener;
    }

    /**
     * 规格选择回调接口
     */
    public interface OnXXListener{
        /**
         *
         * @param spcid
         */
        void selectedComplete(int spcid);
        void onDismiss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.remove(this).commit();
        return super.show(transaction, tag);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        manager.beginTransaction().remove(this).commit();
        super.show(manager, tag);
    }

    public void getData(){
        AppNetModule.createrRetrofit()
                .GoodsDetalis(goodsId, MyApp.app.getUid())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GoodsB>() {
                    @Override
                    public void onSuccess(GoodsB result) {
                        goodsB = result.getReturnValue();
                        dataSet();
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(getActivity(),msg);
                    }
                });
    }

    public void setGoodsB(GoodsB.ReturnValueBean goodsB) {
        this.goodsB = goodsB;
    }

    int size1 = 0;
    /**
     * 初始化数据
     */
    public void dataSet(){
        tvTitle.setText(goodsB.getCommodityName());
        //price = result.g
        // etReturnValue().getc();
        if (goodsB.getCommodityParameterType() != null)
            size1 = goodsB.getCommodityParameterType().size();
        HashMap<String,String> paramere = new HashMap<>();
        for (int i = 0; i < size1; i++) {
            for (GoodsB.ReturnValueBean.CommodityParameterTypeBean.ParameterListBean bean
                    :goodsB.getCommodityParameterType().get(i).getParameterList()) {
                paramere.put(bean.getParameteName(),goodsB.getCommodityParameterType().get(i).getParameterTypeName());
            }
        }
        //
        if (goodsB.getSku() != null){
            List<Sku> skus = new ArrayList<>();
            for (GoodsB.ReturnValueBean.SkuBean bean:goodsB.getSku()) {
                List<SkuAttribute> list = new ArrayList<>();
                for (GoodsB.ReturnValueBean.SkuBean.ParameterBean b:bean.getParameter()) {
                    list.add(new SkuAttribute(paramere.get(b.getParameterName()),b.getParameterName()));
                }
                if (bean.getParameter().size() != 0 && bean.getInventoryNum() > 0){
//                    specificationsView.addEndBean(new SpEndB(list,bean));
//                    Sku sku = new Sku();
                    SS sku = new SS<GoodsB.ReturnValueBean.SkuBean>();
                    sku.setId(String.valueOf(bean.getAttributeId()));
//                    sku.setInStock(Long.parseLong(String.valueOf(bean.getPrice())));//售价
                    sku.setSellingPrice((long) (bean.getPrice() * 100));//售价
                    sku.setStockQuantity(bean.getInventoryNum());//库存
                    sku.setAttributes(list);
                    sku.bean = bean;
                    skus.add(sku);
                }
            }
            selectScrollView.setSkuList(skus);
            if (skus.size() == 1 && skus.get(0).getAttributes().size() == 1){
                setCountData(skus.get(0));
            }
        }

        //getActivity().runOnUiThread(() -> specificationsView.adapterNotifyDataSetChanged());
    }
    public void clear(){
        goodsB = null;
//        if (specificationsView != null){
//            specificationsView.clearAll();
//        }
    }
    public class SS<T> extends Sku{
        public T bean;
    }
}

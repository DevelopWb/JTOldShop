package com.juntai.shop.mall.ui.goods.adt;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.utils.listener.OnUpdateListener;
import com.juntai.shop.mall.view.CountView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 店铺购物车
 * @aouther Ma
 * @date 2019/3/5
 */
public class ShopCartItemAdapter extends BaseQuickAdapter<CartItemLocB, BaseViewHolder> {
    OnUpdateListener onUpdateListener;
    public ShopCartItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartItemLocB item) {
        if (item.getNum() <= 0)return;
        ImageLoadUtil.loadImage(mContext, AppHttpPath.IMAGE + item.getGoodImage(),R.mipmap.ic_launcher,helper.getView(R.id.item_shopcart_goodsimage));
        helper.setText(R.id.item_shopcart_goodsname,item.getGoodsName());
        helper.setText(R.id.item_shopcart_specification,item.getSpcName());
        helper.setText(R.id.item_shopcart_goods_price,"￥ " + item.getPrice());
        //item_shopcart_goodsimage
        //数量
        CountView countView = helper.getView(R.id.cart_countview);
        countView.setNumber(item.getNum());
        countView.setMaxNumber(item.getStock());
        countView.setOnCountChangeListener(new CountView.OnCountChangeListener() {
            @Override
            public void countChange(int count, int type) {
                item.setNum(count);
                EventBus.getDefault().post(item);
                if (count == 0){
                    if (onUpdateListener != null){
                        onUpdateListener.update();
                    }
                }
            }
        });
        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = helper.getAdapterPosition();
                return true;
            }
        });
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }
}
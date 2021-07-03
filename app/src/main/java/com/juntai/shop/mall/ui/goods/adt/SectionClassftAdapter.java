package com.juntai.shop.mall.ui.goods.adt;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.MySection;
import com.juntai.shop.mall.ui.goods.ShopActivity;
import com.juntai.shop.mall.view.CountView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 分类右侧列表
 */
public class SectionClassftAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    HashMap<Integer,Integer> dataMap = new HashMap<>();
    HashMap<Integer,Integer> numMap = new HashMap<>();
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionClassftAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        //helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        dataMap.put(item.t.getCommodityId(),helper.getAdapterPosition());
        helper.addOnClickListener(R.id.item_shop_goodsimage);
        helper.setText(R.id.item_shop_goodsname,item.t.getCommodityName());
        helper.setText(R.id.item_shop_specification,item.t.getCommoditySynopsis());
        helper.setText(R.id.item_shop_mon,"月销 " + item.t.getMonthlySales());
        helper.setText(R.id.item_shop_price,String.valueOf(item.t.getPrice()));
        CountView view = helper.getView(R.id.cart_countview);
        view.setNumber(item.t.getCommodityNum());
        view.setMaxNumber(item.t.getInventoryNum());
        view.setOnCountChangeListener((count, type) -> {
            CartItemLocB cartItemLocB = new CartItemLocB(
                    0,
                    item.t.getCommodityId(),
                    item.t.getCommodityName(),
                    item.t.getCommodityImg(),
                    count,
                    item.t.getInventoryNum(),
                    item.t.getPackingCharges(),
                    item.t.getPrice(),item.t.getAttrId(),"");
            EventBus.getDefault().post(cartItemLocB);
        });
        ImageLoadUtil.loadImage(mContext, AppHttpPath.IMAGE + item.t.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_shop_goodsimage));
        //没有多规格，显示添加购物车。多规格显示选规格
        if (item.t.getIsType() == 0){
            if (item.t.getInventoryNum() < item.t.getCommodityNum()){
                CartItemLocB cartItemLocB = new CartItemLocB(
                        0,
                        item.t.getCommodityId(),
                        item.t.getCommodityName(),
                        item.t.getCommodityImg(),
                        item.t.getInventoryNum(),
                        item.t.getInventoryNum(),
                        item.t.getPackingCharges(),
                        item.t.getPrice(),item.t.getAttrId(),"");
                EventBus.getDefault().post(cartItemLocB);
                item.t.setCommodityNum(item.t.getInventoryNum());
            }
            if (numMap.get(item.t.getCommodityId()) != null){
                item.t.setCommodityNum(numMap.get(item.t.getCommodityId()));
                numMap.remove(item.t.getCommodityId());
            }
            view.setNumber(item.t.getCommodityNum());
            //
            view.setVisibility(View.VISIBLE);
            helper.getView(R.id.cart_specification).setVisibility(View.GONE);
        }else {
            view.setVisibility(View.GONE);
            helper.getView(R.id.cart_specification).setVisibility(View.VISIBLE);
            helper.addOnClickListener(R.id.cart_specification);
        }
    }


    /**
     * 刷新
     * @param id
     */
    public void refresh(int id,int num){
        numMap.put(id,num);
        if (dataMap.get(id) != null){
            getData().get(dataMap.get(id)).t.setCommodityNum(num);
            notifyItemChanged(dataMap.get(id));
        }
    }
}

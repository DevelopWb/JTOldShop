package com.juntai.shop.mall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.juntai.shop.mall.mine.set.ModifyPwd;
import com.juntai.shop.mall.mine.set.address.EditAddressActivity;
import com.juntai.shop.mall.ui.after_sales.AfterSalesActivity;
import com.juntai.shop.mall.ui.after_sales.GoodsReturnActivity;
import com.juntai.shop.mall.ui.after_sales.GoodsSelectActivity;
import com.juntai.shop.mall.ui.after_sales.ReturnDetailsActivity;
import com.juntai.shop.mall.ui.after_sales.ReturnSelectActivity;
import com.juntai.shop.mall.ui.goods.shop.ShopActivity;
import com.juntai.shop.mall.ui.order.OrderConfirmActivity;
import com.juntai.shop.mall.ui.order.OrderDeatilsActivity;
import com.juntai.shop.mall.ui.order.OrderPayActivity;

/**
 * Created by Ma
 * on 2019/12/13
 */
public class ActivityTool {

    /**
     * 重置，修改密码
     * @param context
     * @param type:1`-忘记密码，2修改密码
     */
    public void toPassChange(Context context,String phone,int type){
        context.startActivity(new Intent(context, ModifyPwd.class)
                .putExtra("type",type)
                .putExtra("phone",phone));
    }

    /**
     * 商铺页
     * @param context
     * @param shopid
     */
    public void toShopActivity(Context context,int shopid){
        context.startActivity(new Intent(context, ShopActivity.class)
                .putExtra("shopId",shopid));
    }

    /**
     * 查看商品详情
     * @param context
     * @param shopid
     * @param goodsid
     */
    public void toGoodsActivity(Context context,int shopid,int goodsid){
        context.startActivity(new Intent(context, ShopActivity.class)
                .putExtra("shopId",shopid)
                .putExtra("goodsId",goodsid));
    }

    /**
     * 订单确认页
     * @param context
     * @param shopname
     */
    public void toOrderConfirmActivity(Context context,int shopid,int logoid,String shopname){
        context.startActivity(new Intent(context, OrderConfirmActivity.class)
                .putExtra("id",shopid)
                .putExtra("logoId",logoid)
                .putExtra("shopname",shopname));
    }

    /**
     * 订单详情页
     * @param context
     * @param id
     */
    public void toOrderDtailsActivity(Context context,int id){
        context.startActivity(new Intent(context, OrderDeatilsActivity.class)
                .putExtra("orderId",id));
    }
    public Intent getOrderDtailsIntent(Context context,int id){
        return new Intent(context, OrderDeatilsActivity.class)
                .putExtra("orderId",id);
    }

    /**
     * 添加(编辑地址)
     * @param context
     * @param addressId:-1 = 新增
     */
    public void toAddAddress(Activity context, int addressId){
        context.startActivityForResult(new Intent(context, EditAddressActivity.class).putExtra("id",addressId),AppCode.ADDRESS);
    }

    /**
     * 跳转支付
     * @param context
     * @param orderId
     * @param a
     * @param name
     * @param time
     */
    public void toOrderPayActivity(Activity context, int orderId, double a, String name, long time){
        context.startActivityForResult(new Intent(context, OrderPayActivity.class)
                .putExtra("id",orderId)
                .putExtra("a",a)
                .putExtra("name",name)
                .putExtra("time",time),0);
    }

    public void toOrderPayActivity(Fragment context, int orderId, double a, String name, long time){
        context.startActivityForResult(new Intent(context.getActivity(), OrderPayActivity.class)
                .putExtra("id",orderId)
                .putExtra("a",a)
                .putExtra("name",name)
                .putExtra("time",time),0);
    }

    /**
     * 申请售后选择商品
     * @param context
     * @param orderId
     * @param name
     * @param shopAccount
     */
    public void toGoodsSelectActivity(Context context,int orderId,String name,String shopAccount){
        context.startActivity(new Intent(context, GoodsSelectActivity.class)
                .putExtra("id",orderId)
                .putExtra("name",name)
                .putExtra("ryid",shopAccount));
    }

    /**
     * 申请售后
     */
    public void toAfterSalesActivity(Context context,Intent intent){
        intent.setClass(context,AfterSalesActivity.class);
        context.startActivity(intent);
    }

    /**
     * 选择退款、退货
     * @param context
     * @param intent
     */
    public void toReturnSelectActivity(Context context,Intent intent){
        intent.setClass(context, ReturnSelectActivity.class);
        context.startActivity(intent);
    }
    /**
     * 货物退换等--详情
     * @param context
     * @param type:换货-1，退货-2，退款-3
     */
    public void toReturnGoodsActivity(Context context,int type,Intent intent){
        intent.setClass(context,GoodsReturnActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    public Intent getReturnDetailsIntent(Context context,int id){
        return new Intent(context, ReturnDetailsActivity.class)
                .putExtra("returnId",id);
    }
}

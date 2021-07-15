package com.juntai.shop.mall.mine.adt;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderListBean;
import com.juntai.shop.mall.utils.StringTools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 我的订单
 *
 */
public class MyOrderAdapter extends BaseQuickAdapter<OrderListBean.ReturnValueBean.DatasBean, BaseViewHolder> {


    private OnJumpToOrderDetailCallBack jumpToOrderDetailCallBack;
    int[] idsAll = new int[]{R.id.item_myorder_maijia,
                             R.id.item_myorder_cancle,
                             R.id.item_myorder_fukuan,
                             R.id.item_myorder_wuliu,
                             R.id.item_myorder_shouhuo,
                             R.id.item_myorder_deails,
                             R.id.item_myorder_after_sales,
                             R.id.item_myorder_comment,
                             R.id.item_myorder_more};
    //待付款
    Integer[] ids0 = new Integer[]{R.id.item_myorder_maijia,
                           R.id.item_myorder_cancle,
                           R.id.item_myorder_fukuan,
                           R.id.item_myorder_more};
    //待发货
    Integer[] ids1 = new Integer[]{R.id.item_myorder_wuliu};
    //待收货
    Integer[] ids2 = new Integer[]{R.id.item_myorder_wuliu, R.id.item_myorder_shouhuo};
    //待评价
    Integer[] ids3 = new Integer[]{R.id.item_myorder_after_sales, R.id.item_myorder_comment};
    //
    Integer[] ids4_7 = new Integer[]{R.id.item_myorder_deails};
    //完成
    Integer[] ids5 = new Integer[]{R.id.item_myorder_after_sales, R.id.item_myorder_more};
    //已取消
    Integer[] ids6 = new Integer[]{R.id.item_myorder_more};

    HashMap<Integer, List<Integer>> map = new HashMap<>();
    HashMap<Integer, String> mapStr = new HashMap<>();
    public MyOrderAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        map.put(0, Arrays.asList(ids0));
        map.put(1, Arrays.asList(ids1));
        map.put(2, Arrays.asList(ids2));
        map.put(3, Arrays.asList(ids3));
        map.put(4, Arrays.asList(ids4_7));
        map.put(7, Arrays.asList(ids4_7));
        map.put(5, Arrays.asList(ids5));
        map.put(6, Arrays.asList(ids6));
        //
        mapStr.put(0,"等待买家付款");
        mapStr.put(1,"买家已付款");
        mapStr.put(2,"卖家已发货");
        mapStr.put(3,"交易成功");
        mapStr.put(4,"退款中");
        mapStr.put(7,"退款完成");
        mapStr.put(5,"交易完成");
        mapStr.put(6,"订单已取消");
    }



    public void setJumpToOrderDetailCallBack(OnJumpToOrderDetailCallBack jumpToOrderDetailCallBack) {
        this.jumpToOrderDetailCallBack = jumpToOrderDetailCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ReturnValueBean.DatasBean item) {
        int orderId = item.getOrderId();
        helper.addOnClickListener(R.id.item_myorder_shoplayout);
        //(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）（8：全部订单）
        for (int i = 0; i < idsAll.length; i++) {
            helper.getView(idsAll[i]).setVisibility(View.GONE);
        }
        if (map.get(item.getStatus()) != null){
            for (int i = 0; i < map.get(item.getStatus()).size(); i++) {
                helper.getView(map.get(item.getStatus()).get(i)).setVisibility(View.VISIBLE);
                helper.addOnClickListener(map.get(item.getStatus()).get(i));
            }
        }
        if ("3,4,5,7".indexOf(String.valueOf(item.getStatus())) != -1){
            helper.setTextColor(R.id.item_myorder_right,mContext.getResources().getColor(R.color.colorTheme));
        }else {
            helper.setTextColor(R.id.item_myorder_right,mContext.getResources().getColor(R.color.grey));
        }
        helper.setText(R.id.item_myorder_shopname,item.getShopName());
        helper.setText(R.id.item_myorder_right,mapStr.get(item.getStatus()));
        ImageLoadUtil.loadCircularImage(mContext, StringTools.getImageForCrmInt(item.getLogoId() ),R.mipmap.ic_launcher,R.mipmap.ic_launcher,helper.getView(R.id.item_myorder_logo));
        helper.setText(R.id.item_myorder_price_all,"共"+ item.getCommodityList().size() +"件商品   合计:");
       helper.setText(R.id.item_myorder_price_tv,"￥"+item.getFooting());
        //
        RecyclerView recyclerView = helper.getView(R.id.item_myorder_list);
        MyOrderGoodsAdapter myOrderGoodsAdapter = new MyOrderGoodsAdapter(R.layout.item_myorder_goods,item.getCommodityList());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(myOrderGoodsAdapter);
        helper.addOnClickListener(R.id.item_myorder_list);
        myOrderGoodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转到订单详情
                if (jumpToOrderDetailCallBack != null) {
                    jumpToOrderDetailCallBack.jumpToOrderDetail(orderId);
                }
            }
        });
    }


    public interface OnJumpToOrderDetailCallBack {
        void  jumpToOrderDetail(int orderId);
    }
}
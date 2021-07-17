package com.juntai.shop.mall.mine.adt;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MyCollcetB;
import com.juntai.shop.mall.utils.StringTools;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 我的收藏
 */
public class MyCollectAdapter extends BaseQuickAdapter<MyCollcetB.ReturnValueBean.DatasBean, BaseViewHolder> {
    boolean isEdit = false,isAll = false;
    //第一种方式：把check标记放在实体类里，最后的时候遍历循环
    //第二种方式：创建一个列表集合，每次操作
    int type;
    public MyCollectAdapter(int layoutResId, List data,int t) {
        super(layoutResId, data);
        type = t;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollcetB.ReturnValueBean.DatasBean item) {
        if (isEdit){
            //编辑状态
            if (isAll){
                item.setChecked(true);
            } else{
                item.setChecked(false);
            }
            helper.setChecked(R.id.item_collect_check,item.isChecked());
            helper.getView(R.id.item_collect_check).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_collect_check).setOnClickListener(v -> {
                item.setChecked(((CheckBox)v).isChecked());
            });
        }else {
            helper.getView(R.id.item_collect_check).setVisibility(View.GONE);
        }

        RatingBar ratingBar = helper.getView(R.id.item_collect_ratingBar);
        TextView tvName = helper.getView(R.id.item_collect_name);
        ratingBar.setRating(item.getDegreeOfSatisfaction());
        if (type == 0){
            //商铺
            tvName.setText(item.getShopName());
            tvName.setTextSize(18);
            tvName.setLines(1);
            helper.setTextColor(R.id.item_collect_address, ContextCompat.getColor(mContext,R.color.gray));
            helper.setText(R.id.item_collect_address,item.getAddress());
            helper.setGone(R.id.item_collect_score,true);
            helper.setGone(R.id.item_collect_ratingBar,true);
            DecimalFormat format = new DecimalFormat("##0.0");
            helper.setText(R.id.item_collect_score,"(" + format.format(item.getDegreeOfSatisfaction()) + ")");
            helper.setText(R.id.item_collect_distance,format.format(item.getDistance()) + "km");
            ImageLoadUtil.loadImageNoCrash(mContext, StringTools.getImageForCrmInt(item.getLogoId()),R.mipmap.ic_launcher,helper.getView(R.id.item_collect_image));
        }else{
            //商品
            helper.setGone(R.id.item_collect_ratingBar,false);
            helper.setGone(R.id.item_collect_score,false);
            tvName.setText(item.getCommodityName());
            tvName.setTextSize(16);
            tvName.setLines(2);
            helper.setText(R.id.item_collect_distance,"月销" + item.getMonthlySales());
            helper.setTextColor(R.id.item_collect_address, ContextCompat.getColor(mContext,R.color.red));
            helper.setText(R.id.item_collect_address,"￥"+String.valueOf(item.getPrice()));
            ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_collect_image));
        }
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public void setAll() {
        isAll = !isAll;
        notifyDataSetChanged();
    }
}
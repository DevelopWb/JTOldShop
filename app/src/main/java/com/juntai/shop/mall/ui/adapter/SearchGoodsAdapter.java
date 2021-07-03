package com.juntai.shop.mall.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.SearchGoodsBean;
import com.juntai.shop.mall.utils.StringTools;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 */
public class SearchGoodsAdapter extends BaseQuickAdapter<SearchGoodsBean.ReturnValueBean.DatasBean, BaseViewHolder> {
    public SearchGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchGoodsBean.ReturnValueBean.DatasBean item) {
        helper.getView(R.id.item_collect_check).setVisibility(View.GONE);
        helper.getView(R.id.item_collect_ratingBar).setVisibility(View.GONE);
        TextView tvName = helper.getView(R.id.item_collect_name);
        helper.getView(R.id.item_collect_price_l).setVisibility(View.VISIBLE);
        tvName.setText(item.getCommodityName());
        tvName.setTextSize(16);
        tvName.setMaxLines(2);
        helper.setText(R.id.item_collect_yuexiao,"月销" + item.getMonthlySales());
        helper.setText(R.id.item_collect_price,String.valueOf(item.getPrice()));
        ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_collect_image));
    }
}
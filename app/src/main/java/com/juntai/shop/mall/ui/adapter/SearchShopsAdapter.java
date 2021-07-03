package com.juntai.shop.mall.ui.adapter;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.SearchGoodsBean;
import com.juntai.shop.mall.bean.SearchShopBean;
import com.juntai.shop.mall.utils.StringTools;

import java.text.DecimalFormat;
import java.util.List;

/**
 *
 */
public class SearchShopsAdapter extends BaseQuickAdapter<SearchShopBean.ReturnValueBean.DatasBean, BaseViewHolder> {
    public SearchShopsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchShopBean.ReturnValueBean.DatasBean item) {

        helper.getView(R.id.item_collect_check).setVisibility(View.GONE);
        RatingBar ratingBar = helper.getView(R.id.item_collect_ratingBar);
        TextView tvName = helper.getView(R.id.item_collect_name);
        ratingBar.setRating(item.getDegreeOfSatisfaction());

        //商铺
        helper.getView(R.id.item_collect_price_l).setVisibility(View.GONE);
        tvName.setText(item.getShopName());
        tvName.setTextSize(18);
        tvName.setMaxLines(1);
        helper.setText(R.id.item_collect_address,item.getAddress());
        ratingBar.setVisibility(View.VISIBLE);
        DecimalFormat format = new DecimalFormat("##0.0");
        helper.setText(R.id.item_collect_score,"(" + format.format(item.getDegreeOfSatisfaction()) + ")");
        helper.setText(R.id.item_collect_distance,format.format(item.getDistance()) + "km");
        ImageLoadUtil.loadImageNoCrash(mContext, StringTools.getImageForCrmInt(item.getLogoId()),R.mipmap.ic_launcher,helper.getView(R.id.item_collect_image));
    }
}
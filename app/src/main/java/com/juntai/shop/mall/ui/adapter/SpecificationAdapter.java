package com.juntai.shop.mall.ui.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.GoodsB;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 规格适配器
 */
public class SpecificationAdapter extends BaseQuickAdapter<GoodsB.ReturnValueBean.CommodityParameterTypeBean, BaseViewHolder> {
    List<TagFlowLayout> layoutList = new ArrayList<>();

    List<Integer> selected = new ArrayList<>();
    boolean isComplete = true;
    OnTagClickListener onTagClickListener;

    //临时集合--缓存当前存在的规格集合
    List<String> cachKeys = new ArrayList<>();
    public SpecificationAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, GoodsB.ReturnValueBean.CommodityParameterTypeBean item) {
        helper.setText(R.id.item_specfication_title, item.getParameterTypeName());
        //标签
        TagFlowLayout flowLayout = helper.getView(R.id.item_flowlayout);
        flowLayout.setTag(item.getParameterTypeName());

        flowLayout.setAdapter(new TagAdapter<GoodsB.ReturnValueBean.CommodityParameterTypeBean.ParameterListBean>(item.getParameterList()) {
            @Override
            public View getView(FlowLayout parent, int position, GoodsB.ReturnValueBean.CommodityParameterTypeBean.ParameterListBean s) {
                TextView tv = new TextView(mContext);
                tv.setBackgroundResource(R.drawable.bg_tag);
                tv.setTextColor(Color.BLACK);
                tv.setPadding(20,10,20,10);
                tv.setText(String.valueOf(s.getParameterId()));
                for (int i = 0; i < cachKeys.size(); i++) {
                    if (cachKeys.get(i).indexOf(String.valueOf(s.getParameterId())) == -1){
                        tv.setEnabled(false);
                        tv.setBackgroundResource(R.color.grey);
                    }
                }
//                tv.setText(s.getParameteName());
                return tv;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (onTagClickListener != null){
                    onTagClickListener.tagClick(position,item.getId(),position);
                }

                return false;
            }
        });
        layoutList.add(flowLayout);
    }
    /**
     * 是否所有规格选择完成
     * @return
     */
//    public boolean isSelectedComplete(){
//        selected.clear();
//        specifications.clear();
//        isComplete = true;
//        for (TagFlowLayout fl:layoutList) {
//            if (fl.getSelectedList().size() > 0){
//                fl.getSelectedList().get;
//                selected.add(ss[0]);
//            }else {
//                selected.add(null);
//                isComplete = false;
//                lack.append(fl.getTag() + " ");
//            }
//        }
//        return isComplete;
//    }


    public void setCachKeys(List<String> cachKeys) {
        this.cachKeys = cachKeys;
        notifyDataSetChanged();
    }

    /**
     * 清理
     */
    public void clearAll(){
        for (TagFlowLayout fl:layoutList) {
            fl.removeAllViews();
            fl.removeAllViewsInLayout();
        }
        layoutList.clear();
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener{
        /**
         *
         * @param tagParentId:规格组id
         * @param position：规格所在组
         * @param tagId：组内规格id
         */
        void tagClick(int position,int tagParentId,int tagId);
    }
}
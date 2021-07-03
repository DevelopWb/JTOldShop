package com.juntai.shop.mall.ui.after_sales.adt;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;
import com.juntai.shop.mall.bean.ReturnReasonBean;

import java.util.List;

/**
 * 售后原因选择
 */
public class AfterSalesAdapter extends BaseQuickAdapter<ReturnReasonBean.ReturnValueBean, BaseViewHolder> {
    int selector = 0;
    public AfterSalesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnReasonBean.ReturnValueBean item) {
        helper.setText(R.id.item_dialog_after_sales_text,item.getCauseName());
        if (selector == helper.getAdapterPosition()){
            helper.setChecked(R.id.item_dialog_after_sales_radio,true);
        }else {
            helper.setChecked(R.id.item_dialog_after_sales_radio,false);
        }
        helper.getView(R.id.item_dialog_after_sales_radio).setOnClickListener(v -> {
            selector = helper.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    /**
     *
     * @param position
     */
    public void selectorPosition(int position){
        selector = position;
        notifyDataSetChanged();
    }

    public int getSelector() {
        return selector;
    }
}
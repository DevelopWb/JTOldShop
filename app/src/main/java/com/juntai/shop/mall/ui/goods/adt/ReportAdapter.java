package com.juntai.shop.mall.ui.goods.adt;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ReportTypeBesan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ma
 * on 2019/9/12
 */
public class ReportAdapter extends BaseQuickAdapter<ReportTypeBesan.ReturnValueBean,BaseViewHolder> {
    ArrayList<TextView> textViewList = new ArrayList<>();
    public ReportAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,ReportTypeBesan.ReturnValueBean item) {
        if (helper.getAdapterPosition() == 0){
            textViewList.clear();
        }
        final TextView textView =  helper.getView(R.id.item_report_text);
        textView.setText(item.getName());
        textViewList.add(textView);
    }

    public ArrayList<TextView> getTextViewList() {
        return textViewList;
    }
}

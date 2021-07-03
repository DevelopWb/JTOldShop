package com.juntai.shop.mall.ui.after_sales;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;
import com.juntai.shop.mall.bean.ReturnReasonBean;
import com.juntai.shop.mall.ui.address.AddressSelectAdapter;
import com.juntai.shop.mall.ui.after_sales.adt.AfterSalesAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 售后原因选择
 * Created by Ma
 * on 2019/9/7
 */
public class AfterSalesDialog extends BottomDialogFragment {
    RecyclerView recyclerView;
    AfterSalesAdapter listAdapter;
    TextView textView;
    int select = 0;
    List<ReturnReasonBean.ReturnValueBean> beanList = new ArrayList<>();
    @Override
    public int setView() {
        return R.layout.dialog_after_sales;
    }

    @Override
    public void initView(View view) {

        recyclerView = view.findViewById(R.id.dialog_ater_sales_list);
        textView = view.findViewById(R.id.dialog_ater_sales_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new AfterSalesAdapter(R.layout.item_after_sales, beanList);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener((adapter1, view1, position) -> {
            listAdapter.selectorPosition(position);
        });
        listAdapter.selectorPosition(select);
        view.findViewById(R.id.dialog_ater_sales_close).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.dialog_ater_sales_complete).setOnClickListener(v -> {
            if (onSelectorListener != null){
                select = listAdapter.getSelector();
                onSelectorListener.onSelector(beanList.get(select));
                dismiss();
            }
        });
        if (beanList.size() == 0){
            getData();
        }
        //
    }
    public int dialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    public void getData(){
        AppNetModule.createrRetrofit()
                .returnCause(GoodsReturnActivity.type == 1?1:2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ReturnReasonBean>(null) {
                    @Override
                    public void onSuccess(ReturnReasonBean result) {
                        beanList.clear();
                        beanList.addAll(result.getReturnValue());
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(getActivity(),msg);
                    }
                });
    }
    /**
     * 事件
     */
    OnSelectorListener onSelectorListener;
    public void setOnSelectorListener(OnSelectorListener onSelectorListener) {
        this.onSelectorListener = onSelectorListener;
    }
    public interface OnSelectorListener{
        /**
         *
         * @param selectBean
         */
        void onSelector(ReturnReasonBean.ReturnValueBean selectBean);
    }
}

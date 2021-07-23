package com.juntai.shop.mall.mine.set.address;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择---订单确定页
 * Created by Ma
 * on 2019/9/7
 */

// TODO: 2021/7/23 订单地址选择  界面待优化
public class AddressSelectDialog extends BottomDialogFragment {
    RecyclerView recyclerView;
    AddressSelectAdapter listAdapter;
    int select = 0;
    List<AddressListBean.ReturnValueBean> beanList = new ArrayList<>();
    @Override
    public int setView() {
        return R.layout.dialog_address_select;
    }

    @Override
    public void initView(View view) {

        recyclerView = view.findViewById(R.id.address_selectlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new AddressSelectAdapter(R.layout.item_address_select, beanList);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener((adapter1, view1, position) -> {
            listAdapter.selectorPosition(position);
        });
        listAdapter.selectorPosition(select);
        view.findViewById(R.id.dialog_address_add).setOnClickListener(v -> MyApp.app.activityTool.toAddAddress(getActivity(),-1));
        view.findViewById(R.id.dialog_address_close).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.dialog_address_complete).setOnClickListener(v -> {
            if (onSelectorListener != null){
                select = listAdapter.getSelector();
                onSelectorListener.onSelector(beanList.get(select));
                dismiss();
            }
        });
        //
    }
    @Override
    public int dialogHeight() {
        return MyApp.H / 2;
    }
    public void updateList(List<AddressListBean.ReturnValueBean> list){
        beanList.clear();
        beanList.addAll(list);
        if (listAdapter != null){
            listAdapter.notifyDataSetChanged();
        }
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
        void onSelector(AddressListBean.ReturnValueBean selectBean);
    }
}

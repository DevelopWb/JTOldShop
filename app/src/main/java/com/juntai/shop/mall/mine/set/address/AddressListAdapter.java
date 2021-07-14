package com.juntai.shop.mall.mine.set.address;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;

import java.util.List;

/**
 * 地址列表
 */
public class AddressListAdapter extends BaseQuickAdapter<AddressListBean.ReturnValueBean, BaseViewHolder> {
    public AddressListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressListBean.ReturnValueBean item) {
        helper.addOnClickListener(R.id.item_address_edit);
        helper.setText(R.id.item_address_name,item.getName());
        helper.setText(R.id.item_address_one,item.getName().substring(0,1));
        helper.setText(R.id.item_address_phone,item.getPhone());
        helper.setText(R.id.item_address_adress,
                item.getProvinceName() + " "
                + item.getCityName() + " "
                + item.getAreaName() + " "
                + item.getStreetName() + " "
                + item.getDetailedAddress());
    }
}
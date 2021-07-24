package com.juntai.shop.mall.mine.set.address;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;

import java.util.List;

/**
 * 地址列表---确认订单选择地址
 */
public class AddressSelectAdapter extends BaseQuickAdapter<AddressListBean.ReturnValueBean, BaseViewHolder> {
    int selector = 0;
    public AddressSelectAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressListBean.ReturnValueBean item) {
        if (0== helper.getAdapterPosition()) {
            helper.setGone(R.id.default_addr_iv,true);
        }else {
            helper.setGone(R.id.default_addr_iv,false);
        }
        helper.setText(R.id.item_address_sel_name,item.getName());
        helper.setText(R.id.item_address_sel_one,item.getName().substring(0,1));
        helper.setText(R.id.item_address_sel_phone,item.getPhone());
        helper.setText(R.id.item_address_sel_adress,
                item.getProvinceName() + " "
                        + item.getCityName() + " "
                        + item.getAreaName() + " "
                        + item.getStreetName() + " "
                        + item.getDetailedAddress());
        if (selector == helper.getAdapterPosition()){
            helper.setChecked(R.id.item_address_sel_rad,true);
        }else {
            helper.setChecked(R.id.item_address_sel_rad,false);
        }
        helper.getView(R.id.item_address_sel_rad).setOnClickListener(v -> {
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
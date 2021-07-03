package com.juntai.shop.mall.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySection extends SectionEntity<ShopBean.ReturnValueBean.ShopClassifyBean.CommodityBean> {
    private boolean isMore;
    private String strHead;
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
        strHead = header;
    }

    public MySection(String head, ShopBean.ReturnValueBean.ShopClassifyBean.CommodityBean t) {
        super(t);
        strHead = head;
    }

    public String getStrHead() {
        return strHead;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}

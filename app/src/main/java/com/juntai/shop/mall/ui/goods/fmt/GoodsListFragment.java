package com.juntai.shop.mall.ui.goods.fmt;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.mall.base.base.BaseMvpFragment;
import com.juntai.mall.base.mvp.IPresenter;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseRecyclerviewFragment;
import com.juntai.shop.mall.ui.goods.adt.SectionClassftAdapter;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2021/7/18 16:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/7/18 16:35
 */
public class GoodsListFragment extends BaseRecyclerviewFragment {
    public  static  String TAB_TITLES = "tab标题";


    public static GoodsListFragment getInstance(String goodsType) {
        Bundle args = new Bundle();
        args.putString(TAB_TITLES, goodsType);
        GoodsListFragment fragment = new GoodsListFragment();
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    protected void freshlayoutOnLoadMore() {

    }

    @Override
    protected void freshlayoutOnRefresh() {

    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return   new SectionClassftAdapter(R.layout.item_section_content, R.layout.item_section_head, null);
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {
        mSmartrefreshlayout.setEnableLoadMore(false);
    }
}

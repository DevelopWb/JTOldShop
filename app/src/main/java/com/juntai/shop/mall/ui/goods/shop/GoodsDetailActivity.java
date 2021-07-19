package com.juntai.shop.mall.ui.goods.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.ui.goods.fmt.GoodsDetailsFragment;

/**
 * @aouther tobato
 * @description 描述  商品详情
 * @date 2021/7/19 16:08
 */
public class GoodsDetailActivity extends BaseAppActivity<ShopPresent> implements ShopContract.IShopContractView {

    private GoodsDetailsFragment detailsFragment;

    @Override
    protected ShopPresent createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.goods_detail_activity;
    }

    @Override
    public void initView() {
        detailsFragment = (GoodsDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.shop_fragment_goodsdetails);
        //是否直接展示商品
        int gid = getIntent().getIntExtra("goodsId", -1);
        if (gid != -1) {
            detailsFragment.setGoodsId(gid);
        }

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}

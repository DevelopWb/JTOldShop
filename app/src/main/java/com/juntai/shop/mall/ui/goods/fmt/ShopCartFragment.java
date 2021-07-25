package com.juntai.shop.mall.ui.goods.fmt;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseFragment;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.event.EventToSubmit;
import com.juntai.shop.mall.ui.goods.shop.ShopActivity;
import com.juntai.shop.mall.ui.goods.adt.ShopCartItemAdapter;
import com.juntai.shop.mall.utils.Arith;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @aouther tobato
 * @description 描述  购物车
 * @date 2021/7/18 9:09
 */
public class ShopCartFragment extends BaseFragment implements View.OnClickListener {
    RecyclerView recyclerView;
    private ShopCartItemAdapter cartItemAdapter;
    LinearLayout linearLayout;
    ImageView cartImage;
    View view1,view2;
    TextView tvCartNumber,tvCartPrice;
    ArrayList<CartItemLocB> locBArrayList = new ArrayList<>();
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shopcart;
    }

    @Override
    protected void initView() {

        recyclerView = getView(R.id.dialog_shopcart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartImage = getView(R.id.shop_car_iv);
        tvCartNumber = getView( R.id.selected_goods_account_tv);
        tvCartPrice = getView( R.id.goods_total_money_tv);
        linearLayout = getView(R.id.dialog_shopcart_Layout);
        cartImage.setOnClickListener(this);
        getView(R.id.shopping_car_tv).setOnClickListener(this);
        view1 = getView(R.id.dialog_shopcart_nullview1);
        view2 = getView(R.id.dialog_shopcart_nullview2);
        view1.setOnClickListener(this);
        getView(R.id.shop_cs_iv).setOnClickListener(this);
        getView(R.id.shopcart_confirm).setOnClickListener(this);
        getView(R.id.dialog_shopcart_clear).setOnClickListener(this);
        initData();

//        ImmersionBar.with(this).init();
    }

    protected void initData() {
        cartItemAdapter = new ShopCartItemAdapter(R.layout.item_shopcart,locBArrayList);
        recyclerView.setAdapter(cartItemAdapter);
        cartItemAdapter.setOnUpdateListener(() -> {
            update();
        });
        // important! setLayoutManager should be called after setAdapter
        //cartItemAdapter.expandAll();
        //
    }
    int cartSize;
    double sumPrice = 0.0,sumPack = 0.0;

    /**
     * 刷新购物车
     */
    public void update(){
        cartSize = 0;
        sumPrice = 0.0;
        sumPack = 0.0;
        locBArrayList.clear();
        if (MyApp.app.getCartBeansForShop((ShopActivity.shopId)) != null){
            for (CartItemLocB b: MyApp.app.getCartBeansForShop(ShopActivity.shopId)) {
                if (b.getNum() > 0){
                    locBArrayList.add(b);
                    sumPack = Arith.add(sumPack,b.getPackingCharges());
                    sumPrice = Arith.add(sumPrice,Arith.mul(b.getPrice() , b.getNum()));
                    cartSize++;
                }
                ((ShopActivity)getActivity()).refrehsGoods(b);
            }
        }
        if (MyApp.app.getCartBeansForShop((ShopActivity.shopId)).size() == 0){
            linearLayout.setVisibility(View.GONE);
        }
        if (cartSize == 0){
            tvCartNumber.setVisibility(View.INVISIBLE);
            tvCartPrice.setText("0");
            linearLayout.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
        }else {
            tvCartNumber.setVisibility(View.VISIBLE);
            tvCartNumber.setText("X" + cartSize);
            tvCartPrice.setText("" + sumPrice);
        }
        cartItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_car_iv://购物车图标，需要判断购物车数量，0不需要操作
                if (cartSize > 0){
                    if (linearLayout.getVisibility() == View.VISIBLE){
                        linearLayout.setVisibility(View.GONE);
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                    }else {
                        linearLayout.setVisibility(View.VISIBLE);
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.shopping_car_tv:
                cartImage.performClick();
                break;
            case R.id.dialog_shopcart_nullview1://
                linearLayout.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                break;
            case R.id.shop_cs_iv://联系商家
//                ModuleIm_Init.chat(mContext,ShopActivity.shopRyid,ShopActivity.shopName);
                break;
            case R.id.shopcart_confirm://确定
                EventBus.getDefault().post(new EventToSubmit());
                break;
            case R.id.dialog_shopcart_clear://清空购物车
                MyApp.app.getCartBeansForShop((ShopActivity.shopId)).clear();
                update();
                linearLayout.setVisibility(View.GONE);
                break;
        }
    }
}

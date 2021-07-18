package com.juntai.shop.mall.ui.goods.shop;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.bean.OpenLiveBean;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.mvp.IModel;
import com.juntai.mall.base.utils.RxScheduler;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.bean.ShopBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/7/16 9:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/7/16 9:02
 */
public class ShopPresent extends BasePresenter<IModel,ShopContract.IShopContractView> {
    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 获取商铺详情
     */
    public void getShopDetailInfo(int shopId,String tag) {
        AppNetModule.createrRetrofit()
                .shop(MyApp.app.getAccount(), MyApp.app.getUserToken(),shopId, MyApp.app.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopBean>(null) {
                    @Override
                    public void onSuccess(ShopBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
    /**
     * 收藏店铺
     */
    public void collectShop(int shopId,String tag) {
        AppNetModule.createrRetrofit()
                .shop(MyApp.app.getAccount(), MyApp.app.getUserToken(),shopId, MyApp.app.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopBean>(null) {
                    @Override
                    public void onSuccess(ShopBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

}

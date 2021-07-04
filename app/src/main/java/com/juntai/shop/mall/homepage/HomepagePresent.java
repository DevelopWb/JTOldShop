package com.juntai.shop.mall.homepage;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.mvp.IModel;
import com.juntai.mall.base.utils.RxScheduler;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.bean.CameraLocBean;
import com.juntai.shop.mall.bean.ShopLocationB;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/6/26 16:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/26 16:52
 */
public class HomepagePresent extends BasePresenter<IModel, HomepageContract.IHomepageView> {
    @Override
    protected IModel createModel() {
        return null;
    }


    /**
     * @param tag
     */
    public void getCameraData(String tag) {

        AppNetModule.createrRetrofit()
                .cameraLocation()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CameraLocBean>(getView()) {
                    @Override
                    public void onSuccess(CameraLocBean o) {
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
     * 获取数据
     */
    public void getShopsData(double lat, double lon,String tag) {
        AppNetModule.createrRetrofit()
                .shopsLocation(String.valueOf(lat), String.valueOf(lon))
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopLocationB>(getView()) {
                    @Override
                    public void onSuccess(ShopLocationB result) {
                        if (getView() != null) {
                            getView().onSuccess(tag, result);
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

package com.juntai.shop.mall.mine.set.address;

import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.mvp.IModel;
import com.juntai.mall.base.mvp.IPresenter;
import com.juntai.mall.base.mvp.IView;

import io.reactivex.Observable;

/**
 * @aouther Ma
 * @date 2019/3/9
 */
public interface AddressContract {
    interface View extends IView{
        void setDefault();
        void addAddress();
        void delAddress();
    }

    interface Presenter extends IPresenter<IView>{
        void setDefault(String addressid);
        void addAddress(String gezhong);
        void delAddress(String addressid);
    }

    interface Model extends IModel{
        Observable<BaseResult> setDefault(String addressid);
        Observable<BaseResult> addAddress(String gezhong);
        Observable<BaseResult> delAddress(String addressid);
    }
}

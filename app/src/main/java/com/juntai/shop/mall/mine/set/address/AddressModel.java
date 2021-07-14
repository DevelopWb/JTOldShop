package com.juntai.shop.mall.mine.set.address;

import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.mvp.BaseModel;

import io.reactivex.Observable;

/**
 * @aouther Ma
 * @date 2019/3/9
 */
public class AddressModel extends BaseModel implements AddressContract.Model{
    @Override
    public Observable<BaseResult> setDefault(String addressid) {
        return null;
    }

    @Override
    public Observable<BaseResult> addAddress(String gezhong) {
        return null;
    }

    @Override
    public Observable<BaseResult> delAddress(String addressid) {
        return null;
    }
}

package com.juntai.shop.mall.baseinfo;


import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseMvpFragment;
import com.juntai.mall.base.mvp.IPresenter;

/**
 * @aouther tobato
 * @description 描述  app的fragment的基类
 * @date 2020/7/18 16:43
 */
public abstract class BaseAppFragment<P extends IPresenter> extends BaseMvpFragment<P> {


    /**
     * 获取activity
     *
     * @return
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}

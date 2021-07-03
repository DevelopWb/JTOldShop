package com.juntai.mall.base.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.juntai.mall.base.mvp.IPresenter;
import com.juntai.mall.base.mvp.IView;
import com.juntai.mall.base.utils.ToastUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

public abstract class BaseMvpFragment<P extends IPresenter> extends BaseLazyFragment implements IView {

    protected P mPresenter;
    protected abstract P createPresenter();
    ProgressDialog progressDialog;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        progressDialog = new ProgressDialog(mContext);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        this.mPresenter = null;
    }

    @Override
    public void showLoading() {
//        progressDialog.show();
    }

    @Override
    public void hideLoading() {
//        progressDialog.dismiss();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void showDefaultMsg(String msg) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }
    /**
     * 查找viewid
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V getView(int viewId){
        return mRootView.findViewById(viewId);
    }


    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext,(String)o);
    }
}

package com.juntai.mall.base.mvp;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.juntai.mall.base.utils.Preconditions;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter<V>, LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    private M mModel;
    private V mView;

    /**
     * 获取 Model
     *
     * @return
     */
    protected abstract M createModel();

    public BasePresenter() {
        this.mModel = createModel();
    }

    @Override
    public void attachView(V mView) {
        this.mView = mView;
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView != null && mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
//        if (useEventBus()) {
//            EventBus.getDefault().register(this);
//        }
    }

    public V getView() {
        Preconditions.checkNotNull(mView, "%s cannot be null", IView.class.getName());
        return mView;
    }

    public M getModel() {
        Preconditions.checkNotNull(mModel, "%s cannot be null", IModel.class.getName());
        return mModel;
    }
    /**
     * 解除绑定
     */
    public void detachView() {
        unDispose();// 解除订阅
        if (mModel != null)
            mModel.onDetach();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有的 Disposable 放入集中处理
    }

    /**
     * 停止正在进行的任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证Activity结束时取消
        }
    }
}

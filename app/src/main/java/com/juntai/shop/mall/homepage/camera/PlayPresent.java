package com.juntai.shop.mall.homepage.camera;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.bean.OpenLiveBean;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.mvp.IModel;
import com.juntai.mall.base.utils.RxScheduler;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.bean.stream.StreamCameraDetailBean;
import com.juntai.shop.mall.utils.UserInfoManager;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:49
 */
public class PlayPresent extends BasePresenter<IModel, PlayContract.IPlayView> implements PlayContract.IPlayPresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    @Override
    public void openStream(String channelid, String type, String videourltype,String tag) {
         AppNetModule.createrRetrofit()
                .openStream(channelid,type,videourltype)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OpenLiveBean>(null) {
                    @Override
                    public void onSuccess(OpenLiveBean o) {
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
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                    .addFormDataPart("account", UserInfoManager.getUserAccount())
                .addFormDataPart("token", UserInfoManager.getUserToken())
                .addFormDataPart("uid", String.valueOf(UserInfoManager.getUserId()));
    }
    /**
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
    }

    @Override
    public void capturePic(String channelid, String type, String tag) {
        AppNetModule.createrRetrofit()
                .capturePic(channelid,type)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OpenLiveBean>(PlayContract.GET_STREAM_CAMERA_THUMBNAIL.equals(tag)?null:getView()) {
                    @Override
                    public void onSuccess(OpenLiveBean o) {
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

    @Override
    public void getStreamCameraDetail(RequestBody requestBody,String tag) {
        AppNetModule.createrRetrofit()
                .getStreamCameraDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraDetailBean>(null) {
                    @Override
                    public void onSuccess(StreamCameraDetailBean o) {
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

    @Override
    public void uploadStreamCameraThumbPic(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .uploadStreamCameraThumbPic(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.msg);
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

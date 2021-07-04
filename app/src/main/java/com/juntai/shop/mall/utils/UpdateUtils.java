package com.juntai.shop.mall.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.GsonTools;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.UpgradeBean;
import com.juntai.shop.mall.ui.dialog.UpdateDialog;

/**
 * Created by Ma
 * on 2019/12/24
 */
public class UpdateUtils {
    boolean isForceUpdate = false;
    String version;
    public void update(Context mContext,boolean tishi){
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestMethod(HttpRequestMethod.POST)
                .setRequestUrl(AppHttpPath.UPDATE_VERSION)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result) {
                        //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据
                        LogUtil.d("更新"+result);
                        UpgradeBean upgradeBean = GsonTools.changeGsonToBean(result,UpgradeBean.class);
                        version = upgradeBean.getReturnValue().getVersionsName();
                        isForceUpdate = upgradeBean.getReturnValue().isConstraintUpdate();
                        if (AppUtils.getVersionCode(mContext) < upgradeBean.getReturnValue().getVersionsCode()){
                            return UIData.create()
                                    .setTitle(upgradeBean.getReturnValue().getFileName())
                                    .setContent(upgradeBean.getReturnValue().getUpdateContent())
                                    .setDownloadUrl(upgradeBean.getReturnValue().getDownloadLink());
                        }else {
                            if (tishi){
                                ToastUtils.toast(mContext,"已是最新版本");
                            }
                            //如果是最新版本直接return null
                            return null;
                        }
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        LogUtil.d("更新"+message);
                    }
                })
                .setDownloadAPKPath(FileCacheUtils.getAppPath())//自定义下载路径
                .setApkName(AppUtils.getAppName())
                .setCustomVersionDialogListener((context, versionBundle) -> {
                    UpdateDialog updateDialog = new UpdateDialog(context,R.style.BaseDialog,R.layout.dialog_update);
                    //versionBundle 就是UIData，之前开发者传入的，在这里可以拿出UI数据并展示
                    TextView textView = updateDialog.findViewById(R.id.update_content);
                    textView.setText(versionBundle.getContent());
                    //强制更新，隐藏取消按钮
                    if (isForceUpdate) updateDialog.findViewById(R.id.versionchecklib_version_dialog_cancel).setVisibility(View.GONE);
                    return updateDialog;
                })
                .setForceUpdateListener(() -> {
                    //强制更新
                    if (isForceUpdate){
                        MyApp.app.clearActivitys();
                    }
                    cancle();
                })
                .executeMission(mContext);
    }
    public static void cancle(){
        AllenVersionChecker.getInstance().cancelAllMission();
    }
}

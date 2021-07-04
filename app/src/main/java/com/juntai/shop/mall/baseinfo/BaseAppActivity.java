package com.juntai.shop.mall.baseinfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.utils.DialogUtil;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.bdmap.utils.MD5;
import com.juntai.mall.bdmap.utils.NagivationUtils;
import com.juntai.shop.mall.baseinfo.selectPics.BaseSelectPicsActivity;
import com.juntai.shop.mall.ui.act.LoginActivity;
import com.juntai.shop.mall.utils.UserInfoManager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 导航
     *
     * @param endLatlng 目的地
     * @param endName   目的地名称
     */
    public void navigationLogic(LatLng endLatlng, String endName) {
        AlertDialog.Builder build = DialogUtil.getDialog(mContext);
        final String item_list[] = {"腾讯地图", "高德地图", "百度地图"};
        build.setItems(item_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (item_list[which]) {
                    case "腾讯地图":
                        NagivationUtils.getInstant().openTencent(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    case "高德地图":
                        NagivationUtils.getInstant().openGaoDeMap(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    case "百度地图":
                        NagivationUtils.getInstant().openBaiduMap(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    default:
                        break;
                }
            }
        });
        build.setTitle("请选择导航方式");
        setAlertDialogHeightWidth(build.show(),-1,0);
    }


    /**
     * 发送更新头像的广播
     */
    public void broadcasetRefreshHead() {
        Intent intent = new Intent();
        intent.setAction("action.refreshHead");
        sendBroadcast(intent);
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account", UserInfoManager.getUserAccount());
        builder.add("token", UserInfoManager.getUserToken());
        builder.add("userId", String.valueOf(UserInfoManager.getUserId()));
        return builder;
    }

    /**
     * 获取requestBody
     *
     * @return
     */
    public RequestBody getRequestBody(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
    }


    //    /**
    //     * 是否是内部账号
    //     *
    //     * @return
    //     */
    //    public boolean isInnerAccount() {
    //        return UserInfoManager.isTest();
    //    }

    @Override
    public void singleLogin() {
        //        ToastUtils.toast(mContext, "登录过期,请重新登录");
        //        Hawk.delete(HawkProperty.USER_INFO);
        //        ActivityManagerTool.getInstance().finishApp();
        //        startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }

    /**
     * 将list中的数据转成字符串  并以逗号隔开
     *
     * @return
     */
    public String getListToString(List<String> arrays) {
        if (arrays == null || arrays.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(arrays.size());
        for (String selectedServicePeople : arrays) {
            sb.append(selectedServicePeople + ",");
        }
        String people = sb.toString();
        if (!TextUtils.isEmpty(people)) {
            people = people.substring(0, people.length() - 1);
        }
        return people;
    }

    /**
     * 复制电话号码
     */
    public void copyTelephoneNum(String text) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("simple text", text);
        //传入clipdata对象.
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public boolean requestLocation() {
        return false;
    }


    /**
     * 加密密码
     *
     * @param pwd
     * @return
     */
    protected String encryptPwd(String account, String pwd) {
        return MD5.md5(String.format("%s#%s", account, pwd));
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }

    /**
     * 跳转登录
     */
    public  void goLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 获取签名图片的路径
     *  必须返回null 要不不能判定有没有签名
     * @return
     */
    protected String getSignPath(String picName) {
        String picPath = FileCacheUtils.getAppImagePath() + picName;
        File file = new File(picPath);
        return file.exists()?picPath:null;
    }
    /**
     * 获取图片的路径
     *
     * @return
     */
    protected String getHeadPath(String picName) {
        String picPath = FileCacheUtils.getAppImagePath() + picName;
        File file = new File(picPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picPath;
    }

    @Override
    protected String getTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }
}

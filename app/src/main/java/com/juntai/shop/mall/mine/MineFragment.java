package com.juntai.shop.mall.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juntai.mall.base.base.BaseLazyFragment;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.mall.im.UserIM;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.UserB;
import com.juntai.shop.mall.mine.set.SettingActivity;
import com.juntai.shop.mall.ui.act.LoginActivity;
import com.juntai.shop.mall.mine.ui.MyAssessActivity;
import com.juntai.shop.mall.mine.ui.MyCollectActivity;
import com.juntai.shop.mall.mine.ui.MyOrderActivity;
import com.juntai.shop.mall.utils.AppCode;
import com.juntai.shop.mall.utils.AppUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的
 */
public class MineFragment extends BaseLazyFragment implements View.OnClickListener {
    ImageView headImageView;
    TextView mCommitTv;
    TextView tvNick, tvOrder, tvCom, tvColl, tvShare, tvWx, tvQQ;
    UserB userB;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        headImageView = getView(R.id.my_head_image);
        tvNick = getView(R.id.my_nickname);
        tvOrder = getView(R.id.my_order_num);
        tvCom = getView(R.id.my_com_num);
        tvColl = getView(R.id.my_coll_num);
        tvShare = getView(R.id.my_share_num);
        tvWx = getView(R.id.my_wx);
        tvQQ = getView(R.id.my_qq);
        tvWx.setOnClickListener(MineFragment.this);
        tvQQ.setOnClickListener(MineFragment.this);
        headImageView.setOnClickListener(this);
        getView(R.id.my_order).setOnClickListener(this);
        getView(R.id.my_com).setOnClickListener(this);
        getView(R.id.my_coll).setOnClickListener(this);
        getView(R.id.my_share).setOnClickListener(this);
        getView(R.id.my_setting).setOnClickListener(this);
        if (MyApp.app.getUser() == null) {
            tvNick.setText("未登录");
            tvNick.setOnClickListener(toLoginListener);
            headImageView.setOnClickListener(toLoginListener);
        } else {
            tvNick.setOnClickListener(toUserInfoListener);
            headImageView.setOnClickListener(toUserInfoListener);
            tvNick.setText(MyApp.app.getUser().getReturnValue().getNickName());
        }
        mCommitTv = getView(R.id.commit_form_tv);
        mCommitTv.setOnClickListener(v -> {
            loginout();
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        //getdata();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (headImageView != null) {
            getdata();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order:
                //订单
                startActivity(new Intent(mContext, MyOrderActivity.class));
                break;
            case R.id.my_com:
                //评价
                startActivity(new Intent(mContext, MyAssessActivity.class));
                break;
            case R.id.my_coll:
                //收藏
                startActivity(new Intent(mContext, MyCollectActivity.class).putExtra("function", 1));
                break;
            case R.id.my_share:
                //分享
                startActivity(new Intent(mContext, MyCollectActivity.class).putExtra("function", 2));
                break;
            case R.id.my_wx:
                if (MyApp.app.getUser() == null) {
                    //跳转到登录界面
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                //绑定微信
                if (userB.getReturnValue().getWeChatId() == null || userB.getReturnValue().getWeChatId().isEmpty()) {
                    loginForQQWeChat(Wechat.NAME);
                } else {
                    ToastUtils.toast(mContext, "已绑定");
                }
                break;
            case R.id.my_qq:
                if (MyApp.app.getUser() == null) {
                    //跳转到登录界面
                    startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                //绑定qq
                if (userB.getReturnValue().getQqId() == null || userB.getReturnValue().getQqId().isEmpty()) {
                    loginForQQWeChat(QQ.NAME);
                } else {
                    ToastUtils.toast(mContext, "已绑定");
                }
                break;
            case R.id.my_setting:
                //设置
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    /**
     * 登录监听
     */
    View.OnClickListener toLoginListener = v -> {
        startActivityForResult(new Intent(mContext, LoginActivity.class), AppCode.LOGIN);
    };
    /**
     * 用户信息
     */
    View.OnClickListener toUserInfoListener = v -> {
        if (userB != null) {
            startActivityForResult(new Intent(mContext, MyInfoActivity.class), AppCode.REFRESH);
        }
    };

    @SuppressLint("CheckResult")
    public void getdata() {
        AppNetModule.createrRetrofit()
                .userInfo(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserB>() {
                    @Override
                    public void accept(UserB result) throws Exception {
                        if (result.success && result.getReturnValue() != null) {
                            mCommitTv.setVisibility(View.VISIBLE);
                            userB = result;
                            MyApp.app.customerServicePhone = userB.getReturnValue().getCustomerServicePhone();
                            tvNick.setText(result.getReturnValue().getNickName());
                            tvOrder.setText(String.valueOf(result.getReturnValue().getOrderFormNum()));
                            tvCom.setText(String.valueOf(result.getReturnValue().getCommodityEvaluateNum()));
                            tvColl.setText(String.valueOf(result.getReturnValue().getMyCollectNum()));
                            tvShare.setText(String.valueOf(result.getReturnValue().getMyShareNum()));
                            tvWx.setText(result.getReturnValue().getWeChatName());
                            tvQQ.setText(result.getReturnValue().getQqName());
                            //
//                            ModuleIm_Init.setUser(new UserIM(String.valueOf(result.getReturnValue().getId()),result.getReturnValue().getNickName(), AppHttpPath.IMAGE + result.getReturnValue().getHeadUrl()));
                            ImageLoadUtil.loadCircularImage(
                                    mContext,
                                    AppHttpPath.IMAGE + result.getReturnValue().getHeadUrl(),
                                    R.mipmap.ic_launcher,
                                    R.mipmap.ic_launcher,
                                    headImageView);
                            //微信qq
                            if (result.getReturnValue().getWeChatId() == null || result.getReturnValue().getWeChatId().equals("")) {
                                tvWx.setText("未绑定");

                            } else {
                                tvWx.setText(result.getReturnValue().getWeChatName());
                            }
                            if (result.getReturnValue().getQqId() == null || result.getReturnValue().getQqId().equals("")) {
                                tvQQ.setText("未绑定");
                            } else {
                                tvQQ.setText(result.getReturnValue().getQqName());
                            }
                            //
                            if (MyApp.app.getUser() == null) {
                                tvNick.setText("未登录");
                                tvNick.setOnClickListener(toLoginListener);
                                headImageView.setOnClickListener(toLoginListener);
                            } else {
                                tvNick.setOnClickListener(toUserInfoListener);
                                headImageView.setOnClickListener(toUserInfoListener);
                                tvNick.setText(userB.getReturnValue().getNickName());
                            }
                        }
                    }
                });
    }

    /**
     * 绑定
     */
    private void bind() {
        AppNetModule.createrRetrofit()
                .bind(MyApp.app.getAccount(), MyApp.app.getUserToken(), weChatId, weChatName, qqId, qqName, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult o) {
                        getdata();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }

    /**
     * 退出登录
     */
    public void loginout() {
        AppNetModule.createrRetrofit()
                .loginOut(MyApp.app.getAccount(), MyApp.app.getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        userB = null;
//                        ModuleIm_Init.logout();
                        tvNick.setText("未登录");

                        tvNick.setOnClickListener(toLoginListener);
                        headImageView.setOnClickListener(toLoginListener);

                        tvOrder.setText("");
                        tvCom.setText("");
                        tvColl.setText("");
                        tvShare.setText("");
                        tvWx.setText("");
                        tvQQ.setText("");
                        mCommitTv.setVisibility(View.GONE);
                        //

                        SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, "");
                        MyApp.app.setUserBean(null);
                        ToastUtils.success(mContext, result.msg);
                        startActivityForResult(new Intent(mContext, LoginActivity.class), AppCode.LOGIN);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }

    String weChatId, weChatName, qqId, qqName;
    PlatformDb platDB;

    /**
     * 第三方数据
     *
     * @param name
     */
    public void loginForQQWeChat(String name) {
        qqId = "";
        qqName = "";
        weChatId = "";
        weChatName = "";
        Platform plat = ShareSDK.getPlatform(name);
        if (!plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
            if (name.equals(QQ.NAME)) {
                ToastUtils.warning(mContext, "未安装QQ");
            } else {
                ToastUtils.warning(mContext, "未安装微信");
            }
        }
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //数据
                LogUtil.d("userinfo=" + platform.getDb().exportData());
                LogUtil.d("userinfo=" + platform.getName());
                LogUtil.d("userinfo=" + hashMap.toString());
                //用户资源都保存到res
                //通过打印res数据看看有哪些数据是你想要的
                if (i == Platform.ACTION_USER_INFOR) {
                    platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    platDB.getToken();
                    platDB.getUserGender();
                    platDB.getUserIcon();
                    platDB.getUserId();
                    platDB.getUserName();
                    if (platform.getName().equals(QQ.NAME)) {
                        qqId = platDB.getUserId();
                        qqName = platDB.getUserName();
                    } else {
                        weChatId = platDB.getUserId();
                        weChatName = platDB.getUserName();
                    }
                    getActivity().runOnUiThread(() -> bind());
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e(throwable.toString());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });//授权回调监听，监听oncomplete，onerror，oncancel三种状态
        if (plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if (plat.isAuthValid()) {
            //判断是否已经存在授权状态，可以根据自己的登录逻辑设置
            Toast.makeText(mContext, "已经授权过了", Toast.LENGTH_SHORT).show();
            return;
        }
        ShareSDK.setActivity(getActivity());//抖音登录适配安卓9.0
        plat.showUser(MyApp.app.getAccount());    //要数据不要功能，主要体现在不会重复出现授权界面
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCode.LOGIN && resultCode == getActivity().RESULT_OK) {
            tvNick.setOnClickListener(toUserInfoListener);
            headImageView.setOnClickListener(toUserInfoListener);
//            getdata();
        }
        if (requestCode == AppCode.REFRESH && resultCode == getActivity().RESULT_OK) {
            getdata();
        }
    }
}

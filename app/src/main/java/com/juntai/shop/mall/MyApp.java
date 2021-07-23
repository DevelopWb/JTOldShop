package com.juntai.shop.mall;


import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.juntai.mall.base.app.BaseApplication;
import com.juntai.mall.base.utils.GsonTools;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.LoginBean;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.utils.ActivityTool;
import com.juntai.shop.mall.utils.AppUtils;
import com.mob.MobSDK;
import com.orhanobut.hawk.Hawk;
import com.videogo.openapi.EZOpenSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApp extends BaseApplication {
    private LoginBean loginB;
    public static MyApp app;
    BDLocation bdLocation;
    public String customerServicePhone;
    public ActivityTool activityTool;

    public long codeOverTime = 0;//验证码获取剩余时间
    //需要售后的商品
    public List<OrderCommodityListBean> goodsReturnBeans = new ArrayList<>();
    //商铺id,
    HashMap<Integer, ArrayList<CartItemLocB>> cartMap = new HashMap<>();
    @Override
    public void appBackground(boolean isBackground, Activity activity) {

    }

    @Override
    public String getTinkerId() {
        return "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Hawk.init(this).build();
        activityTool = new ActivityTool();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        //
        MobSDK.init(this);
//        ModuleIm_Init.init(this);
    }
    public void setUserBean(LoginBean loginBean) {
        loginB = loginBean;
    }

    public LoginBean getUser(){
        if (loginB == null){
            try {
                loginB = GsonTools.changeGsonToBean(
                        SPTools.getString(app.getApplicationContext(), AppUtils.SP_KEY_LOGIN,""),
                        LoginBean.class);
            }catch (Exception e){
                loginB = null;
            }
        }
        return loginB;
    }
    /**
     * 获取usertoken
     * @return
     */
    public String getUserToken(){
        if (getUser() == null){
            return "";
        }else {
            return getUser().getReturnValue().getToken();
        }
    }

    /**
     * 获取账号
     * @return
     */
    public String getAccount(){
        if (getUser() == null){
            return "";
        }else {
            return getUser().getReturnValue().getAccount();
        }
    }
    /**
     * 设置萤石id
     * @param appKey
     * @param accessToken
     */
    public void setYsAccount(String appKey,String accessToken) {
        EZOpenSDK.initLib(this, appKey);
        EZOpenSDK.getInstance().setAccessToken(accessToken);
//        for (VideoTokenBean.DataBean bean:videoPlayer.getData()) {
//            /** * APP_KEY请替换成自己申请的 */
//            if (id.equals(bean.getMaId()+"")){
//                LogUtil.e("key="+bean.getAppKey()+"  token="+bean.getAccesstoken());
//                EZOpenSDK.initLib(this, bean.getAppKey());
//                EZOpenSDK.getInstance().setAccessToken(bean.getAccesstoken());
//            }
//        }
    }
    /**
     * 获取id
     * @return
     */
    public int getUid(){
        if (getUser() == null){
            return 0;
        }else {
            return getUser().getReturnValue().getId();
        }
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    /**
     * 获取店铺下的购物车
     * @param shopid
     * @return
     */
    public ArrayList<CartItemLocB> getCartBeansForShop(int shopid){
        if (cartMap.get(shopid) == null){
            cartMap.put(shopid,new ArrayList<>());
        }
        return cartMap.get(shopid);
    }

    /**
     * 获取单个购物车商品
     * @param shopid
     * @param goodid
     * @param spcid
     * @return
     */
    public CartItemLocB getCartBean(int shopid,int goodid,int spcid){
        if (cartMap.get(shopid) == null){
            cartMap.put(shopid,new ArrayList<>());
        }
        for (CartItemLocB b:cartMap.get(shopid)) {
            if (b.getGoodsId() == goodid && b.getSpcId() == spcid){
                return b;
            }
        }
        return null;
    }
    ArrayList<CartItemLocB> cartItemLocBS;
    boolean isD;

    /**
     * 添加单个商品到购物车
     * @param shopId
     * @param bean
     */
    public void setCartBean(int shopId,CartItemLocB bean){
        isD = false;
        cartItemLocBS = MyApp.app.getCartBeansForShop((shopId));
        for (int i = 0; i < cartItemLocBS.size(); i++) {
            if (cartItemLocBS.get(i).getGoodsId() == bean.getGoodsId()){
                //本地购物车存在。
                if (cartItemLocBS.get(i).getSpcId() == bean.getSpcId()){
                    //同规格
                    bean.setCartId(cartItemLocBS.get(i).getCartId());
                    cartItemLocBS.remove(i);
                }else {
                    //不同规格
                }
                cartItemLocBS.add(i,bean);
//                if (bean.getNum() != 0){
//                    //数量为不为0
//                    cartItemLocBS.add(i,bean);
//                }
                isD = true;
                break;
            }
        }
        if (!isD){
            //本地购物车不存在时，直接添加
            cartItemLocBS.add(bean);
        }
    }

    /**
     * 添加购物（整个商铺）
     * @param shopid
     * @param beans
     */
    public void setCartBeans(Integer shopid,ArrayList<CartItemLocB> beans){
        cartMap.put(shopid,beans);
    }
}

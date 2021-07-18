package com.juntai.shop.mall.homepage;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.juntai.mall.base.net.FileRetrofit;
import com.juntai.mall.bdmap.act.DistanceUtilActivity;
import com.juntai.mall.bdmap.act.PanoramaActivity;
import com.juntai.mall.bdmap.utils.clusterutil.clustering.Cluster;
import com.juntai.mall.bdmap.utils.clusterutil.clustering.ClusterManager;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppFragment;
import com.juntai.shop.mall.bean.CameraLocBean;
import com.juntai.shop.mall.bean.MyItem;
import com.juntai.shop.mall.bean.ShopLocationB;
import com.juntai.shop.mall.homepage.camera.ClusterClickAdapter;
import com.juntai.shop.mall.homepage.camera.ijkplayer.PlayerLiveActivity;
import com.juntai.shop.mall.ui.act.SearchActivity;
import com.juntai.shop.mall.ui.act.WeatherActivity;
import com.juntai.shop.mall.ui.dialog.MapDateListDialog;
import com.juntai.shop.mall.utils.ImageUtil;
import com.juntai.shop.mall.utils.StringTools;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页
 */
public class HomepageFragment extends BaseAppFragment<HomepagePresent> implements View.OnClickListener, BaiduMap.OnMapLoadedCallback, HomepageContract.IHomepageView
        , ClusterManager.OnClusterClickListener<MyItem>,
        ClusterManager.OnClusterItemClickListener<MyItem>{
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus mMapStatus;
    //侧滑布局=======================================
    private DrawerLayout drawerLayout = null;
    private RelativeLayout sideLayout = null;
    //
    private ClusterManager<MyItem> mClusterManager;
    ArrayList<ShopLocationB.ReturnValueBean> arrayList = new ArrayList<>();
    //当前点击的marker
    Marker nowMarker;
    //地图，天气，视频，
    CheckBox checkBoxMap, checkBoxWeather, checkBoxVideo;
    //2d,3d,卫星图，测距
    ImageView type2D, type3D, typeWx, typeQj;
    //全景图，路况
    ImageView typeLk, typeCj;
    boolean isCheckedLk = false, isCheckedCj = false;
    //
    TextView tv2D, tv3D, tvWx, tvQj, tvLk, tvCj, nowTv;
    MapDateListDialog mapDateListDialog = new MapDateListDialog();
    int dateType;//1-商铺,2-摄像头
    int nowShopId = 0;
    boolean isUpdateShop = true;//是否需要刷新周边店铺
    private BitmapDescriptor bitmapDescriptor;
    private ClusterClickAdapter clusterClickAdapter;
    private BottomSheetDialog mapBottomDialog;

    @Override
    protected int getLayoutRes() {
        return R.layout.main_map;
    }

    @Override
    protected void initView() {
        drawerLayout = getView(R.id.drawerlayout);
        sideLayout = getView(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                checkBoxMap.setChecked(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        //右侧菜单
        checkBoxMap = getView(R.id.map_menu_map);
        checkBoxWeather = getView(R.id.map_menu_weather);
        checkBoxVideo = getView(R.id.map_menu_video);
        type2D = getView(R.id.drawer_map_type_2d);
        type3D = getView(R.id.drawer_map_type_3d);
        typeWx = getView(R.id.drawer_map_type_wx);
        typeQj = getView(R.id.drawer_map_type_qj);
        typeLk = getView(R.id.drawer_map_type_lk);
        typeCj = getView(R.id.drawer_map_type_cj);
        tv2D = getView(R.id.drawer_text_2d);
        tv3D = getView(R.id.drawer_text_3d);
        tvWx = getView(R.id.drawer_text_wx);
        tvQj = getView(R.id.drawer_text_qj);
        tvLk = getView(R.id.drawer_text_lk);
        tvCj = getView(R.id.drawer_text_cj);
        checkBoxMap.setOnClickListener(this);
        checkBoxWeather.setOnClickListener(this);
        checkBoxVideo.setOnClickListener(this);
        type2D.setOnClickListener(this);
        type3D.setOnClickListener(this);
        typeWx.setOnClickListener(this);
        typeQj.setOnClickListener(this);
        typeLk.setOnClickListener(this);
        typeCj.setOnClickListener(this);
        tv2D.setTextColor(getResources().getColor(R.color.colorTheme));
        type2D.setImageResource(R.mipmap.ic_map_type2d_check);
        nowTv = tv2D;
        //
        getView(R.id.map_zoom_big).setOnClickListener(this);
        getView(R.id.map_zoom_small).setOnClickListener(this);
        getView(R.id.map_location_my).setOnClickListener(this);
        getView(R.id.map_search).setOnClickListener(this);
        //
        mMapView = getView(R.id.bdMapView);
        mBaiduMap = mMapView.getMap();
        initBaiduMapConfig();
        onMarkerClick();


    }

    private void onMarkerClick() {
        mBaiduMap.setOnMarkerClickListener(marker -> {
            //不是大的聚合点
            if (!mClusterManager.getClusterMarkerCollection().getMarkers().contains(marker)) {
                if (nowMarker != null) {
                    nowMarker.setIcon(bitmapDescriptor);
                }
                //marker.setIcon(BitmapDescriptorFactory.fromBitmap(compoundBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_client_location_pre), BitmapFactory.decodeResource(getResources(),R.mipmap.ic_my_default_head))));
                nowMarker = marker;
            }
            //在nowMarker赋值之后
            mClusterManager.onMarkerClick(marker);
            return false;
        });
    }


    /**
     * 获取到定位数据
     *
     * @param bdLocation
     */
    public void onLocationReceived(BDLocation bdLocation) {

        MyApp.app.setBdLocation(bdLocation);
        if (isUpdateShop) {
            isUpdateShop = false;
        }
        setMap(bdLocation.getLatitude(), bdLocation.getLongitude());
    }

    //地图设置
    private void initBaiduMapConfig() {

        // 隐藏百度的LOGO
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 不显示地图上比例尺
        mMapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);
        //定位的属性:定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色以及精度圈边框颜色5个属性。
        mBaiduMap.setMyLocationConfiguration(
                new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                        true,
                        BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_center),
                        0x55dfa820, 0xAAcd940a));

        mBaiduMap.setOnMapLoadedCallback(this);
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(mContext, mBaiduMap);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(HomepageFragment.this);//点点击
        mClusterManager.setOnClusterClickListener(HomepageFragment.this);//聚合展开
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_search:
                //搜索
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.map_location_my:
                //回到自己位置
                checkBoxMap.setChecked(false);
                checkBoxWeather.setChecked(false);
                checkBoxVideo.setChecked(false);
                if (MyApp.app.getBdLocation() != null) {
                    setMap(MyApp.app.getBdLocation().getLatitude(), MyApp.app.getBdLocation().getLongitude());
                }
                break;
            case R.id.map_zoom_big:
                //地图放大
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.map_zoom_small:
                //地图缩小
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
            case R.id.map_menu_map://地图
                checkBoxWeather.setChecked(false);
                checkBoxVideo.setChecked(false);
                if (checkBoxMap.isChecked()) {
                    drawerLayout.openDrawer(sideLayout);
                } else {
                    drawerLayout.closeDrawer(sideLayout);
                }
                break;
            case R.id.map_menu_weather://天气
                // TODO: 2021/7/17 暂时跳转到店铺详情
                MyApp.app.activityTool.toShopActivityTest(mContext,14);
//                checkBoxMap.setChecked(false);
//                checkBoxVideo.setChecked(false);
//                startActivity(new Intent(mContext, WeatherActivity.class));
//                checkBoxWeather.setChecked(false);
                break;
            case R.id.map_menu_video://视频
                checkBoxMap.setChecked(false);
                checkBoxWeather.setChecked(false);
                mPresenter.getCameraData(AppHttpPath.CAMERA_LOCATION);
                break;
            case R.id.drawer_map_type_2d://2d
                //普通地图（默认3d，没有2d）
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mapType(tv2D, type2D, R.mipmap.ic_map_type2d_check);
                break;
            case R.id.drawer_map_type_3d://3d
                //普通地图 ,mBaiduMap是地图控制器对象
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mapType(tv3D, type3D, R.mipmap.ic_map_type3d_check);
                break;
            case R.id.drawer_map_type_wx://卫星图
                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                mapType(tvWx, typeWx, R.mipmap.ic_map_type_wx_check);
                break;
            case R.id.drawer_map_type_qj://全景图
                startActivity(new Intent(mContext, PanoramaActivity.class)
                        .putExtra("lat", MyApp.app.getBdLocation().getLatitude())
                        .putExtra("lon", MyApp.app.getBdLocation().getLongitude()));
                mapType(tvQj, typeQj, R.mipmap.ic_map_type_qj_check);
                break;
            case R.id.drawer_map_type_lk://路况
                //开启交通图
                isCheckedLk = !isCheckedLk;
                textColor(isCheckedLk, tvLk, typeLk, R.mipmap.ic_map_type_lk_check, R.mipmap.ic_map_type_lk);
                mBaiduMap.setTrafficEnabled(isCheckedLk);
                break;
            case R.id.drawer_map_type_cj://测距
                //isCheckedCj = !isCheckedCj;
                //textColor(isCheckedCj,tvCj,typeCj,R.mipmap.ic_map_type_cj_check,R.mipmap.ic_map_type_cj);
                startActivity(new Intent(mContext, DistanceUtilActivity.class)
                        .putExtra("lat", MyApp.app.getBdLocation().getLatitude())
                        .putExtra("lon", MyApp.app.getBdLocation().getLongitude()));
                break;
        }
    }

    /**
     * 设置文字颜色-可不选
     *
     * @param isCheck
     * @param tv
     */
    public void textColor(boolean isCheck, TextView tv, ImageView imageView, int checkedImage, int image) {
        nowTv.setTextColor(getResources().getColor(R.color.black));
        imageView.setImageResource(isCheck ? checkedImage : image);
        tv.setTextColor(isCheck ? getResources().getColor(R.color.colorTheme) : getResources().getColor(R.color.black));
        nowTv = tv;
    }

    /**
     * 地图模式-必选
     *
     * @param tv
     * @param imageView
     * @param checkedImage
     */
    public void mapType(TextView tv, ImageView imageView, int checkedImage) {
        type2D.setImageResource(R.mipmap.ic_map_type2d);
        type3D.setImageResource(R.mipmap.ic_map_type3d);
        typeWx.setImageResource(R.mipmap.ic_map_type_wx);
        typeQj.setImageResource(R.mipmap.ic_map_type_qj);
        nowTv.setTextColor(getResources().getColor(R.color.black));
        imageView.setImageResource(checkedImage);
        tv.setTextColor(getResources().getColor(R.color.colorTheme));
        nowTv = tv;
    }

    public void showDialog() {
        mapDateListDialog.setDate(arrayList);
        getChildFragmentManager().beginTransaction().remove(mapDateListDialog).commit();
        mapDateListDialog.show(getChildFragmentManager(), "mapdate");
    }




    /**
     * 设置地图
     */
    public void setMap(double lat, double lon) {
        mMapStatus = new MapStatus.Builder().target(new LatLng(lat, lon)).zoom(16).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));

        //显示定位
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(MyApp.app.getBdLocation().getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(MyApp.app.getBdLocation().getDirection()).latitude(MyApp.app.getBdLocation().getLatitude())
                .longitude(MyApp.app.getBdLocation().getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mPresenter.getShopsData(lat, lon,AppHttpPath.SHOP_LOCATION);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        // 在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
        super.onPause();
    }


    @Override
    public void onDestroy() {
        // 在activity执行onDestroy时必须调用mMapView.onDestroy()
        mClusterManager.clearItems();
        mMapView.onDestroy();
        if (mapBottomDialog != null) {
            mapBottomDialog.dismiss();
            mapBottomDialog = null;
        }
        super.onDestroy();
    }

    /**
     * 向地图添加Marker点-商铺
     *
     * @param result
     */
    public void addMarkers(ShopLocationB result) {
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_shop);
        List<MyItem> items = new ArrayList<>();
        for (ShopLocationB.ReturnValueBean valueBean : result.getReturnValue()) {
            items.add(new MyItem<>(valueBean, valueBean.getLatitude(), valueBean.getLongitude(),bitmapDescriptor));
        }

        mClusterManager.clearItems();
        mClusterManager.addItems(items);
        mClusterManager.cluster();
        dateType = 1;
    }

    /**
     * 向地图添加Marker点-摄像头
     *
     * @param result
     */
    public void addMarkersCmera(CameraLocBean result) {
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_camera);
        List<MyItem> items = new ArrayList<>();
        for (CameraLocBean.ReturnValueBean valueBean : result.getReturnValue()) {
            items.add(new MyItem(valueBean, Double.parseDouble(valueBean.getLatitude()), Double.parseDouble(valueBean.getLongitude()),bitmapDescriptor));
        }
        mClusterManager.clearItems();
        mClusterManager.addItems(items);
        mClusterManager.cluster();
        dateType = 2;
    }


    @Override
    protected HomepagePresent createPresenter() {
        return new HomepagePresent();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.CAMERA_LOCATION:
                //获取摄像头数据
                CameraLocBean cameraLocBean = (CameraLocBean) o;
                // 添加Marker点
                addMarkersCmera(cameraLocBean);
                break;
            case AppHttpPath.SHOP_LOCATION:
                //获取商铺数据
                ShopLocationB result = (ShopLocationB) o;
                // 添加Marker点
                addMarkers(result);
                break;
            default:
                break;
        }
    }



    @Override
    public void onMapLoaded() {
        mMapStatus = new MapStatus.Builder().zoom(9).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        //大的聚合点
        //摄像头
        if (dateType == 2){
            List<MyItem> items = new ArrayList<MyItem>(cluster.getItems());
            if (mapBottomDialog == null) {
                mapBottomDialog = new BottomSheetDialog(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_list_layout, null);
                mapBottomDialog.setContentView(view);
                RecyclerView bottomRv = view.findViewById(R.id.map_bottom_list_rv);
                clusterClickAdapter = new ClusterClickAdapter(R.layout.care_item_layout);
                getBaseActivity().initRecyclerview(bottomRv, clusterClickAdapter, LinearLayoutManager.VERTICAL);
                clusterClickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        MyItem item = (MyItem) adapter.getData().get(position);
//                        if (infowindow != null) {
//                            mBmapView.removeView(infowindow);
//                        }
                        if (nowMarker != null) {
                            nowMarker.setIcon(bitmapDescriptor);
                        }
                        nowMarker = null;
                        mBaiduMap.hideInfoWindow();
//                        clickItemType = 1;
                        onClusterItemClick(item);
//                    releaseBottomListDialog();
                    }
                });
            }
            clusterClickAdapter.setNewData(items);
            mapBottomDialog.show();
            return false;
        }
        //cluster.getItems()
        arrayList.clear();
        for (MyItem bean : cluster.getItems()) {
            arrayList.add((ShopLocationB.ReturnValueBean) bean.bean);
        }
        showDialog();
        //Toast.makeText(mContext, "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onClusterItemClick(MyItem item) {

        //单个小的
        if (dateType == 2) {//摄像头
            CameraLocBean.ReturnValueBean currentStreamCamera = (CameraLocBean.ReturnValueBean) item.getBean();
            startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
                    .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, currentStreamCamera.getId())
                    .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, currentStreamCamera.getEzOpen())
                    .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, currentStreamCamera.getNumber()));
            return false;
        }
        //跳转店铺详情
        if (((ShopLocationB.ReturnValueBean) item.bean).getId() == nowShopId) {
            MyApp.app.activityTool.toShopActivity(mContext, ((ShopLocationB.ReturnValueBean) item.bean).getId());
        }
        nowShopId = ((ShopLocationB.ReturnValueBean) item.bean).getId();
        //更改图标
        //默认
        try {
            nowMarker.setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtil.combineBitmap(
                    BitmapFactory.decodeStream(getResources().getAssets().open("ic_map_shop_bg.png")),
                    ImageUtil.getRoundedCornerBitmap(ImageUtil.zoomImg(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_map_shop)), 200))));
        } catch (Exception e) {
            Log.e("Marker图标加载失败", e.toString());
            e.printStackTrace();
        }
        FileRetrofit.getInstance().getFileService().getFile_GET(StringTools.getImageForCrmInt(((ShopLocationB.ReturnValueBean) item.bean).getLogoId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    InputStream inputStream = responseBody.byteStream();
                    nowMarker.setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtil.combineBitmap(
                            BitmapFactory.decodeStream(getResources().getAssets().open("ic_map_shop_bg.png")),
                            ImageUtil.getRoundedCornerBitmap(ImageUtil.zoomImg(BitmapFactory.decodeStream(inputStream)), 200))));
                }, throwable -> {
                });
        arrayList.clear();
        arrayList.add((ShopLocationB.ReturnValueBean) item.bean);
        return false;
    }
}

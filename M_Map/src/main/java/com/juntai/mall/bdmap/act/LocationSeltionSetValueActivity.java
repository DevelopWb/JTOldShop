package com.juntai.mall.bdmap.act;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.bdmap.Address;
import com.juntai.mall.bdmap.R;
import com.juntai.mall.bdmap.adapter.PlaceListAdapter;
import com.juntai.mall.bdmap.utils.BaiDuLocationOneUtils;
import com.juntai.mall.bdmap.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择位置
 */
public class LocationSeltionSetValueActivity extends BaseActivity implements BaiduMap.OnMapClickListener ,
        BaseQuickAdapter.OnItemClickListener{
    private String address = "";
    private RecyclerView addressListRV;
    private MapView mapView;
    private Double lat = 0.0, lng = 0.0, selectLat = 0.0, selectLng = 0.0;
    private BaiduMap map = null;
    private BitmapDescriptor locationMarker = null;
    private List<Address> addressList = new ArrayList<>();
    private ProgressBar progressBar = null;
    private PlaceListAdapter adapter = null;
    private int selectedPosition = 0;

    @Override
    public int getLayoutView() {
        return R.layout.activity_location_seltion;
    }

    @Override
    public void initView() {
        setTitleName("地理位置");

        getTitleRightTv().setVisibility(View.VISIBLE);
        getTitleRightTv().setText("发送");
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (selectLat != 0.0 && selectLng != 0.0 && !address.equals("")) {
                    intent.putExtra("lat", selectLat);
                    intent.putExtra("lng", selectLng);
                    intent.putExtra("address", address);
                    setResult(RESULT_OK,intent);
                    finish();
                } else {
                    ToastUtils.toast(mContext, "未选择位置或位置错误");
                }
                finish();
            }
        });

        addressListRV = findViewById(R.id.address_listRy);
        mapView = findViewById(R.id.mMapViewRy);
        map = mapView.getMap();
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        mapView.removeViewAt(1);
        locationMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.red_marker_im);
        addressListRV = findViewById(R.id.address_listRy);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        addressListRV.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progressBarRy);

    }

    @Override
    public void initData() {
        new BaiDuLocationOneUtils(mContext).start(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                start();
            }
        });

    }
    public void start(){
        selectLat = lat;
        selectLng = lng;
        if (lat != 0.0 & lng != 0.0) {
            MapUtil.mapMoveTo(map, new LatLng(lat, lng));
        }
        map.setOnMapClickListener(this);//先搜索再设置监听，有概率收不到回调
        onMapClick(new LatLng(lat, lng));

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 18);    //设置地图中心点以及缩放级别
        map.animateMapStatus(u);
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        GeoCoder mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.toast(mContext, "无结果");
                    progressBar.setVisibility(View.INVISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    return;
                } else {
                    selectLat = latLng.latitude;
                    selectLng = latLng.longitude;
                    List<PoiInfo> searchList = reverseGeoCodeResult.getPoiList();
                    addressList = new ArrayList<>();
                    address = reverseGeoCodeResult.getAddress();
                    addressList.add(new Address(reverseGeoCodeResult.getAddress(), true));
                    if (searchList != null) {
                        for (int i = 0; i < searchList.size(); i++) {
                            addressList.add(new Address(searchList.get(i).getAddress(), false));
                        }
                    }
                    adapter = new PlaceListAdapter(R.layout.item_immap_list, addressList);
                    adapter.setOnItemClickListener(LocationSeltionSetValueActivity.this);
                    adapter.openLoadAnimation();
                    addressListRV.setAdapter(adapter);
                    selectedPosition = 0;
                    progressBar.setVisibility(View.INVISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        };
        mCoder.setOnGetGeoCodeResultListener(listener);
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
        //==============================================
        map.clear();
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions options = new MarkerOptions().position(latLng).icon(locationMarker);
        // 在地图上添加Marker，并显示
        map.addOverlay(options);
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ReverseGeoCodeOption op = new ReverseGeoCodeOption();
        op.location(latLng);
        mCoder.destroy();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        onMapClick(mapPoi.getPosition());
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (selectedPosition == position && selectedPosition != 0) {
            return;
        } else {
            address = addressList.get(position).getAddress();
            addressList.get(selectedPosition).setIschecked(false);
            addressList.get(position).setIschecked(true);
            adapter.notifyItemChanged(position);
            adapter.notifyItemChanged(selectedPosition);
            selectedPosition = position;
        }
    }
    @Override
    protected void onDestroy() {
        locationMarker.recycle();
        locationMarker = null;
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
}

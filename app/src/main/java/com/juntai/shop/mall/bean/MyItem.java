package com.juntai.shop.mall.bean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/6/26 17:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/26 17:28
 */

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.juntai.mall.bdmap.utils.clusterutil.clustering.ClusterItem;

/**
 * 每个Marker点，包含Marker点坐标以及图标
 */
public class MyItem<T> implements ClusterItem {
    private final LatLng mPosition;
    public T bean;
    private Marker marker;
    BitmapDescriptor bitmapDescriptor;

    public MyItem(T b, double lat, double lon,BitmapDescriptor bitmapDescriptor) {
        mPosition = new LatLng(lat, lon);
        this.bitmapDescriptor = bitmapDescriptor;
        this.bean = b;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        //设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间。
        return bitmapDescriptor;
    }

    public void setBitmapDescriptor(BitmapDescriptor bitmapDescriptor) {
        this.bitmapDescriptor = bitmapDescriptor;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setMarker(Marker marker) {

    }

    public T getBean() {
        return bean;
    }

    public Marker getMarker() {
        return marker;
    }
}

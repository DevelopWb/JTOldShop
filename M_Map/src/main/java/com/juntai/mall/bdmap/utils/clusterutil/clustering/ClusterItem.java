/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.juntai.mall.bdmap.utils.clusterutil.clustering;


import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

/**
 * ClusterItem represents a marker on the map.
 */
public interface ClusterItem {

    /**
     * The position of this marker. This must always return the same value.
     */
    LatLng getPosition();

    BitmapDescriptor getBitmapDescriptor();

    int getId();

    void setMarker(Marker marker);


}
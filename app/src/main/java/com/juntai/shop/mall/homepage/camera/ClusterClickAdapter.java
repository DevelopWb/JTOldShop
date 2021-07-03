package com.juntai.shop.mall.homepage.camera;


import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CameraLocBean;
import com.juntai.shop.mall.bean.MyItem;

/**
 * author:wong  地图底部弹窗的适配器
 * Date: 2019/4/19
 * Description:
 */
public class ClusterClickAdapter extends BaseQuickAdapter<MyItem, BaseViewHolder> {
    public ClusterClickAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyItem item) {
        CameraLocBean.ReturnValueBean currentStreamCamera = (CameraLocBean.ReturnValueBean) item.getBean();
        ImageView imageView = helper.getView(R.id.care_item_iv);
        helper.setGone(R.id.add_dev_tv, false);
        helper.setBackgroundRes(R.id.dev_tag_tv, 0);
        helper.setTextColor(R.id.dev_tag_tv, ContextCompat.getColor(mContext, R.color.text_default_color));
        helper.getView(R.id.dev_tag_tv).setPadding(0, 0, 0, 0);
        ImageLoadUtil.loadImageNoCrash(mContext.getApplicationContext(), currentStreamCamera.getEzOpen(), imageView);
        helper.setText(R.id.camera_name_tv, currentStreamCamera.getName())
                .setText(R.id.camera_no_tv, "编号:" + currentStreamCamera.getNumber())
                .setText(R.id.dev_tag_tv, currentStreamCamera.getAddress());
    }
}

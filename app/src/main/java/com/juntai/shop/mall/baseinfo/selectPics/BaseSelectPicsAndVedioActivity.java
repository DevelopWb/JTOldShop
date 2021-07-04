package com.juntai.shop.mall.baseinfo.selectPics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.utils.ActionConfig;
import com.juntai.mall.base.utils.DisplayUtil;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.baseinfo.selectPics.SelectPhotosFragment;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/7/3 14:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/3 14:36
 */
public abstract class BaseSelectPicsAndVedioActivity<P extends BasePresenter> extends BaseAppActivity<P> implements SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed, View.OnClickListener {

    protected SelectPhotosFragment selectPhotosFragment;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;
    protected String videoPath = null;
    private ImageView mItemVideoPic, mDeleteVedio, mItemVideoTag;


    protected abstract void recordVedio();
    protected abstract SelectPhotosFragment getFragment();
    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }

    @Override
    public boolean requestLocation() {
        return false;
    }
    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }
    @Override
    public void initView() {
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView) findViewById(R.id.push_case_delete_vedio_iv);
        mDeleteVedio.setOnClickListener(this);
        mItemVideoPic.setOnClickListener(this);
        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);
        selectPhotosFragment =getFragment().setPicCalculateCallBack(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    /**
     * 动态获取到fragment中图片的宽高后 改变mItemVideoPic的宽高
     *
     * @param width
     */
    @Override
    public void picCalculateed(int width) {
        ViewGroup.LayoutParams params = mItemVideoPic.getLayoutParams();
        params.width = DisplayUtil.dp2px(mContext, width);
        params.height = DisplayUtil.dp2px(mContext, width);
        mItemVideoPic.setLayoutParams(params);
    }
    /**
     * 视频录制成功回调广播play_button
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedio.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_video_pic:
                //选择视频
                recordVedio();
                break;
            case R.id.push_case_delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedio.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
    }
}

package com.juntai.shop.mall.baseinfo.selectPics;


import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.DisplayUtil;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.R;

/**
 * Author:wang_sir
 * Time:2018/7/19 10:52
 * Description:This is ShowSelectedPicsAdapter
 */
public class ShowSelectedPicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean isShowTag = false;
    private int widthAndHeigh = 60;

    public void setShowTag(boolean showTag) {
        isShowTag = showTag;
    }
    private boolean delateable = true;

    public void setWidthAndHeigh(int widthAndHeigh) {
        this.widthAndHeigh = widthAndHeigh;
    }
    public void setDelateable(boolean delateable) {
        this.delateable = delateable;
    }

    public ShowSelectedPicsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if ("-1".equals(item)) {
            ImageLoadUtil.loadCentercropImage(mContext.getApplicationContext(), R.mipmap.add_icons, (ImageView) helper.getView(R.id.select_pic_icon_iv));
            helper.setGone(R.id.delete_pushed_news_iv, false);
        } else {
            ImageLoadUtil.loadImageNoCrash(mContext, item, (ImageView) helper.getView(R.id.select_pic_icon_iv));
            if (delateable) {
                helper.setGone(R.id.delete_pushed_news_iv, true);
            }else{
                helper.setGone(R.id.delete_pushed_news_iv, false);
            }

            if (item.contains(".mp4")) {
                helper.setGone(R.id.item_video_tag, true);
            } else {
                helper.setGone(R.id.item_video_tag, false);
            }
        }
        if (isShowTag){
            helper.setVisible(R.id.item_tag,true);
            switch (helper.getLayoutPosition()){
                case 0:
                    helper.setText(R.id.item_tag,"封面图");
                    break;
                default:
                    helper.setVisible(R.id.item_tag,false);
                    break;
            }
        }else {
            helper.setVisible(R.id.item_tag,false);
        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_pushed_news_iv);
        ImageView imageView = helper.getView(R.id.select_pic_icon_iv);
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.width = DisplayUtil.dp2px(mContext, widthAndHeigh);// 控件的宽强制设成30
        linearParams.height = DisplayUtil.dp2px(mContext, widthAndHeigh);// 控件的高强制设成30
        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
}
package com.juntai.shop.mall.ui.order;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dxngxhl.imageselection.t2.Bean;
import com.dxngxhl.imageselection.t2.ImageSelectionView2;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.ProgressDialog;
import com.juntai.mall.video.record.VideoEvent;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.utils.imageselect.ImageSelect;
import com.juntai.shop.mall.utils.imageselect.ImageSelectLoad;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 订单评价
 * Created by Ma
 * 
 * on 2019/12/9
 */

// TODO: 2021/7/15   发表订单评价  这个效果图还没有出 
public class OrderCommentActivicty extends BaseActivity {
    RadioGroup radioGroup;//1-5
    RatingBar ratingBar;//1-5
    EditText editTextRj,editTextContent;
    ImageSelectionView2 imageSelectionView;
    CheckBox checkBox;
    TextView textView;
    //满意度1-5，匿名评价（0：匿名）（1：不匿名）
    int degreeOfSatisfaction = 5,anonymity = 0;
    int shopid,ordid;
    @Override
    public int getLayoutView() {
        return R.layout.activity_edit_comment;
    }

    @Override
    public void initView() {
        
        setTitleName("发表评论");
        shopid = getIntent().getIntExtra("shopid",-1);
        ordid = getIntent().getIntExtra("ordid",-1);
        if (shopid == -1) finish();
        progressDialog = new ProgressDialog(mContext);
        radioGroup = findViewById(R.id.comment_group);
        ratingBar = findViewById(R.id.comment_ratingbar);
        editTextRj = findViewById(R.id.comment_edittext_rj);
        editTextContent = findViewById(R.id.comment_comment);
        textView = findViewById(R.id.comment_number);
        imageSelectionView = findViewById(R.id.comment_imageselect);
        checkBox = findViewById(R.id.comment_niming);
        editTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(String.format("%s/150",s.length()));
            }
        });
        imageSelectionView
                .setMaxCount(10)
                .setImageChoose(new ImageSelect(this))
                .setImageLoader(new ImageSelectLoad())
                .setNumColumn(4)
                .setImageAddResource(R.mipmap.ic_report_image)
                .setVideoAddRous(R.mipmap.ic_report_video)
                .setScaleType(ImageView.ScaleType.FIT_CENTER)
                .init();
        imageSelectionView.setBackgroundResource(R.color.content_layout);
        imageSelectionView.addImagePath(new Bean(null,true));
        imageSelectionView.setRemoveListener((bean, i) -> {
            if (i == 0 && bean.isVideo()){
                imageSelectionView.addImagePath(new Bean(null,true),0);
            }
        });
        //
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.comment_radio1:
                    degreeOfSatisfaction = 1;
                    break;
                case R.id.comment_radio2:
                    degreeOfSatisfaction = 2;
                    break;
                case R.id.comment_radio3:
                    degreeOfSatisfaction = 3;
                    break;
                case R.id.comment_radio4:
                    degreeOfSatisfaction = 4;
                    break;
                case R.id.comment_radio5:
                    degreeOfSatisfaction = 5;
                    break;
            }
        });
        ratingBar.setRating(5);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            anonymity = isChecked?0:1;//匿名评价（0：匿名）（1：不匿名）
        });
        findViewById(R.id.comment_submit).setOnClickListener(v -> {
            setFormData();
            submit();
        });
    }
    RequestBody requestBody;
    public void setFormData() {
        requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", MyApp.app.getUserToken())
                .addFormDataPart("account", MyApp.app.getAccount())
                .addFormDataPart("purchaserId", String.valueOf(MyApp.app.getUid()))
                .addFormDataPart("merchantId", String.valueOf(shopid))//	商家id
                .addFormDataPart("orderId", String.valueOf(ordid))//	订单id
                .addFormDataPart("degreeOfSatisfaction",String.valueOf(degreeOfSatisfaction))//	满意度
                .addFormDataPart("deliverySpeed",String.valueOf((int) ratingBar.getRating()))//	发货速度
                .addFormDataPart("anonymity",String.valueOf(anonymity))//匿名评价（0：匿名）（1：不匿名）
                .addFormDataPart("perCaPita",editTextRj.getText().toString())//人均-可空
                .addFormDataPart("evaluate",editTextContent.getText().toString());//评论内容-可空

        //前3个为图片pictureFile
        for (Bean path:imageSelectionView.getImagePaths()) {
            if (path.isVideo()){
                //视频
                if (path.getVidePath() == null) continue;
                builder.addFormDataPart("videoFile", "videoFile",
                        RequestBody.create(MediaType.parse("file"),
                                new File(path.getVidePath())));
            }else {
                if (path.getImagePath() == null) continue;
                builder.addFormDataPart("pictureFile", "pictureFile",
                        RequestBody.create(MediaType.parse("file"),
                                new File(path.getImagePath())));
            }
        }
        requestBody = builder.build();
    }
    @Override
    public void initData() {

    }

    boolean isSubmit = false;
    private void submit(){
        isSubmit = true;
        AppNetModule.createrRetrofit()
                .submitBody(AppHttpPath.COMMENT,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext,result.msg);
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                        isSubmit = false;
                    }
                });
    }

    private ProgressDialog progressDialog;
    /**
     * 图片压缩
     * @param paths
     */
    public void imageCompress(List<String> paths) {
        Luban.with(mContext)
                .load(paths)
                .ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        progressDialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        imageSelectionView.addImagePath(new Bean(file.getPath(),false));
                        LogUtil.e("push-图片压缩"+file.getPath());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过程出现问题时调用
                        LogUtil.e("push-图片压缩失败");
                        progressDialog.dismiss();
                    }
                }).launch();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelect.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            LogUtil.d("选择图片" +"  =  " + Matisse.obtainPathResult(data));
            imageCompress(Matisse.obtainPathResult(data));
        }else if (requestCode == ImageSelect.REQUEST_CODE_CHOOSE_VIDEO && resultCode == RESULT_OK) {
            LogUtil.d("选择视频" +"  =  " + Matisse.obtainPathResult(data));
            File file;
            try {
                file = new File(Matisse.obtainPathResult(data).get(0));
                if (file.length() / 1024 < 10000){
                    imageSelectionView.updateImagePath( new Bean(Matisse.obtainPathResult(data).get(0),Matisse.obtainPathResult(data).get(0),true),0);
                }else {
                    ToastUtils.toast(mContext,"请选择小于10m的视频");
                }
            }catch (Exception e){
                LogUtil.e(e.toString());
            }

            LogUtil.d("选择视频  大小" +"  =  " + new File(Matisse.obtainPathResult(data).get(0)).length() / 1024 +"k");
        }
    }
    /**
     * 视频录制
     * @param videoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoEvent(VideoEvent videoEvent){
        imageSelectionView.updateImagePath( new Bean(videoEvent.videoImage,videoEvent.video,true),0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}

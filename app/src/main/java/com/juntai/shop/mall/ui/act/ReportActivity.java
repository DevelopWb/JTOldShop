package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxngxhl.imageselection.t2.Bean;
import com.dxngxhl.imageselection.t2.ImageSelectionView2;
import com.dxngxhl.imageselection.t2.RemoveListener;
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
import com.juntai.shop.mall.bean.ReportTypeBesan;
import com.juntai.shop.mall.ui.goods.ReportDialog;
import com.juntai.shop.mall.ui.goods.shop.ShopActivity;
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
 * 举报商家
 * Created by Ma
 * on 2019/9/9
 */
public class ReportActivity extends BaseActivity implements View.OnClickListener {
    //图片选择
    ImageSelectionView2 imageSelectionView;
    private ProgressDialog progressDialog;
    ReportDialog reportDialog = new ReportDialog();;
    TextView tvSelect;
    EditText editText,editTextPhone;
    ReportTypeBesan.ReturnValueBean valueBean;
    @Override
    public int getLayoutView() {
        return R.layout.activity_report;
    }

    @Override
    public void initView() {
        setTitleName("举报店铺");
        //
        progressDialog = new ProgressDialog(mContext);
        imageSelectionView = findViewById(R.id.report_imageselect);
        tvSelect = findViewById(R.id.report_select);
        editText = findViewById(R.id.report_edittext);
        editTextPhone = findViewById(R.id.report_phone);
        tvSelect.setOnClickListener(this);
        imageSelectionView
                .setMaxCount(4)
                .setImageChoose(new ImageSelect(this))
                .setImageLoader(new ImageSelectLoad())
                .setNumColumn(4)
                .setImageAddResource(R.mipmap.ic_report_image)
                .setVideoAddRous(R.mipmap.ic_report_video)
                .setScaleType(ImageView.ScaleType.FIT_CENTER)
                .init();
        imageSelectionView.setBackgroundResource(R.color.content_layout);
        imageSelectionView.addImagePath(new Bean(null,true));
        imageSelectionView.setRemoveListener(new RemoveListener() {
            @Override
            public void remove(Bean bean, int i) {
                if (i == 0 && bean.isVideo()){
                    imageSelectionView.addImagePath(new Bean(null,true),0);
                }
            }
        });
        //
        reportDialog.setOnSelectListener(bean -> {
            valueBean = bean;
            tvSelect.setText(valueBean.getName());
        });
        findViewById(R.id.report_submit).setOnClickListener(v -> {
            if (valueBean == null){
                ToastUtils.toast(mContext,"请选择举报类型");
            }else if(editText.getText().toString().isEmpty() && (imageSelectionView.getImagePaths().size() == 1 && imageSelectionView.getImagePaths().get(0).getVidePath() == null)){
                ToastUtils.toast(mContext,"请填写内容或上传视频、图片");
            }else {
                setFormData();
                report();
            }
        });
    }

    @Override
    public void initData() {

    }
    boolean isSubmit = false;
    /**
     * 举报
     */
    private void report(){
        isSubmit = true;
        AppNetModule.createrRetrofit()
                .submitBody(AppHttpPath.REPORT_SHOP,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
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

    RequestBody requestBody;
    public void setFormData() {
        requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", MyApp.app.getUserToken())
                .addFormDataPart("account", MyApp.app.getAccount())
                .addFormDataPart("purchaserId", String.valueOf(MyApp.app.getUid()))
                .addFormDataPart("merchantId", String.valueOf(ShopActivity.shopId))
                .addFormDataPart("reportTypeId", String.valueOf(valueBean.getId()))//
                .addFormDataPart("phone", editTextPhone.getText().toString())//
                .addFormDataPart("content", editText.getText().toString());


        for (int i = 0; i < imageSelectionView.getImagePaths().size(); i++) {
            if (i == 0 && imageSelectionView.getImagePaths().get(0).isVideo() && imageSelectionView.getImagePaths().get(0).getVidePath() != null){
                builder.addFormDataPart("videoFile", "videoFile",
                        RequestBody.create(MediaType.parse("file"),
                                new File(imageSelectionView.getImagePaths().get(0).getVidePath())));
            }else if (imageSelectionView.getImagePaths().get(i).getImagePath() != null){
                if (i == 1){
                    builder.addFormDataPart("photoOne", "photoOne",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }else if (i == 2){
                    builder.addFormDataPart("photoTwo", "photoTwo",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }else if (i == 3){
                    builder.addFormDataPart("photoThree", "photoThree",
                            RequestBody.create(MediaType.parse("file"),
                                    new File(imageSelectionView.getImagePaths().get(i).getImagePath())));
                }
            }
        }
        requestBody = builder.build();
    }
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
//            List<Uri> result = Matisse.obtainResult(data);
            LogUtil.d("选择图片" +"  =  " + Matisse.obtainPathResult(data));
            imageCompress(Matisse.obtainPathResult(data));
//            imageSelectionView.addImagePath(Matisse.obtainPathResult(data));
            //不压缩时
            /**
             for (int i = 0; i < imageCaches.size(); i++) {
             imageBeans.get(i).setPath(imageCaches.get(i));
             }
             */
//            imageCompress(result);
        }else if (requestCode == ImageSelect.REQUEST_CODE_CHOOSE_VIDEO && resultCode == RESULT_OK) {
//            List<Uri> result = Matisse.obtainResult(data);
            LogUtil.d("选择视频" +"  =  " + Matisse.obtainPathResult(data));
            File file;
            try {
                //imageSelectionView.addImagePath(new Bean(Matisse.obtainPathResult(data).get(0),Matisse.obtainPathResult(data).get(0),true));
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
//            LogUtil.d("选择视频  大小" +"  =  " + String.format("%.2i",(new File(Matisse.obtainPathResult(data).get(0)).length() / 1024 / 1024)));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_select://选择
                reportDialog.show(getSupportFragmentManager(),"report");
                break;
        }
    }
}

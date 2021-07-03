package com.juntai.mall.video.img;

import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.net.FileRetrofit;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.MenuDialog;
import com.juntai.mall.video.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 图片缩放
 * Created by Ma
 * on 2019/5/22
 */
public class ImageZoomActivity extends BaseActivity {
    MyViewPagerAdapter myViewPagerAdapter;
    ViewPager viewPager;
    List<View> viewList = new ArrayList<>();
    ArrayList<String> paths = new ArrayList<>();
    String nowImagePath = "";
    MenuDialog menuDialog;
    String[] ss = new String[]{"保存图片","取消"};
    @Override
    public int getLayoutView() {
        return R.layout.activity_imagezoom;
    }

    @Override
    public void initView() {
//        getToolbar().setVisibility(View.GONE);
        menuDialog = new MenuDialog();
        viewPager = findViewById(R.id.imagezoom_viewpager);
    }

    @Override
    public void initData() {
        paths.addAll(getIntent().getStringArrayListExtra("paths"));
        int item = getIntent().getIntExtra("item",0);
        if (paths == null){
            return;
        }
        for (String path:paths) {
            PhotoView photoView = new PhotoView(this);
            photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            photoView.enable();
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuDialog.showMenu(getSupportFragmentManager(), Arrays.asList(ss));
                    return false;
                }
            });
            ImageLoadUtil.loadImage(this,path,photoView);
            viewList.add(photoView);
        }
        myViewPagerAdapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(item);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                setTitleName("第 " + (i + 1) + " 张");
                nowImagePath = paths.get(i);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        menuDialog.setOnItemClickListener(new MenuDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == 0){
                    ToastUtils.toast(mContext,"开始下载");
                    menuDialog.dismiss();
                    FileRetrofit.getInstance()
                            .getFileService().getFile_POST(nowImagePath)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseBody>() {
                                @Override
                                public void accept(ResponseBody responseBody) throws Exception {
                                    InputStream inputStream = responseBody.byteStream();
                                    saveImage(inputStream);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    LogUtil.e(throwable.toString());
                                }
                            });
                }
            }
        });
    }
    /**
     * 保存图片
     * @param ins
     */
    public void saveImage(InputStream ins) {
        try {
            LogUtil.d("----->in");
            File file = new File(FileCacheUtils.getImageSavePath()+System.currentTimeMillis()+".jpg");
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            LogUtil.d("----->ok");
            os.close();
            ins.close();
            ToastUtils.toast(mContext,"图片保存地址:"+ file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("----->error-"+e.toString());
        }
    }
}

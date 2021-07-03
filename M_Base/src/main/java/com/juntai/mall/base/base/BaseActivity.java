package com.juntai.mall.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.R;
import com.juntai.mall.base.app.BaseApplication;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.LoadingDialog;
import com.juntai.mall.base.utils.LogUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;


public abstract class BaseActivity extends RxAppCompatActivity implements Toolbar.OnMenuItemClickListener {
    public abstract int getLayoutView();

    public abstract void initView();

    public abstract void initData();

    public Context mContext;
    public Toast toast;
    private Toolbar toolbar;
    private boolean title_menu_first = true;
    private ImageView titleBack;
    private TextView titleName, titleRightTv, statusTopNullView;
    private boolean autoHideKeyboard = true;
    FrameLayout contentLayout;
    public LinearLayout mBaseRootCol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        setContentView(R.layout.activity_base);
        mBaseRootCol = findViewById(R.id.base_layout);
        contentLayout = findViewById(R.id.base_content);
        contentLayout.addView(View.inflate(mContext, getLayoutView(), null));
        toolbar = findViewById(R.id.base_toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        titleBack = findViewById(R.id.title_back);
        titleName = findViewById(R.id.title_name);
        titleRightTv = findViewById(R.id.title_rightTv);
        statusTopNullView = findViewById(R.id.status_top_null_view);
        statusTopNullView.getLayoutParams().height = BaseApplication.statusBarH;
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        initView();
        initData();
        EventBus.getDefault().register(this);
    }
    /**
     * 初始化recyclerview LinearLayoutManager
     */
    public void initRecyclerview(RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter,
                                 @RecyclerView.Orientation int orientation) {
        if (recyclerView == null) {
            return;
        }
        LinearLayoutManager managere = new LinearLayoutManager(this, orientation, false);
        //        baseQuickAdapter.setEmptyView(getAdapterEmptyView("一条信息也没有",0));
        recyclerView.setLayoutManager(managere);
        recyclerView.setAdapter(baseQuickAdapter);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTitleRightTv() {
        titleRightTv.setVisibility(View.VISIBLE);
        return titleRightTv;
    }

    public TextView getStatusTopNullView() {
        return statusTopNullView;
    }

    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    public LinearLayout getmBaseRootCol() {
        return mBaseRootCol;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStringEvent(String str) {
    }

    /**
     * 标题
     *
     * @param title
     */
    public void setTitleName(String title) {
        titleName.setText(title);
//        toolbar.setTitle(title);
    }

    /**
     * 显示返回
     */
    public void hindTitleBack() {

    }

    /**
     * title右侧:图标类
     */
    private void setRightRes() {
        //扩展menu
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //添加监听
        toolbar.setOnMenuItemClickListener(this);
    }

    /**
     * 显示右侧图标
     *
     * @param itemId
     */
    public void showTitleRes(int... itemId) {
        if (title_menu_first) {
            setRightRes();
            title_menu_first = false;
        }
        for (int item : itemId) {
            //显示
            toolbar.getMenu().findItem(item).setVisible(true);//通过id查找,也可以用setIcon()设置图标
            //            toolBar.getMenu().getItem(0).setVisible(true);//通过位置查找
        }
    }

    /**
     * 隐藏title图标
     *
     * @param itemId :图标对应的选项id
     */
    public void hindTitleRes(int... itemId) {
        //        if (titleBack != null)
        //            titleBack.setVisibility(View.GONE);
        for (int item : itemId) {
            //隐藏
            toolbar.getMenu().findItem(item).setVisible(false);
        }
    }

    /**
     * toolbar菜单监听---子activity直接onMenuItemClick()
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    /**
     * 清理webview
     *
     * @param webView
     */
    public void closeWebView(WebView webView) {
        if (webView != null) {
//            ViewGroup parent = webView.getParent();
//            if (parent != null) {
//                parent.re(webView);
//            }
            webView.removeAllViews();
            webView.destroy();
        }
    }
//    @Override
//    public void showLoadingFileDialog() {
//        showFileDialog();
//    }
//
//    @Override
//    public void hideLoadingFileDialog() {
//        hideFileDialog();
//    }

//    @Override
//    public void onProgress(long totalSize, long downSize) {
//        if (dialog != null) {
//            dialog.setProgress((int) (downSize * 100 / totalSize));
//        }
//    }

    /**
     * 点击空白隐藏键盘
     *
     * @param event
     * @param view
     * @param activity
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击空白自动隐藏键盘- - - 默认
     *
     * @param autoHideKeyboard:false - 不自动隐藏
     */
    public void setAutoHideKeyboard(boolean autoHideKeyboard) {
        this.autoHideKeyboard = autoHideKeyboard;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                if (autoHideKeyboard) {
                    hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                }
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }



    /**
     * 压缩图片
     * @param path  图片路径
     * @param saveDirName  保存本地图片的目录
     * @param onImageCompressedPath
     * @param saveFileName  保存文件的名称
     */
    public void  compressImage(String path, String saveDirName,
                               String saveFileName,OnImageCompressedPath onImageCompressedPath) {
        //        showLoadingDialog(mContext);
        Luban.with(mContext).load(path).ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath(saveDirName))
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                return TextUtils.isEmpty(saveFileName)||saveFileName==null?System.currentTimeMillis()+".jpg":
                        saveFileName+".jpg";
            }
        })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI

                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        if (onImageCompressedPath != null) {
                            onImageCompressedPath.compressedImagePath(file);
                        }
                        stopLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("push-图片压缩失败");
                        stopLoadingDialog();
                    }
                }).launch();
    }
    /**
     * 停止加载动画
     */
    public void stopLoadingDialog() {
        LoadingDialog.getInstance().dismissProgress();
    }

    /**
     * 图片压缩成功回调
     */
    public interface OnImageCompressedPath {
        void  compressedImagePath(File file);
    }

    /**
     * 展示加载动画
     */
    public void showLoadingDialog(Context context) {
        LoadingDialog.getInstance().showProgress(context);
    }
}

package com.juntai.mall.base.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.R;
import com.juntai.mall.base.utils.ActivityManagerTool;
import com.juntai.mall.base.utils.DisplayUtil;
import com.juntai.mall.base.utils.DividerItemDecoration;
import com.juntai.mall.base.utils.LoadingDialog;
import com.juntai.mall.base.utils.ScreenUtils;
import com.juntai.mall.base.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
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
    protected CoordinatorLayout mBaseRootCol;
    private boolean title_menu_first = true;
    private TextView mBackTv;
    public ImmersionBar mImmersionBar;
    private TextView titleName, titleRightTv;
    private boolean autoHideKeyboard = true;
    public FrameLayout frameLayout;
    public static int ActivityResult = 1001;//activity?????????
    public static int BASE_REQUEST_RESULT = 10086;//???????????????
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//??????
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ????????????
        mContext = this;
        mImmersionBar = ImmersionBar.with(this);
        initWidows();
        setContentView(R.layout.activity_base);
        frameLayout = findViewById(R.id.base_content);
        if (0 != getLayoutView()) {
            frameLayout.addView(View.inflate(this, getLayoutView(), null));
        }
        toolbar = findViewById(R.id.base_toolbar);
        mBaseRootCol = findViewById(R.id.base_col);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBackTv = findViewById(R.id.back_tv);
        titleName = findViewById(R.id.title_name);
        titleRightTv = findViewById(R.id.title_rightTv);
        initToolbarAndStatusBar(true);
        initLeftBackTv(true,R.drawable.app_back);
        initView();
        initData();
        adapterScreen();
        ActivityManagerTool.getInstance().addActivity(this);
    }

    /**
     * ??????toolbar??????
     * @param resId
     */
    public void setToolbarBg(int resId){
        titleName.setTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundResource(resId);
        AppBarLayout.LayoutParams params = new AppBarLayout.LayoutParams(toolbar.getLayoutParams());
        params.width = AppBarLayout.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.dp2px(mContext,60);
        params.gravity = Gravity.CENTER_VERTICAL;
        toolbar.setLayoutParams(params);
        toolbar.setPadding(0,DisplayUtil.dp2px(mContext,20),DisplayUtil.dp2px(mContext,20),0);
        initLeftBackTv(true,R.drawable.app_back_white);
    }
    /**
     * ????????? ????????????????????? ?????????toolbar????????????
     */
    protected void initToolbarAndStatusBar(boolean visible) {
        if (visible) {
            getToolbar().setVisibility(View.VISIBLE);
            getToolbar().setNavigationIcon(null);
            getToolbar().setBackgroundResource(R.drawable.bg_white_only_bottom_gray_shape_1px);
            //???????????????
            mBaseRootCol.setFitsSystemWindows(true);
            mImmersionBar.statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .init();
        }else{
            getToolbar().setVisibility(View.GONE);
            //???????????????
            mBaseRootCol.setFitsSystemWindows(false);
            mImmersionBar.reset().transparentStatusBar().init();
        }

    }
    /**
     * ??????????????? ?????????????????????????????????
     */
    protected void adapterScreen() {
        ScreenUtils screenUtils = ScreenUtils.getInstance(this);
        if (screenUtils.isPortrait()) {
            screenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            screenUtils.adaptScreen4HorizontalSlide(this, 360);
        }

    }
    /**
     * ????????????????????? ???????????????
     *
     * @param isShow ????????????
     */
    protected void initLeftBackTv(boolean isShow,int resId) {
        if (isShow) {
            mBackTv.setVisibility(View.VISIBLE);
            Drawable drawable = mContext.getResources().getDrawable(resId);
            // ?????????????????????,??????????????????.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBackTv.setCompoundDrawables(drawable, null, null, null);
//            mBackTv.setText("??????");
            mBackTv.setCompoundDrawablePadding(-DisplayUtil.dp2px(this, 3));
            mBackTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            mBackTv.setVisibility(View.GONE);
        }

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTitleRightTv() {
        titleRightTv.setVisibility(View.VISIBLE);
        return titleRightTv;
    }

    /**
     * ??????????????????
     */
    public void showLoadingDialog(Context context) {
        LoadingDialog.getInstance().showProgress(context);
    }

    /**
     * ???????????????
     *
     * @return
     */
    public TextView getTitleLeftTv() {
        mBackTv.setVisibility(View.VISIBLE);
        return mBackTv;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ??????
     *
     * @param title
     */
    public void setTitleName(String title) {
        titleName.setText(title);
        titleName.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    /**
     * title??????:?????????
     */
    private void setRightRes() {
        //??????menu
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //????????????
        toolbar.setOnMenuItemClickListener(this);
    }

    /**
     * ??????????????????
     *
     * @param itemId
     */
    public void showTitleRes(int... itemId) {
        if (title_menu_first) {
            setRightRes();
            title_menu_first = false;
        }
        for (int item : itemId) {
            //??????
            toolbar.getMenu().findItem(item).setVisible(true);//??????id??????,????????????setIcon()????????????
            //            toolBar.getMenu().getItem(0).setVisible(true);//??????????????????
        }
    }

    /**
     * ??????title??????
     *
     * @param itemId :?????????????????????id
     */
    public void hindTitleRes(int... itemId) {
        //        if (titleBack != null)
        //            titleBack.setVisibility(View.GONE);
        for (int item : itemId) {
            //??????
            toolbar.getMenu().findItem(item).setVisible(false);
        }
    }

    /**
     * toolbar????????????---???activity??????onMenuItemClick()
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    /**
     * ??????webview
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
     * ????????????????????????
     *
     * @param event
     * @param view
     * @param activity
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof TextView) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // ???????????????????????????????????????????????????????????????????????????????????????
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // ????????????
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
     * ???????????????  view ????????????????????????view ???????????????edittext
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * ??????????????????????????????- - - ??????
     *
     * @param autoHideKeyboard:false - ???????????????
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
                    hideKeyboard(ev, view, BaseActivity.this);//??????????????????????????????????????????
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        ToastUtils.info(mContext,"??????");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ActivityManagerTool.getInstance().removeActivity(this);
        EventBus.getDefault().unregister(this);//??????
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //??????????????????????????????????????????????????????????????????????????????bar???????????????????????????app????????????????????????????????????????????????????????????bar???????????????
            mImmersionBar = null;
        }
        this.mContext = null;
        stopLoadingDialog();
    }

    /**
     * ??????????????????
     */
    public void stopLoadingDialog() {
        LoadingDialog.getInstance().dismissProgress();
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public List<String> getTestData() {
        return Arrays.asList(new String[]{ "test2", "test3", "test4", "test5", "???????????????????????????????????????????????????????????????XXXXXXXXXXXXX", "???????????????????????????????????????????????????????????????"});
    }

    /**
     * ??????TextView??????
     *
     * @param textView
     * @return
     */
    public String getTextViewValue(TextView textView) {
        return textView.getText().toString().trim();
    }



    /**
     * ??????????????? ?????????????????????????????????
     */
    protected void initWidows() {
        //?????????????????? 360??????????????????px/2
        ScreenUtils screenUtils = ScreenUtils.getInstance(getApplicationContext());
        if (screenUtils.isPortrait()) {
            screenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            screenUtils.adaptScreen4HorizontalSlide(this, 360);
        }

    }

    /**
     * ?????????recyclerview LinearLayoutManager
     */
    public void initRecyclerview(RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter, @RecyclerView.Orientation int orientation) {
        LinearLayoutManager managere = new LinearLayoutManager(this, orientation, false);
        //        baseQuickAdapter.setEmptyView(getAdapterEmptyView("?????????????????????",0));
        recyclerView.setLayoutManager(managere);
        recyclerView.setAdapter(baseQuickAdapter);
    }
    /**
     * ?????????recyclerview LinearLayoutManager
     */
    public void initRecyclerviewNoScroll(RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter,
                                         @RecyclerView.Orientation int orientation) {
        LinearLayoutManager managere = new LinearLayoutManager(this, orientation, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        //        baseQuickAdapter.setEmptyView(getAdapterEmptyView("?????????????????????",0));
        recyclerView.setLayoutManager(managere);
        recyclerView.setAdapter(baseQuickAdapter);
    }
    /**
     * ???????????????
     *
     * @param recyclerView
     * @param haveTopLine
     * @param isHorizontalDivider ???????????????
     * @param haveEndLine         ????????????item???????????????
     */
    public void addDivider(boolean isHorizontalDivider, RecyclerView recyclerView, boolean haveTopLine, boolean haveEndLine) {
        DividerItemDecoration dividerItemDecoration;
        if (isHorizontalDivider) {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_hor_line_sp);
        } else {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
        }
        if (haveTopLine) {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.ALL);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.START);
            }
        } else {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.END);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.INSIDE);

            }
        }
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    /**
     * ??????alertdialog?????????
     * ????????????????????????????????? ??????????????????????????????
     * ?????????dialog  show()?????????????????? ???????????????
     *
     * @param dialog
     * @param width  -1??????????????????  0 ?????? wrap_content  ???????????????????????????
     * @param height
     */
    public void setAlertDialogHeightWidth(AlertDialog dialog, int width, int height) {
        // ??????dialog?????????
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        if (-1 == width) {
            params.width = getScreenWidth();
        } else if (0 == width) {
            params.width = params.width;
        } else {
            params.width = width;
        }
        if (-1 == height) {
            params.height = getScreenHeight();
        } else if (0 == height) {
            params.height = params.height;
        } else {
            params.height = height;
        }
        dialog.getWindow().setAttributes(params);
    }
    /**
     * ??????????????????(px)
     *
     * @param
     * @return
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        return width;
    }

    /**
     * ??????????????????(px)
     *
     * @param
     * @return
     */
    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        return height;
    }
    /**
     * ???????????????
     *
     * @param text
     * @return
     */
    public View getAdapterEmptyView(String text, int imageId) {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        TextView noticeTv = view.findViewById(R.id.none_tv);
        noticeTv.setText(text);
        ImageView imageView = view.findViewById(R.id.none_image);
        if (-1==imageId) {
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setImageResource(imageId);
        }
        return view;
    }

    /**
     * ??????imageview????????????
     *
     * @param imageView
     */
    public void recycleImageView(ImageView imageView) {
        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bm.recycle();
        bm = null;
    }
    //????????????
    public  void singleLogin(){}
    @Subscribe(threadMode = ThreadMode.MAIN) //???ui????????????
    public void receiveBaseStringMsg(String msg) {
        switch (msg) {
//            case EventManager.SINGLE_LOGIN:
//                //????????????
//                singleLogin();
//                break;
            default:
                break;
        }
    }
    /**
     * ??????view???margin??????
     */
    public void setMargin(ViewGroup.MarginLayoutParams params,View view, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(this, left);
        top = DisplayUtil.dp2px(this, top);
        right = DisplayUtil.dp2px(this, right);
        bottom = DisplayUtil.dp2px(this, bottom);
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);
    }
    /**
     * ???????????????  view ????????????????????????view ???????????????edittext
     */
    public void hideKeyboardFromView(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * view????????????
     */
    public  void getViewFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        hideKeyboardFromView(view);
    }
    /**
     * ???????????????
     *
     * @param view
     */
    public void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

    }


    /**
     * ????????????????????????
     */
    public interface OnImageCompressedPath {
        void  compressedImagePath(File file);
    }



}

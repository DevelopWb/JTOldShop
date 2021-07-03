package com.juntai.shop.mall.ui.my;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.UserB;
import com.juntai.shop.mall.ui.act.ImageCropActivity;
import com.juntai.shop.mall.ui.dialog.PromptDialog;
import com.juntai.shop.mall.utils.AppCode;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.EmojiExcludeFilter;
import com.juntai.shop.mall.utils.imageselect.GlideEngine4;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人信息
 * Created by Ma
 * on 2019/8/24
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    ImageView headImageView;
    TextView tvAccount,tvDate,tvPhone,tvWx,tvQQ;
    EditText editNick;
    TimePickerView pvTime;
    PromptDialog promptDialog = new PromptDialog();
    String nicknameNet = "",nickname,birth;
    //是否修改过资料
    boolean isEdited = false;
    @Override
    public int getLayoutView() {
        return R.layout.activity_myinfo;
    }

    @Override
    public void initView() {
        setTitleName("个人信息");
        getContentLayout().setBackground(null);
        headImageView = findViewById(R.id.myinfo_head_image);
        editNick = findViewById(R.id.myinfo_nick);
        tvAccount = findViewById(R.id.myinfo_account);
        tvDate = findViewById(R.id.myinfo_date);
        tvPhone = findViewById(R.id.myinfo_phone);
        tvWx = findViewById(R.id.myinfo_wx);
        tvQQ = findViewById(R.id.myinfo_qq);
        headImageView.setOnClickListener(this);
        findViewById(R.id.myinfo_nick_edit).setOnClickListener(this);
        findViewById(R.id.myinfo_birth).setOnClickListener(this);
        //去掉表情
        editNick.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        //
        getmBaseRootCol().setOnClickListener(v -> {
            //判断当前和网络是否一致。不一致提示更改昵称
            nickname = editNick.getText().toString();
            if (editNick.isEnabled() && (!nicknameNet.equals(nickname))){
                promptDialog.show(getSupportFragmentManager(),"prompt");
            }
            editNick.setEnabled(false);
        });
        promptDialog.setMessage("是否修改为当前昵称","否","修改");
        promptDialog.setOkClickListener(new PromptDialog.OnOkClickListener() {
            @Override
            public void onClick() {
                birth = null;
                edit();
            }

            @Override
            public void cancle() {
                editNick.setText(nicknameNet);
            }
        });
        initTimePicker();
    }

    @Override
    public void initData() {
        AppNetModule.createrRetrofit()
                .userInfo(App.app.getAccount(),App.app.getUserToken(),App.app.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserB>() {
                    @Override
                    public void onSuccess(UserB result) {
                        nicknameNet = result.getReturnValue().getNickName();
                        editNick.setText(result.getReturnValue().getNickName());
                        tvAccount.setText(result.getReturnValue().getAccount());
                        tvDate.setText(result.getReturnValue().getBirthday());
                        tvPhone.setText(result.getReturnValue().getPhone());
                        tvWx.setText(result.getReturnValue().getWeChatName());
                        tvQQ.setText(result.getReturnValue().getQqName());
                        tvWx.setText(result.getReturnValue().getWeChatName());
                        tvQQ.setText(result.getReturnValue().getQqName());
                        ImageLoadUtil.loadCircularImage(
                                mContext,
                                AppHttpPath.IMAGE + result.getReturnValue().getHeadUrl(),
                                R.mipmap.ic_launcher,
                                R.mipmap.ic_launcher,
                                headImageView);
                        tvWx.setText(result.getReturnValue().getWeChatName());
                        tvQQ.setText(result.getReturnValue().getQqName());
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myinfo_head_image:
                //修改头像
                new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Matisse.from(MyInfoActivity.this)
                                            .choose(MimeType.ofImage())
                                            .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                            .countable(true)
                                            .maxSelectable(1)                     // 最多选择一张
                                            .capture(true)
                                            .captureStrategy(new CaptureStrategy(true, AppUtils.getFileprovider()))
                                            //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                            .thumbnailScale(0.85f)
                                            .imageEngine(new GlideEngine4())
                                            .forResult(AppCode.CHOOSE_IMAGE);
                                    //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                                } else {
                                    Toast.makeText(mContext, "请给与相应权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.myinfo_nick_edit:
                editNick.setEnabled(true);
                editNick.requestFocus();
                editNick.requestFocusFromTouch();
                InputMethodManager inputManager =
                        (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editNick, 0);
                break;
            case R.id.myinfo_birth:
                pvTime.show();
                break;
        }
    }
    public void edit(){
        AppNetModule.createrRetrofit()
                .userInfoEdit(App.app.getAccount(),App.app.getUserToken(),App.app.getUid(),nickname,birth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        isEdited = true;
                        initData();
                        ToastUtils.success(mContext,"修改成功");
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }

    /**
     * 更改头像
     */
    public void postHead(File file){
        RequestBody request;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", App.app.getUserToken())
                .addFormDataPart("id", String.valueOf(App.app.getUid()))
                .addFormDataPart("account", App.app.getAccount());
        builder.addFormDataPart("file", "file",
                RequestBody.create(MediaType.parse("file"), file));
        request = builder.build();
        AppNetModule.createrRetrofit()
                .submitBody(AppHttpPath.USER_HEAD_EDIT,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        isEdited = true;
                        initData();
                        ToastUtils.success(mContext,baseResult.msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }
    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
//        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 0,0,0,0);

        Calendar endDate = Calendar.getInstance();
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText();*/
                birth = getTime(date);
                tvDate.setText(birth);
                nickname = null;
                edit();
            }
        }).setLayoutRes(R.layout.dialog_birth_date, v -> {
            //处理view--submit等
            TextView textView = v.findViewById(R.id.selector_title);
            textView.setText("选择生日");
            v.findViewById(R.id.selector_close).setOnClickListener(v1 -> {
                //
                pvTime.dismiss();
            });
            v.findViewById(R.id.birth_date_ok).setOnClickListener(v1 -> {
                pvTime.returnData();
                pvTime.dismiss();
            });
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(16)
                .setDate(endDate)
                .setRangDate(startDate, endDate)
                .setOutSideCancelable(false)
                .build();

        //pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCode.CHOOSE_IMAGE && resultCode == RESULT_OK) {
//            List<Uri> result = Matisse.obtainResult(data);
            String path = Matisse.obtainPathResult(data).get(0);
            Uri uri = Matisse.obtainResult(data).get(0);

            Intent intent = new Intent(mContext, ImageCropActivity.class);
            intent.putExtra("path",path);
            startActivityForResult(intent,AppCode.CROP_IMAGE);
        }else if (requestCode == AppCode.CROP_IMAGE && resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            postHead(new File(path));
        }
    }

    @Override
    protected void onDestroy() {
        if (isEdited)
            setResult(RESULT_OK);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isEdited)
            setResult(RESULT_OK);
        super.onBackPressed();
    }
}

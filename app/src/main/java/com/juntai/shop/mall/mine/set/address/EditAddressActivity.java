package com.juntai.shop.mall.mine.set.address;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressInfoBean;
import com.juntai.shop.mall.bean.PlaceBean;
import com.juntai.shop.mall.mine.set.address.selector.AddressSelectorDialog;
import com.juntai.shop.mall.ui.dialog.PromptDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/9
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener {
    ImageView imageView;
    TextInputEditText editTextName,editTextPhone,editeTextPlace;
    TextView addressTv,tvTag;
    AddressSelectorDialog selectorDialog = new AddressSelectorDialog();
    AddressTagDialog addressTagDialog = new AddressTagDialog();
    //家，公司，学校，默认地址
    CheckBox checkBox1,checkBox2,checkBox3;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2;
    int sex = 0;//性别（0男，1女）
    int def = 0;//默认地址（0：不是）（1：是）
    StringBuffer stringBuffer = new StringBuffer();
    int id = -1;
    private PlaceBean.ReturnValueBean beanProvince,beanCity,beanCounty,beanTown;
    private AddressInfoBean.ReturnValueBean valueBean;
    PromptDialog promptDialog = new PromptDialog();
    boolean isDel = false;//是否删除
    private TextView tvDelete;
    private SwitchCompat mDefaultAddrSc;

    @Override
    public int getLayoutView() {
        return R.layout.activity_address;
    }

    @Override
    public void initView() {
        getToolbar().setNavigationOnClickListener(v -> quit());
        id = getIntent().getIntExtra("id",-1);

        imageView = findViewById(R.id.address_add_save_contact);
        editTextName = findViewById(R.id.address_add_name);
        editTextPhone = findViewById(R.id.address_add_phone);
        editeTextPlace = findViewById(R.id.address_add_place);
        addressTv = findViewById(R.id.address_add_select);
        radioGroup = findViewById(R.id.address_add_group);
        radioButton1 = findViewById(R.id.address_add_radio1);
        radioButton2 = findViewById(R.id.address_add_radio2);
        tvTag = findViewById(R.id.address_add_tag);
        checkBox1 = findViewById(R.id.address_add_tag1);
        checkBox2 = findViewById(R.id.address_add_tag2);
        checkBox3 = findViewById(R.id.address_add_tag3);
        mDefaultAddrSc = findViewById(R.id.default_addr_sc);
        tvDelete = getTitleRightTv();
        tvDelete.setText("删除");
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog.setMessage("是否删除地址","取消","删除");
                isDel = true;
                promptDialog.show(getSupportFragmentManager(),"delete");
            }
        });
        imageView.setOnClickListener(this);
        addressTv.setOnClickListener(this);
        findViewById(R.id.address_add_taglayout).setOnClickListener(this);
        findViewById(R.id.address_add_save).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.address_add_radio1){
                sex = 0;
            }else if (checkedId == R.id.address_add_radio2){
                sex = 1;
            }
        });
        mDefaultAddrSc.setOnCheckedChangeListener((buttonView, isChecked) -> def = isChecked ? 1 : 0);
        selectorDialog.setOnSelectorListener((province, city, area, street) -> {
            beanProvince = province;
            beanCity = city;
            beanCounty = area;
            beanTown = street;
            stringBuffer = new StringBuffer();
            stringBuffer.append(province.getName())
                    .append(" ")
                    .append(city.getName())
                    .append(" ")
                    .append(area.getName());
            if (beanTown != null){
                stringBuffer.append(" ")
                        .append(street.getName());
            }
            addressTv.setText(stringBuffer.toString());
        });

        promptDialog.setOkClickListener(new PromptDialog.OnOkClickListener() {
            @Override
            public void onClick() {
                if (isDel){
                    toDel();
                }else{
                    toSave();
                }
                isDel = false;
            }

            @Override
            public void cancle() {
                if (!isDel){//不是删除时为退出提示保存
                    finish();
                }
                isDel = false;
            }
        });
        addressTagDialog.setView();

        if(id == -1){
            setTitleName("新建收货地址");
            tvDelete.setVisibility(View.GONE);
        }else {
            setTitleName("编辑收货地址");
            getAddress();
            tvDelete.setVisibility(View.VISIBLE);
        }
        addressTagDialog.setOnSelectListener(tag -> {
            tvTag.setText(tag);
            initAddrCheckBoxTag(tag);

        });
    }

    /**
     * 地址标签 控件状态
     */
    private void initAddrCheckBoxTag(String tag) {
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        switch (tag){
            case "家":
                checkBox1.setChecked(true);
                break;
            case "公司":
                checkBox2.setChecked(true);
                break;
            case "学校":
                checkBox3.setChecked(true);
                break;
        }
    }

    @Override
    public void initData() {

    }
    private void toSave(){
        if (id != -1 && !isEdited()){
            ToastUtils.toast(mContext,"您还没有编辑内容");
            return;
        }
        if (editTextName.getText().toString().isEmpty()){
            ToastUtils.toast(mContext,"请输入收货人姓名");
        }else if (editTextPhone.getText().toString().isEmpty()){
            ToastUtils.toast(mContext,"请输入收货人联系方式");
        }else if (addressTv.getText().toString().isEmpty()){
            ToastUtils.toast(mContext,"请选择地址");
        }else if (editeTextPlace.getText().toString().isEmpty()){
            ToastUtils.toast(mContext,"请填写详细地址");
        }else {
            setFormData();
            addAddress();
        }
    }
    boolean isSubmit;
    /**
     * 上传
     */
    private void addAddress(){
        isSubmit = true;
        AppNetModule.createrRetrofit()
                .submitBody(id == -1?AppHttpPath.ADDRESS_ADD:AppHttpPath.ADDRESS_UPDATE,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext,result.msg);
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                        isSubmit = false;
                    }
                });
    }
    /**
     * 获取地址信息
     */
    private void getAddress(){
        AppNetModule.createrRetrofit()
                .addressInfo(MyApp.app.getAccount(), MyApp.app.getUserToken(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AddressInfoBean>(null) {
                    @Override
                    public void onSuccess(AddressInfoBean result) {
                        valueBean = result.getReturnValue();
                        editTextName.setText(valueBean.getName());
                        editTextPhone.setText(valueBean.getPhone());
                        editeTextPlace.setText(valueBean.getDetailedAddress());
                        addressTv.setText(String.format("%s %s %s %s",
                                valueBean.getProvinceName(),
                                valueBean.getCityName(),
                                valueBean.getAreaName(),
                                valueBean.getStreetName()));
                        tvTag.setText(valueBean.getLabel());
                       initAddrCheckBoxTag(valueBean.getLabel());
                        //性别（0男，1女）
                        sex = valueBean.getSex();
                        if (sex == 0) {
                            radioButton1.setChecked(true);
                        } else {
                            radioButton2.setChecked(true);
                        }
                        //默认地址（0：不是）（1：是）
                        def = valueBean.getDefaultAddress();
                        mDefaultAddrSc.setChecked(def == 1);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    /**
     * 删除地址
     */
    private void toDel(){
        AppNetModule.createrRetrofit()
                .addressDel(MyApp.app.getAccount(), MyApp.app.getUserToken(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.toast(mContext,"删除成功");
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
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
                .addFormDataPart("name", editTextName.getText().toString())//
                .addFormDataPart("phone", editTextPhone.getText().toString())//
                .addFormDataPart("sex", String.valueOf(sex))//
                .addFormDataPart("defaultAddress", String.valueOf(def))//是否为默认地址
                .addFormDataPart("detailedAddress", editeTextPlace.getText().toString());//详细地址

        if (beanProvince != null){
            builder.addFormDataPart("provinceCode", String.valueOf(beanProvince.getCityNum()))//省代码
                   .addFormDataPart("cityCode", String.valueOf(beanCity.getCityNum()))//市代码
                   .addFormDataPart("areaCode", String.valueOf(beanCounty.getCityNum()));//县区代码
        }
        if (id != -1){
            //地址id
            builder.addFormDataPart("id",String.valueOf(id));
            builder.addFormDataPart("purchaserId",String.valueOf(MyApp.app.getUid()));
        }
        if (beanTown != null){
            //街道代码
            builder.addFormDataPart("streetCode",String.valueOf(beanTown.getCityNum()));
        }
        if (!tvTag.getText().toString().isEmpty()){
            //标签
            builder.addFormDataPart("label",tvTag.getText().toString());
        }
        requestBody = builder.build();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_add_save_contact://从通讯录选择收货人
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent,1000);
                break;
            case R.id.address_add_select://选择收货地址
                selectorDialog.show(getSupportFragmentManager(),"selector");
                break;
            case R.id.address_add_taglayout://标签
                addressTagDialog.show(getSupportFragmentManager(),"selector_tag");
                break;
            case R.id.address_add_save://添加收货地址
                toSave();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        quit();
    }
    private void quit(){
        if (valueBean != null){
            if (isEdited()){
                //valueBean.getLabel()可能为空
                promptDialog.setMessage("是否保存本次编辑结果","不保存","保存");
                promptDialog.show(getSupportFragmentManager(),"dialog");
                return;
            }
        }else if (id == -1){
            if (!editTextName.getText().toString().isEmpty()
                    || !editTextPhone.getText().toString().isEmpty()
                    || !editeTextPlace.getText().toString().isEmpty()
                    || !addressTv.getText().toString().isEmpty()
                    || !tvTag.getText().toString().isEmpty()){
                promptDialog.setMessage("是否保存本次编辑结果","不保存","保存");
                promptDialog.show(getSupportFragmentManager(),"dialog");
                return;
            }
        }
        finish();
    }

    /**
     * 编辑状态下是否修改过
     * @return
     */
    public boolean isEdited(){
        return sex != valueBean.getSex()
                || def != valueBean.getDefaultAddress()
                || !valueBean.getName().equals(editTextName.getText().toString())
                || !valueBean.getPhone().equals(editTextPhone.getText().toString())
                || !valueBean.getDetailedAddress().equals(editeTextPlace.getText().toString())
                || (valueBean.getLabel() == null && !tvTag.getText().toString().isEmpty())
                || (valueBean.getLabel() != null && tvTag.getText().toString().isEmpty())
                || ((valueBean.getLabel() != null && !tvTag.getText().toString().isEmpty()) && !(tvTag.getText().toString().equals(valueBean.getLabel())))
                || beanProvince != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode==RESULT_OK){
                if (data!=null){
                    Uri uri=data.getData();
                    String[] contact=getPhoneContacts(uri);
                    if (contact!=null){
                        String name = contact[0];//姓名
                        String number = contact[1];//手机号
                        String number2 = formatPhoneNum(contact[1]);//手机号

                        editTextName.setText(name);
                        editTextPhone.setText(number2);
                    }
                }
            }
        }
    }
    /**
     * 读取联系人信息
     * @param uri
     */
    private String[] getPhoneContacts(Uri uri){
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null&&cursor.moveToFirst()) {
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            contact[1]=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
            Log.e("fffffff","contacts"+contact[0]);
            Log.e("ffffffff","contactsUsername="+contact[1]);
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    /**
     * 去掉手机号内除数字外的所有字符
     *
     * @param phoneNum 手机号
     * @return
     */
    private String formatPhoneNum(String phoneNum) {
        String regex = "(\\+86)|[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.replaceAll("");
    }

}

package com.juntai.shop.mall.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;

/**
 * 商品数量
 */
public class CountView extends LinearLayout {
    private OnCountChangeListener onCountChangeListener;
    boolean isNumEdit = true;
    LinearLayout linearLayout;
    ImageView imageView;
    public CountView(Context context) {
        super(context);
        initView();
    }

    public CountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CountView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    TextView numberText;
    ImageView subtract,add;
    //maxNumber=-1表示不限制数量
    int number = 0,maxNumber = 0;

    /**
     * 限制添加的最大数量
     * @param maxNum
     */
    public void setMaxNumber(int maxNum){
        maxNumber = maxNum;
    }
    public void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.countview,this);
        linearLayout = findViewById(R.id.count_num_layout);
        imageView = findViewById(R.id.count_cart_add);
        subtract = findViewById(R.id.count_subtract);
        numberText = findViewById(R.id.count_num);
        add = findViewById(R.id.count_add);

        subtract.setOnClickListener(v -> {
            if (!isNumEdit) return;
            if (number > 0){
                number --;
                if (number == 0){
                    numberText.setText("");
                    linearLayout.setVisibility(GONE);
                    imageView.setVisibility(VISIBLE);
                }
                numberText.setText(number == 0 ? "" : ""+number);
                if (onCountChangeListener != null){
                    onCountChangeListener.countChange(number,0);
                }
            }
        });
        imageView.setOnClickListener(v -> {
            if (!isNumEdit) return;
            if (number < maxNumber && maxNumber > 0){
                linearLayout.setVisibility(VISIBLE);
                imageView.setVisibility(GONE);
                number ++;
                numberText.setText(""+number);
                if (onCountChangeListener != null){
                    onCountChangeListener.countChange(number,1);
                }
            }else if(maxNumber <= 0){
                ToastUtils.toast(MyApp.app,"库存不足");
            }else {
                ToastUtils.toast(MyApp.app,"当前库存不足");
            }
        });
        add.setOnClickListener(v -> {
            if (!isNumEdit) return;
            if (number < maxNumber && maxNumber > 0){
                linearLayout.setVisibility(VISIBLE);
                imageView.setVisibility(GONE);
                number ++;
                numberText.setText(""+number);
                if (onCountChangeListener != null){
                    onCountChangeListener.countChange(number,1);
                }
            }else if(maxNumber <= 0){
                ToastUtils.toast(MyApp.app,"库存不足");
            }else {
                ToastUtils.toast(MyApp.app,"当前库存不足");
            }
        });
    }

    public void setNumEdit(boolean edit) {
        isNumEdit = edit;
    }

    public void setNumber(int number) {
        this.number = number;
        if (number != 0){
            numberText.setText(""+number);
            linearLayout.setVisibility(VISIBLE);
            imageView.setVisibility(GONE);
        }else {
            linearLayout.setVisibility(GONE);
            imageView.setVisibility(VISIBLE);
        }
    }

    /**
     * 获取当前数量
     * @return
     */
    public int getNumber() {
        return number;
    }


    public void setOnCountChangeListener(OnCountChangeListener listener){
        onCountChangeListener = listener;
    }
    /**
     * 数量变化
     */
    public interface OnCountChangeListener{
        /**
         * 数量变化
         * @param count：数量
         * @param type：0：减，1：加
         */
        void countChange(int count,int type);
    }
}

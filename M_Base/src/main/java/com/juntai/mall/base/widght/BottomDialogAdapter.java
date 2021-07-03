package com.juntai.mall.base.widght;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.R;
import com.juntai.mall.base.utils.DisplayUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/16 14:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/16 14:20
 */
public class BottomDialogAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BottomDialogAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.single_text_tv, item);

        TextView tv = helper.getView(R.id.single_text_tv);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
        params.height = DisplayUtil.dp2px(mContext, 50);
        tv.setLayoutParams(params);
    }
}

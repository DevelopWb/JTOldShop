package com.juntai.shop.mall.ui.goods.fmt;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.juntai.mall.base.base.BaseFragment;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MySection;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.bean.event.EventDetailsMessage;
import com.juntai.shop.mall.ui.adapter.ClassftLeftAdapter;
import com.juntai.shop.mall.ui.goods.adt.SectionClassftAdapter;
import com.juntai.shop.mall.ui.goods.SpecificationsDialog;
import com.juntai.shop.mall.utils.listener.GoodsStatusChangeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 店铺商品  分类
 */
public class GoodsFragment extends BaseFragment {
    RecyclerView recyclerViewLeft,recyclerViewRight;
    ClassftLeftAdapter leftAdapter;
    SectionClassftAdapter sectionAdapter;
    List<MySection> mData;
    LinearLayoutManager manager;
    View leftView;
    LinearSmoothScroller smoothScroller;
    LinearSmoothScroller smoothScrollerLeft;
    HashMap<String,Integer> tagLeft = new HashMap<>();
    HashMap<String,Integer> tagRight = new HashMap<>();
    TextView tvHead;
    //右侧滑动，左侧标记
    int nowTypePosition = -1;
    String nowType = "";
    //右侧是否是自动滑动
    boolean isAuto = false;
    MySection nowMySection;
    GoodsStatusChangeListener goodsStatusChangeListener;
    List<ShopBean.ReturnValueBean.ShopClassifyBean> dateList = new ArrayList<>();
    ArrayList<String> dateListLeft = new ArrayList<>();
    SpecificationsDialog specificationsDialog = new SpecificationsDialog();
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_classification;
    }

    @Override
    public void initView() {
        recyclerViewLeft = getView(R.id.classftLeftList);
        tvHead = getView(R.id.shop_header);
        recyclerViewRight = getView(R.id.classftRightList);
        recyclerViewLeft.setLayoutManager(new LinearLayoutManager(mContext));
        //左侧
        leftAdapter = new ClassftLeftAdapter(R.layout.item_classft_left,dateListLeft);
        recyclerViewLeft.setAdapter(leftAdapter);
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(1,200));
        leftAdapter.addFooterView(textView);
        leftAdapter.setOnItemClickListener((adapter, view, position) -> {
            isAuto = true;
            nowType = leftAdapter.getData().get(position);
            tvHead.setText(nowType);
            if (leftView != null) leftView.setBackgroundResource(R.color.background3);
            view.setBackgroundResource(R.drawable.line_classft_left);
            leftView = view;
            smoothScroller.setTargetPosition(tagRight.get(leftAdapter.getData().get(position)));
            recyclerViewRight.getLayoutManager().startSmoothScroll(smoothScroller);
        });
        //右侧
        recyclerViewRight.setLayoutManager(new LinearLayoutManager(mContext));
        mData = new ArrayList<>();
        sectionAdapter = new SectionClassftAdapter(R.layout.item_section_content, R.layout.item_section_head, mData);

//        TextView textView2 = new TextView(mContext);
//        textView2.setLayoutParams(new ViewGroup.LayoutParams(1,200));
//        sectionAdapter.addFooterView(textView2);

        sectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_shop_goodsimage){
                MySection mySection = mData.get(position);
                if (mySection.isHeader)
                    Toast.makeText(mContext, mySection.header, Toast.LENGTH_LONG).show();
                else{
                    EventBus.getDefault().post(new EventDetailsMessage(sectionAdapter.getData().get(position).t.getCommodityId()));
                }
            }
            if (view.getId() == R.id.cart_specification){
                specificationsDialog.setGoodsId(sectionAdapter.getData().get(position).t.getCommodityId());
                specificationsDialog.show(getChildFragmentManager(),"spec");
            }
        });
        recyclerViewRight.setAdapter(sectionAdapter);
        recyclerViewRight.setOnTouchListener((v, event) -> {
            isAuto = false;
            return false;
        });
        recyclerViewRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.e("onScrollStateChanged= newState=" + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (sectionAdapter.getData().size() > 0){
                    nowTypePosition = layoutManager.findFirstVisibleItemPosition();
                    nowMySection = sectionAdapter.getData().get(nowTypePosition);
                    if (!nowType.equals(nowMySection.getStrHead()) && !isAuto){
                        nowType = nowMySection.getStrHead();
                        tvHead.setText(nowType);
                        smoothScrollerLeft.setTargetPosition(tagLeft.get(nowMySection.getStrHead()));
                        recyclerViewLeft.getLayoutManager().startSmoothScroll(smoothScrollerLeft);
//                    recyclerViewLeft.getLayoutManager().scrollToPosition(tagLeft.get(nowMySection.getStrHead()));
                        if (leftView != null)
                            leftView.setBackgroundResource(R.color.background3);
                        if (leftAdapter.getTextViews().size() > 0){
                            leftView = leftAdapter.getTextViews().get(tagLeft.get(nowMySection.getStrHead()));
                        }
                        if (leftView != null)
                            leftView.setBackgroundResource(R.drawable.line_classft_left);
                    }
                }
            }
        });
        layoutManager = (LinearLayoutManager) recyclerViewRight.getLayoutManager();
//        recyclerViewRight.getLayoutManager().scrollToPosition();
        //顶部图片
//        ImageView imageView = new ImageView(mContext);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(recyclerViewRight.getLayoutParams().width,300));
//        Glide.with(mContext).load("https://gd3.alicdn.com/imgextra/i4/0/O1CN01T7SOEg1u3nlIfoDOf_!!0-item_pic.jpg")
//                .apply(new RequestOptions().centerCrop()).into(imageView);
//        sectionAdapter.addHeaderView(imageView);
        //联动滑动右侧
        smoothScroller = new LinearSmoothScroller(mContext){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScrollerLeft = new LinearSmoothScroller(mContext){
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        //
        initPop();
    }
    LinearLayoutManager layoutManager;
    @Override
    public void initData() {

    }

    /**
     * 从购物车修改数量，刷新列表数量
     */
    public void refreshList(int id,int num){
        if (sectionAdapter != null){
            sectionAdapter.refresh(id,num);
        }
    }

    public void initPop(){

    }
    /**
     * 设置事件
     * @param goodsStatusChangeListener
     */
    public void setGoodsStatusChangeListener(GoodsStatusChangeListener goodsStatusChangeListener) {
        this.goodsStatusChangeListener = goodsStatusChangeListener;
    }
    /**
     * 设置数据
     * @param list
     */
    public void setDateList(List<ShopBean.ReturnValueBean.ShopClassifyBean> list) {
        this.dateList = list;
        if (dateList == null){
            if (leftAdapter != null){
                leftAdapter.getData().clear();
                sectionAdapter.getData().clear();
            }
            return;
        }else {
            initListData();
            if (leftAdapter != null){
                leftAdapter.notifyDataSetChanged();
                sectionAdapter.notifyDataSetChanged();
            }
        }
    }

    public void initListData(){
        //
        int size = dateList.size();
        for (int i = 0; i < size; i++) {
            tagLeft.put(dateList.get(i).getShopClassifyName(),i);
            leftAdapter.addData(dateList.get(i).getShopClassifyName());
            mData.add(new MySection(true,dateList.get(i).getShopClassifyName(),false));
            if(dateList.get(i).getCommodity() != null
                    && dateList.get(i).getCommodity().size() > 0
                    && dateList.get(i).getCommodity().get(0) != null){
                for (ShopBean.ReturnValueBean.ShopClassifyBean.CommodityBean be:dateList.get(i).getCommodity()) {
                    mData.add(new MySection(dateList.get(i).getShopClassifyName(),be));
                }
            }
            tagRight.put(dateList.get(i).getShopClassifyName(),mData.size());
        }
//        mData.add(new MySection("",null));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        specificationsDialog.clear();
    }
}

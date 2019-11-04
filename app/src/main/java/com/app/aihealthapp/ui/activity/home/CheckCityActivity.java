package com.app.aihealthapp.ui.activity.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.helper.GsonHelper;
import com.app.aihealthapp.core.selectlibrary.CheckView;
import com.app.aihealthapp.core.selectlibrary.CitySelect;
import com.app.aihealthapp.core.selectlibrary.Province;
import com.app.aihealthapp.core.selectlibrary.SelectBean;
import com.app.aihealthapp.util.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealthApp
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/11/4 22:45
 * 修改人：Chen
 * 修改时间：2019/11/4 22:45
 */
public class CheckCityActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private final static int TAB_PROVINCE = 1;
    private final static int TAB_CITY = 2;
    private final static int TAB_AREA = 3;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private TextView mProvinceTextView;
    private TextView mCityTextView;
    private TextView mAreaTextView;
    private View mIndicatorView;
    private ListView mListView;
    private List<Province> mProvinces;
    private List<Province> mProvinceData = new ArrayList<>();
    private List<Province.City> mCityData = new ArrayList<>();
    private List<Province.Area> mAreaData = new ArrayList<>();
    private List<SelectBean> mData = new ArrayList<>();

    private int provinceSelectIndex = -1;
    private int citySelectIndex = -1;
    private int areaSelectIndex = -1;
    private SelectAdapter mSelectAdapter;

    private int tabSelect = TAB_PROVINCE;
    private Province province;
    private List<Province.City> mCities;
    private Province.City city;
    private int mainColor = Color.parseColor("#FF4040");
    private Dialog mDialog;
    @Override
    public int getLayoutId() {
        return R.layout.layout_city_select;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("选择省市");
    }

    @Override
    public void initView() {
        provinceSelectIndex = -1;
        citySelectIndex = -1;
        areaSelectIndex = -1;
        tabSelect = TAB_PROVINCE;

        fillData();

        mProvinceTextView = findViewById(R.id.tv_province);
        mCityTextView = findViewById(R.id.tv_city);
        mAreaTextView = findViewById(R.id.tv_area);
        mIndicatorView = findViewById(R.id.indicator);
        mListView = findViewById(R.id.lv_list);

        mProvinceTextView.setOnClickListener(this);
        mCityTextView.setOnClickListener(this);
        mAreaTextView.setOnClickListener(this);
        mProvinceTextView.measure(0, 0);
        ViewGroup.LayoutParams layoutParams = mIndicatorView.getLayoutParams();
        layoutParams.width = mProvinceTextView.getMeasuredWidth();
        mIndicatorView.setLayoutParams(layoutParams);
        mProvinceTextView.setTextColor(mainColor);
        initView();
        initAdapter();
    }
    private void fillData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mProvinces = GsonHelper.GsonToList(utils.InitAssetsData(CheckCityActivity.this,"city.json"), Province.class,"city");

            }
        }).start();
    }
    @Override
    public void initData() {

    }
    private void initAdapter() {
        mSelectAdapter = new SelectAdapter();
        mListView.setAdapter(mSelectAdapter);
        mListView.setOnItemClickListener(this);
        fillProvincesData();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_province) {//省
            if (tabSelect == TAB_PROVINCE) {
                return;
            }
            tabSelect = TAB_PROVINCE;
            mData.clear();
            for (int i = 0; i < mProvinceData.size(); i++) {
                SelectBean bean = new SelectBean();
                bean.setCode(mProvinceData.get(i).code);
                bean.setName(mProvinceData.get(i).name);
                mData.add(bean);
            }
//            mData.addAll(mProvinceData);
            mSelectAdapter.notifyDataSetChanged();
            selectProvinceTab();
            doIndicatorAnim(mProvinceTextView);
        } else if (v.getId() == R.id.tv_city) {//市
            if (tabSelect == TAB_CITY) {
                return;
            }
            tabSelect = TAB_CITY;
            mData.clear();
//            mData.addAll(mCityData);
            for (int i = 0; i < mCityData.size(); i++) {
                SelectBean bean = new SelectBean();
                bean.setCode(mCityData.get(i).code);
                bean.setName(mCityData.get(i).name);
                mData.add(bean);
            }
            mSelectAdapter.notifyDataSetChanged();
            selectCityTab();
            doIndicatorAnim(mCityTextView);
        } else if (v.getId() == R.id.tv_area) {//区
            if (tabSelect == TAB_AREA) {
                return;
            }
            tabSelect = TAB_AREA;
            mData.clear();
//            mData.addAll(mAreaData.);
            for (int i = 0; i < mAreaData.size(); i++) {
                SelectBean bean = new SelectBean();
                bean.setCode(mAreaData.get(i).code);
                bean.setName(mAreaData.get(i).name);
                mData.add(bean);
            }
            mSelectAdapter.notifyDataSetChanged();
            selectAreaTab();
            doIndicatorAnim(mAreaTextView);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (tabSelect) {
            case TAB_PROVINCE://省
                provinceSelectIndex = position;
                citySelectIndex = -1;
                areaSelectIndex = -1;
                province = mProvinces.get(position);
                String provinceName = province.name;
                mProvinceTextView.setText(provinceName);
                mCityTextView.setVisibility(View.VISIBLE);
                mCityTextView.setText("请选择");
                mAreaTextView.setVisibility(View.INVISIBLE);
                selectCityTab();
                fillCityData();
                tabSelect = TAB_CITY;
                doIndicatorAnim(mCityTextView);
                break;
            case TAB_CITY://市
                citySelectIndex = position;
                areaSelectIndex = -1;
                city = mCities.get(position);
                String cityName = city.name;
                mCityTextView.setText(cityName);
                mAreaTextView.setVisibility(View.VISIBLE);
                mAreaTextView.setText("请选择");
                selectAreaTab();
                fillAreaData();
                tabSelect = TAB_AREA;
                doIndicatorAnim(mAreaTextView);
                break;
            case TAB_AREA://区
                areaSelectIndex = position;
                String area = mAreaData.get(position).name;
                mAreaTextView.setText(area);
                Province provinceString = mProvinceData.get(provinceSelectIndex);
                Province.City cityString = mCityData.get(citySelectIndex);
                Province.Area areaString = mAreaData.get(areaSelectIndex);
                doIndicatorAnim(mAreaTextView);
                mSelectAdapter.notifyDataSetChanged();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (onSelectListener != null) {
                    onSelectListener.onSelect(provinceString, cityString, areaString);
                }
                break;
        }
    }

    /**
     * 填充省数据
     */
    private void fillProvincesData() {
        mProvinceData.clear();
        for (Province province : mProvinces) {
            mProvinceData.add(province);
        }
        mData.clear();
        for (int i = 0; i < mProvinces.size(); i++) {
            SelectBean bean = new SelectBean();
            bean.setCode(mProvinces.get(i).code);
            bean.setName(mProvinces.get(i).name);
            mData.add(bean);
        }
//        mData.addAll(mProvinceData);
        mSelectAdapter.notifyDataSetChanged();
    }

    /**
     * 填充市数据
     */
    private void fillCityData() {
        mCityData.clear();
        if (province != null) {
            mCities = province.cityList;
            for (Province.City city : mCities) {
                mCityData.add(city);
            }
            mData.clear();
            for (int i = 0; i < mCityData.size(); i++) {
                SelectBean bean = new SelectBean();
                bean.setCode(mCityData.get(i).code);
                bean.setName(mCityData.get(i).name);
                mData.add(bean);
            }
//            mData.addAll(mCityData);
            mSelectAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 填充区县市
     */
    private void fillAreaData() {
        mAreaData.clear();
        if (city != null) {
            List<Province.Area> areas = city.areaList;
            for (Province.Area area : areas) {
                mAreaData.add(area);
            }
            mData.clear();
            for (int i = 0; i < mAreaData.size(); i++) {
                SelectBean bean = new SelectBean();
                bean.setCode(mAreaData.get(i).code);
                bean.setName(mAreaData.get(i).name);
                mData.add(bean);
            }
//            mData.addAll(mAreaData);
            mSelectAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 选上区TAB
     */
    private void selectAreaTab() {
        mProvinceTextView.setTextColor(Color.BLACK);
        mCityTextView.setTextColor(Color.BLACK);
        mAreaTextView.setTextColor(mainColor);
    }

    /**
     * 选上市TAB
     */
    private void selectCityTab() {
        mProvinceTextView.setTextColor(Color.BLACK);
        mCityTextView.setTextColor(mainColor);
        mAreaTextView.setTextColor(Color.BLACK);
    }

    /**
     * 选上省TAB
     */
    private void selectProvinceTab() {
        mProvinceTextView.setTextColor(mainColor);
        mCityTextView.setTextColor(Color.BLACK);
        mAreaTextView.setTextColor(Color.BLACK);
    }

    /**
     * 游标动画
     */
    private void doIndicatorAnim(final TextView tabTextView) {
        tabTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float endX = tabTextView.getX();
                float endWidth = tabTextView.getMeasuredWidth();
                doAnim(endX, endWidth);
                tabTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * 真正做动画
     *
     * @param endX
     * @param endWidth
     */
    private void doAnim(float endX, float endWidth) {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mIndicatorView, "X", mIndicatorView.getX(), endX);
        final ViewGroup.LayoutParams layoutParams = mIndicatorView.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofFloat(layoutParams.width, endWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                layoutParams.width = (int) animatedValue;
                mIndicatorView.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(new Animator[]{translationAnimator, widthAnimator});
        set.start();
    }



    /**
     * 设置主题颜色
     *
     * @param color
     */
    public CheckCityActivity setMainColor(int color) {
        this.mainColor = color;
        mProvinceTextView.setTextColor(color);
        mIndicatorView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置省市区的数据
     *
     * @param provinces
     */
    public CheckCityActivity setProvinceData(List<Province> provinces) {
        this.mProvinces = provinces;
        fillProvincesData();
        return this;
    }

    private CitySelect.OnSelectListener onSelectListener;

    public CheckCityActivity listener(CitySelect.OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
        return this;
    }

    public interface OnSelectListener {
        void onSelect(Province province, Province.City city, Province.Area area);
    }


    class SelectAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public SelectAdapter() {
            layoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_city_name, parent, false);
                viewHolder = new SelectAdapter.ViewHolder();
                viewHolder.mTextView = convertView.findViewById(R.id.tv_text);
                viewHolder.mCheckview = convertView.findViewById(R.id.checkview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SelectAdapter.ViewHolder) convertView.getTag();
            }

            String city = mData.get(position).getName();
            viewHolder.mTextView.setText(city);
            viewHolder.mCheckview.setVisibility(View.INVISIBLE);
            viewHolder.mCheckview.setColor(mainColor);
            viewHolder.mTextView.setTextColor(Color.BLACK);

            switch (tabSelect) {
                case TAB_PROVINCE://省
                    if (provinceSelectIndex == position) {
                        viewHolder.mCheckview.setVisibility(View.VISIBLE);
                        viewHolder.mTextView.setTextColor(mainColor);
                    }
                    break;
                case TAB_CITY://市
                    if (citySelectIndex == position) {
                        viewHolder.mCheckview.setVisibility(View.VISIBLE);
                        viewHolder.mTextView.setTextColor(mainColor);
                    }
                    break;
                case TAB_AREA://区
                    if (areaSelectIndex == position) {
                        viewHolder.mCheckview.setVisibility(View.VISIBLE);
                        viewHolder.mTextView.setTextColor(mainColor);
                    }
                    break;
            }

            return convertView;
        }


        class ViewHolder {
            public TextView mTextView;
            public CheckView mCheckview;
        }
    }
}

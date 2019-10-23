package com.app.aihealthapp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.base.BaseFragmentPageAdapter;
import com.app.aihealthapp.core.helper.UserHelper;
import com.app.aihealthapp.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Name：AiHealth
 * @Description：我的咨询
 * @Author：Chen
 * @Date：2019/8/19 21:14
 * 修改人：Chen
 * 修改时间：2019/8/19 21:14
 */
public class MineAskActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_search_record)
    TextView tv_search_record;
    @BindView(R.id.view_search_record_line)
    View view_search_record_line;
    @BindView(R.id.tv_interrogation_record)
    TextView tv_interrogation_record;
    @BindView(R.id.view_interrogation_record_line)
    View view_interrogation_record_line;

    @BindView(R.id.viewpage_ask)
    NoScrollViewPager viewpage_ask;

    private BaseFragmentPageAdapter mBaseFragmentPageAdapter;
    private ArrayList<Fragment> mFragments;
    @Override
    public int getLayoutId() {
        return R.layout.acticity_mine_ask;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initToolBar();
        setTitle("我的咨询");
    }

    @Override
    public void initView() {
        mFragments = new ArrayList<>();
        if (UserHelper.getUserInfo().getKind_type()==1){
            tv_search_record.setText("问诊记录");
            tv_interrogation_record.setText("咨询记录");
            mFragments.add(SearchRecordFragment.getInstance(0));
            mFragments.add(SearchRecordFragment.getInstance(1));
        }else {
            tv_search_record.setText("咨询记录");
            tv_interrogation_record.setText("问诊记录");
            mFragments.add(InterrogationRecordFragment.getInstance(0));
            mFragments.add(InterrogationRecordFragment.getInstance(1));
        }
        mBaseFragmentPageAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), mFragments);
        viewpage_ask.setNoScroll(false);//设置viewpage 是否可以左右滑动
        viewpage_ask.setAdapter(mBaseFragmentPageAdapter);
        viewpage_ask.addOnPageChangeListener(this);
        viewpage_ask.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.tv_search_record, R.id.tv_interrogation_record})
    public void onClick(View v) {
        if (v == tv_search_record) {
            viewpage_ask.setCurrentItem(0);
            tv_search_record.setTextColor(getResources().getColor(R.color.default_text_color));
            tv_interrogation_record.setTextColor(getResources().getColor(R.color.text_color));
            view_search_record_line.setBackgroundResource(R.color.default_text_color);
            view_interrogation_record_line.setBackgroundResource(R.color.view_line);
        } else if (v == tv_interrogation_record) {
            viewpage_ask.setCurrentItem(1);
            tv_search_record.setTextColor(getResources().getColor(R.color.text_color));
            tv_interrogation_record.setTextColor(getResources().getColor(R.color.default_text_color));
            view_search_record_line.setBackgroundResource(R.color.view_line);
            view_interrogation_record_line.setBackgroundResource(R.color.default_text_color);
        }
    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        viewpage_ask.setCurrentItem(position);
        switch (position) {
            case 0:
                tv_search_record.setTextColor(getResources().getColor(R.color.default_text_color));
                tv_interrogation_record.setTextColor(getResources().getColor(R.color.text_color));
                view_search_record_line.setBackgroundResource(R.color.default_text_color);
                view_interrogation_record_line.setBackgroundResource(R.color.view_line);
                break;
            case 1:
                tv_search_record.setTextColor(getResources().getColor(R.color.text_color));
                tv_interrogation_record.setTextColor(getResources().getColor(R.color.default_text_color));
                view_search_record_line.setBackgroundResource(R.color.view_line);
                view_interrogation_record_line.setBackgroundResource(R.color.default_text_color);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

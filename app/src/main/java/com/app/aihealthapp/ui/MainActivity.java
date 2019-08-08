package com.app.aihealthapp.ui;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.app.aihealthapp.R;
import com.app.aihealthapp.core.base.BaseActivity;
import com.app.aihealthapp.core.base.BaseFragmentPageAdapter;
import com.app.aihealthapp.core.helper.ToastyHelper;
import com.app.aihealthapp.core.tablayout.CommonTabLayout;
import com.app.aihealthapp.core.tablayout.listener.CustomTabEntity;
import com.app.aihealthapp.core.tablayout.listener.OnTabSelectListener;
import com.app.aihealthapp.ui.activity.forum.ForumFragment;
import com.app.aihealthapp.ui.activity.home.HomeFragment;
import com.app.aihealthapp.ui.activity.manage.ManageFragment;
import com.app.aihealthapp.ui.activity.mine.MineFragment;
import com.app.aihealthapp.ui.activity.shop.ShopFragment;
import com.app.aihealthapp.ui.bean.TabEntityBean;
import com.app.aihealthapp.util.PermissionUtils;
import com.app.aihealthapp.view.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.vpage_main)
    NoScrollViewPager vpageMain;
    @BindView(R.id.tab_main)
    CommonTabLayout tabMain;
    private BaseFragmentPageAdapter mBaseFragmentPageAdapter;
    private ArrayList<Fragment> mFragments;
    private ArrayList<CustomTabEntity> mTabEntities;

    private String[] mTitles;
    private int[] mIconUnselectIds = {
            R.mipmap.home_icon_normal, R.mipmap.management_icon_normal,
            R.mipmap.forum_icon_normal, R.mipmap.mall_icon_normal,
            R.mipmap.ming_icon_normal};
    private int[] mIconSelectIds = {
            R.mipmap.home_icon_select, R.mipmap.management_icon_select,
            R.mipmap.forum_icon_select, R.mipmap.mall_icon_select,
            R.mipmap.mind_icon_select};


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public String returnToolBarTitle() {
        return null;
    }

    @Override
    public void initView() {
        mFragments = new ArrayList<>();
        mTabEntities = new ArrayList<>();
    }

    @Override
    public void initData() {
        mTitles = getResources().getStringArray(R.array.tab);
        mFragments.add(HomeFragment.getInstance(mTitles[0]));
        mFragments.add(ManageFragment.getInstance(mTitles[1]));
        mFragments.add(ForumFragment.getInstance(mTitles[2]));
        mFragments.add(ShopFragment.getInstance(mTitles[3]));
        mFragments.add(MineFragment.getInstance(mTitles[4]));

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntityBean(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mBaseFragmentPageAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), mFragments);
        vpageMain.setOffscreenPageLimit(5);
        vpageMain.setNoScroll(true);//设置viewpage 是否可以左右滑动
        vpageMain.setAdapter(mBaseFragmentPageAdapter);
        tabMain.setTabData(mTabEntities);
        tabMain.setOnTabSelectListener(this);
        vpageMain.addOnPageChangeListener(this);
        vpageMain.setCurrentItem(0);
    }

    @Override
    public void onTabSelect(int position) {
        vpageMain.setCurrentItem(position, false);

    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        tabMain.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
//记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public void onBackPressed() {

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastyHelper.toastyNormal(MainActivity.this, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            AppManager.getAppManager().AppExit(this);
            super.onBackPressed();
        }
    }


}

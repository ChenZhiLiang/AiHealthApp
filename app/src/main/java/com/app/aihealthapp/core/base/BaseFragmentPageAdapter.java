package com.app.aihealthapp.core.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * author：chenzl
 * Create time: 2017/11/7 0007 17:14
 * describe:
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class BaseFragmentPageAdapter extends FragmentStatePagerAdapter {

    List<Fragment>data;
    public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.data = data;
    }
    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

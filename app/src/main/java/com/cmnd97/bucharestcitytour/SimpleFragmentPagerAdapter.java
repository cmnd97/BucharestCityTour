package com.cmnd97.bucharestcitytour;

/**
 * Created by cristi-mnd on 06.07.17.
 * <p>
 * This class provides the appropriate Fragment for a vew pager
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }

    private int tabTitlesIds[] = new int[]{R.string.welcome, R.string.relax, R.string.learn, R.string.shop, R.string.fun};

    @Override
    public Fragment getItem(int position) {
        Fragment result = new Fragment();
        switch (position) {
            case 0:
                result = new WelcomeFragment();
                break;
            case 1:
                result = new RelaxFragment();
                break;
            case 2:
                result = new LearnFragment();
                break;
            case 3:
                result = new ShopFragment();
                break;
            case 4:
                result = new FunFragment();
                break;
        }
        return result;

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(tabTitlesIds[position]);
    }
}

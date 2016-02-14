package lcc.ishuhui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import lcc.ishuhui.fragment.CategoryFragment;
import lcc.ishuhui.fragment.SubscribeFragment;

/**
 * Created by lcc_luffy on 2016/1/30.
 */
public class CategoryFragmentAdapter extends FragmentPagerAdapter{
    List<Fragment> categoryFragments;
    public CategoryFragmentAdapter(FragmentManager fm) {
        super(fm);
        categoryFragments = new ArrayList<>(4);

        categoryFragments.add(SubscribeFragment.newInstance());
        categoryFragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_HOT));
        categoryFragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_SAME));
        categoryFragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_MOUSE));
    }

    @Override
    public Fragment getItem(int position) {
        return categoryFragments.get(position);
    }

    @Override
    public int getCount() {
        return categoryFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryFragments.get(position).toString();
    }
}

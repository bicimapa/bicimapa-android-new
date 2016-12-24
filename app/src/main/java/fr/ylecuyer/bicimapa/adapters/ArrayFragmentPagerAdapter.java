package fr.ylecuyer.bicimapa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ArrayFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<String> names = new ArrayList<String>();

    public ArrayFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> names) {
        super(fm);
        this.fragments = fragments;
        this.names = names;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

}

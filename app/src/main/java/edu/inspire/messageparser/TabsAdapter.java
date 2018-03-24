package edu.inspire.messageparser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabsAdapter extends FragmentStatePagerAdapter {

    String[] tabNames;

    public TabsAdapter(FragmentManager fm, String[] tabNames){
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fr = null;

        switch (position){
            case 0:
                fr = new EmergencyFragment();
                break;
            case 1:
                fr = new HappyFragment();
                break;
            case 2:
                fr = new EmotionalFragment();
                break;
        }


        return fr;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}

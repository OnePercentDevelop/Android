package sumus.com.onepercent.Object;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sumus.com.onepercent.Fragment.MainFragment;
import sumus.com.onepercent.Fragment.MoreFragment;
import sumus.com.onepercent.Fragment.PrizeFragment;
import sumus.com.onepercent.Fragment.VoteFragment;

/**
 * Created by MINI on 2016-10-27.
 */

 public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = null;
        switch (position) {
            case 0:
                fragment =  MainFragment.newInstance("hello", "world");
                break;
            case 1:
                fragment = VoteFragment.newInstance("hello", "world");
                break;
            case 2:
                fragment = PrizeFragment.newInstance("hello", "world");
                break;
            case 3:
                fragment = MoreFragment.newInstance("hello", "world");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    





    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
//            case 0:
//                return "홈";
//            case 1:
//                return "질문";
//            case 2:
//                return "당첨자";
//            case 3:
//                return "더보기";
        }
        return null;
    }
}
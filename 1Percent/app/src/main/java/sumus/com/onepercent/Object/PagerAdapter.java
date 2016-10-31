package sumus.com.onepercent.Object;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import sumus.com.onepercent.R;

/**
 * Created by MINI on 2016-10-31.
 */

public class PagerAdapter  extends android.support.v4.view.PagerAdapter{

    private LayoutInflater mInflater;

    public PagerAdapter(Context c){
        super();
        mInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(View pager, int position) {
        View v = null;
        if(position==0){
            v = mInflater.inflate(R.layout.fragment_main, null);
        }
        else if(position==1){
            v = mInflater.inflate(R.layout.fragment_vote, null);
        }else if(position==2){
            v = mInflater.inflate(R.layout.fragment_prize, null);
        }else{
            v = mInflater.inflate(R.layout.fragment_more, null);
        }

        ((ViewPager)pager).addView(v, 0);

        return v;
    }

    @Override
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    @Override
    public boolean isViewFromObject(View pager, Object obj) {
        return pager == obj;
    }

    @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
    @Override public Parcelable saveState() { return null; }
    @Override public void startUpdate(View arg0) {}
    @Override public void finishUpdate(View arg0) {}
}

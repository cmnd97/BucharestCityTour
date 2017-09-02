package com.cmnd97.bucharestcitytour;

/**
 * Created by cristi-mnd on 19.07.17.
 * <p>
 * This class is used to create slideshows for entries
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class CustomAdapter extends PagerAdapter {
    private Entry entry;
    private LayoutInflater inflater;
    private Context context;

    CustomAdapter(Context context, Entry entry) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.entry = entry;
    }

    @Override
    public int getCount() {
        return entry.getImageArraySize();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pos) {
        View v = inflater.inflate(R.layout.swipe, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.ImagesContainer);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        img.setImageResource(entry.getDestImageId(pos));
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

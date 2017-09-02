package com.cmnd97.bucharestcitytour;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cristi-mnd on 07.07.17.
 * <p>
 * This class is used to create an adapter for the main listview
 */

class EntryAdapter extends ArrayAdapter<Entry> {

    EntryAdapter(Context context, ArrayList<Entry> entries) {
        super(context, 0, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;


        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Entry currentEntry = getItem(position);

        //setting entry components to modify them later
        ((TextView) listItemView.findViewById(R.id.entry_description)).setText(currentEntry.getDestDescription());
        ((TextView) listItemView.findViewById(R.id.entry_name)).setText(currentEntry.getDestName());
        currentEntry.setEntryGpsIndicator((ImageView) listItemView.findViewById(R.id.entry_gps_indicator));
        currentEntry.setEntryDistanceView((TextView) listItemView.findViewById(R.id.entry_distance));
        currentEntry.setHiddenContainer(listItemView.findViewById(R.id.hidden_container));
        currentEntry.setArrowDropper((ImageView) listItemView.findViewById(R.id.arrow_dropper));
        currentEntry.setImagePager((ViewPager) listItemView.findViewById(R.id.entry_image_pager));
        currentEntry.setImagePagerDots((TabLayout) listItemView.findViewById(R.id.entry_image_indicator));
        currentEntry.setWalkDirectionsView((ImageView) listItemView.findViewById(R.id.directions_walk));
        currentEntry.setPtDirectionsView((ImageView) listItemView.findViewById(R.id.directions_pt));
        currentEntry.setTaxiDirectionsView((ImageView) listItemView.findViewById(R.id.directions_taxi));
        return listItemView;
    }
}
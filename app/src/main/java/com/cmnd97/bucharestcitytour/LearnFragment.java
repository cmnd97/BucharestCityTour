package com.cmnd97.bucharestcitytour;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class LearnFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public LearnFragment() {
        // Required empty public constructorconstructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Location userLoc = new Location("userLoc");
        MainMenu baseActivity = (MainMenu) getActivity();
        if (baseActivity.isLocationEnabled())
            userLoc = baseActivity.getUserLoc();

        View rootView = inflater.inflate(R.layout.entry_list, container, false);
        final ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(new int[]{R.drawable.l1p1, R.drawable.l1p2, R.drawable.l1p3}, getString(R.string.l1t), getString(R.string.l1d), 44.468469, 26.080531, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.l2p1, R.drawable.l2p2, R.drawable.l2p3}, getString(R.string.l2t), getString(R.string.l2d), 44.4531131, 26.0846382, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.l3p1, R.drawable.l3p2, R.drawable.l3p3}, getString(R.string.l3t), getString(R.string.l3d), 44.4393668, 26.095874, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.l4p1, R.drawable.l4p2, R.drawable.l4p3}, getString(R.string.l4t), getString(R.string.l4d), 44.4275035, 26.0873506, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.l5p1, R.drawable.l5p2, R.drawable.l5p3}, getString(R.string.l5t), getString(R.string.l5d), 44.4413202, 26.0972792, baseActivity.isLocationEnabled()));

        if (baseActivity.isLocationEnabled()) calculateDistances(entries, userLoc);

        EntryAdapter adapter = new EntryAdapter(getActivity(), entries);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Entry entry = entries.get(position);
                if (entry.getHiddenContainer().getVisibility() == View.VISIBLE) {
                    entry.getHiddenContainer().setVisibility(View.GONE);
                    entry.getArrowDropper().setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                } else {
                    entry.getHiddenContainer().setVisibility(View.VISIBLE);
                    entry.getArrowDropper().setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    entry.getImagePager().setAdapter(new CustomAdapter(getActivity(), entry));
                    entry.getImagePagerDots().setupWithViewPager(entry.getImagePager(), true);
                    setDirectionListener(entry);


                }
            }

        });
        return rootView;

    }


    void setDirectionListener(final Entry entry) {

        entry.getWalkDirectionsView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + entry.getDestLocation().getLatitude() + "," + entry.getDestLocation().getLongitude() + "&travelmode=walking"));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        entry.getPtDirectionsView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + entry.getDestLocation().getLatitude() + "," + entry.getDestLocation().getLongitude() + "&travelmode=transit"));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        entry.getTaxiDirectionsView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + entry.getDestLocation().getLatitude() + "," + entry.getDestLocation().getLongitude() + "&travelmode=driving"));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public void calculateDistances(ArrayList<Entry> entries, Location userLoc) {
        for (int i = 0; i < entries.size(); i++)
            entries.get(i).setDistanceToDest(userLoc.distanceTo(entries.get(i).getDestLocation()) / 1000); //in km
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

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


public class FunFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public FunFragment() {
        // Required empty public constructor
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
        entries.add(new Entry(new int[]{R.drawable.f1p1, R.drawable.f1p2, R.drawable.f1p3}, getString(R.string.f1t), getString(R.string.f1d), 44.4677414, 26.1179345, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.f2p1, R.drawable.f2p2, R.drawable.f2p3}, getString(R.string.f2t), getString(R.string.f2d), 44.4724799, 26.1122162, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.f3p1, R.drawable.f3p2, R.drawable.f3p3}, getString(R.string.f3t), getString(R.string.f3d), 44.4750515, 26.0760592, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.f4p1, R.drawable.f4p2, R.drawable.f4p3}, getString(R.string.f4t), getString(R.string.f4d), 44.4325345, 26.1016295, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.f5p1, R.drawable.f5p2, R.drawable.f5p3}, getString(R.string.f5t), getString(R.string.f5d), 44.4453839, 26.0586003, baseActivity.isLocationEnabled()));


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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

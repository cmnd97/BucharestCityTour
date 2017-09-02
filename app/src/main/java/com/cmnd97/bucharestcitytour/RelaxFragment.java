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


public class RelaxFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public RelaxFragment() {
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
        entries.add(new Entry(new int[]{R.drawable.r1p1, R.drawable.r1p2, R.drawable.r1p3}, getString(R.string.r1t), getString(R.string.r1d), 44.468469, 26.080531, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.r2p1, R.drawable.r2p2, R.drawable.r2p3}, getString(R.string.r2t), getString(R.string.r2d), 44.4369687, 26.0909837, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.r3p1, R.drawable.r3p2, R.drawable.r3p3}, getString(R.string.r3t), getString(R.string.r3d), 44.4131638, 26.0956123, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.r4p1, R.drawable.r4p2, R.drawable.r4p3}, getString(R.string.r4t), getString(R.string.r4d), 44.4195921, 26.1570534, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.r5p1, R.drawable.r5p2, R.drawable.r5p3}, getString(R.string.r5t), getString(R.string.r5d), 44.4057176, 26.1060607, baseActivity.isLocationEnabled()));


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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

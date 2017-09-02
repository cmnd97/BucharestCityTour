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


public class ShopFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
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
        entries.add(new Entry(new int[]{R.drawable.s1p1, R.drawable.s1p2, R.drawable.s1p3}, getString(R.string.s1t), getString(R.string.s1d), 44.4325076, 26.0981863, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.s2p1, R.drawable.s2p2, R.drawable.s2p3}, getString(R.string.s2t), getString(R.string.s2d), 44.430609, 26.0521352, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.s3p1, R.drawable.s3p2, R.drawable.s3p3}, getString(R.string.s3t), getString(R.string.s3d), 44.4206244, 26.1500617, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.s4p1, R.drawable.s4p2, R.drawable.s4p3}, getString(R.string.s4t), getString(R.string.s4d), 44.4278659, 26.1045774, baseActivity.isLocationEnabled()));
        entries.add(new Entry(new int[]{R.drawable.s5p1, R.drawable.s5p2, R.drawable.s5p3}, getString(R.string.s5t), getString(R.string.s5d), 44.4417741, 26.1522835, baseActivity.isLocationEnabled()));

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

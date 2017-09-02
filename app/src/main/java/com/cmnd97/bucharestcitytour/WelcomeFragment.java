package com.cmnd97.bucharestcitytour;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WelcomeFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        MainMenu baseActivity = (MainMenu) getActivity();
        if (!baseActivity.isLocationEnabled())
            rootView.findViewById(R.id.loc_tip).setVisibility(View.VISIBLE);
        return rootView;
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

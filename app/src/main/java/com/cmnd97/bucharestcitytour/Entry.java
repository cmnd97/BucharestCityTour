package com.cmnd97.bucharestcitytour;

import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cristi-mnd on 07.07.17.
 */

class Entry {
    private int destImageIds[] = new int[3];
    private String destName;
    private String destDescription;
    private View hiddenContainer;
    private ImageView arrowDropper;
    private ViewPager imagePager;
    private TabLayout imagePagerDots;
    private ImageView entryGpsIndicator;
    private TextView entryDistanceView;
    private double distanceToDest = 0;
    private ImageView walkDirectionsView;
    private ImageView ptDirectionsView;
    private Boolean LocAcquired;
    private ImageView taxiDirectionsView;
    private Location destLocation = new Location("destLoc");

    Entry(int destImageIds[], String destName, String destDescription, double destLat, double destLong, Boolean LocAcquired) {
        for (int i = 0; i < 3; i++)
            this.destImageIds[i] = destImageIds[i];
        this.destName = destName;
        this.destLocation.setLatitude(destLat);
        this.destLocation.setLongitude(destLong);
        this.destDescription = destDescription;
        this.LocAcquired = LocAcquired;
    }

    int getDestImageId(int index) {
        return destImageIds[index];
    }

    int getImageArraySize() {
        return 3;
    }

    String getDestName() {
        return destName;
    }

    String getDestDescription() {
        return destDescription;
    }

    Location getDestLocation() {
        return destLocation;
    }

    View getHiddenContainer() {
        return hiddenContainer;
    }

    void setHiddenContainer(View entryDescription) {
        this.hiddenContainer = entryDescription;
    }

    ImageView getArrowDropper() {
        return arrowDropper;
    }

    void setArrowDropper(ImageView arrowDropper) {
        this.arrowDropper = arrowDropper;
    }

    void setImagePager(ViewPager imagePager) {
        this.imagePager = imagePager;
    }

    void setImagePagerDots(TabLayout imagePagerDots) {
        this.imagePagerDots = imagePagerDots;
    }

    ViewPager getImagePager() {
        return imagePager;
    }

    TabLayout getImagePagerDots() {
        return imagePagerDots;
    }


    ImageView getWalkDirectionsView() {
        return walkDirectionsView;
    }

    void setWalkDirectionsView(ImageView walkDirections) {
        this.walkDirectionsView = walkDirections;
    }

    ImageView getPtDirectionsView() {
        return ptDirectionsView;
    }

    void setPtDirectionsView(ImageView ptDirections) {
        this.ptDirectionsView = ptDirections;
    }

    ImageView getTaxiDirectionsView() {
        return taxiDirectionsView;
    }

    void setTaxiDirectionsView(ImageView taxiDirections) {
        this.taxiDirectionsView = taxiDirections;
    }

    void setEntryGpsIndicator(ImageView entryGpsIndicator) {
        this.entryGpsIndicator = entryGpsIndicator;
        this.entryGpsIndicator.setImageResource(LocAcquired ? R.drawable.ic_location_on_black_24dp : R.drawable.ic_location_off_black_24dp);
    }


    void setEntryDistanceView(TextView entryDistanceView) {
        this.entryDistanceView = entryDistanceView;
        if (distanceToDest != 0) {
            distanceToDest = Math.floor(distanceToDest * 100) / 100;
            this.entryDistanceView.setText(distanceToDest + " km");
            //fetching a String resource outside of an Activity/Fragment (passing context to every entry) would be too much effort, no need to translate "km"
        }
    }


    void setDistanceToDest(double distanceToDest) {
        this.distanceToDest = distanceToDest;
    }

}


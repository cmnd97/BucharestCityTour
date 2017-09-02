package com.cmnd97.bucharestcitytour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener listener;
    private Location userLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        int[] tabImagesId = {
                R.drawable.ic_nature_people_black_24dp,
                R.drawable.ic_account_balance_black_24dp,
                R.drawable.ic_shopping_basket_black_24dp,
                R.drawable.smiley_face};

        for (int i = 0; i < tabImagesId.length; i++) {
            tabLayout.getTabAt(i + 1).setIcon(tabImagesId[i]);
            tabLayout.getTabAt(i + 1).getIcon().setColorFilter(ContextCompat.getColor(this, R.color.entrytext), PorterDuff.Mode.SRC_ATOP); //white icons on tab, first at i=0 is text not icon
            tabLayout.getTabAt(i + 1).setText("");
        }


        if (android.os.Build.VERSION.SDK_INT >= 22)
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabTitlesIds[] = {R.string.app_name, R.string.relax, R.string.learn, R.string.shop, R.string.fun};
                    setTitle(getString(tabTitlesIds[tabLayout.getSelectedTabPosition()]));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

        /** This is used to support my own phone, running on API level 19 ( OS v4.4.2 Kitkat )
         the new (addOnTabSelectedListener)method requires API level 22 or higher
         */
        else
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabTitlesIds[] = {R.string.app_name, R.string.relax, R.string.learn, R.string.shop, R.string.fun};
                    setTitle(getString(tabTitlesIds[tabLayout.getSelectedTabPosition()]));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLoc = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };

        getLocation();
    }

    public void goToLocationSettings(View v) {
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);
    }

    public Boolean isLocationEnabled() {
        if (userLoc == null)
            return false;
        else
            return true;
    }

    public Location getUserLoc() {
        return userLoc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                getLocation();
                break;
            default:
                break;
        }
    }

    void getLocation() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}
                        , 10);
            }
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

    }

}


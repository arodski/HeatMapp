package com.wedevjs.heatmapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFrag extends Fragment  {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final LatLng CHAMPAIGN = new LatLng(40.107725, -88.227889);
    LayoutInflater inflater;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5 * 1; // 1 minute

    /* Campus Bars */
    private final LatLng RED_LION = new LatLng(40.109971, -88.235613);
    private final LatLng KAMS = new LatLng(40.108167, -88.229495);
    private final LatLng JOES = new LatLng(40.109702, -88.231851);
    private final LatLng MURPHYS = new LatLng(40.110514, -88.230165);
    private final LatLng WHITE_HORSE = new LatLng(40.109259, -88.230881);
    private final LatLng BROTHERS = new LatLng(40.110125, -88.229687);
    private final LatLng LEGENDS = new LatLng(40.110486, -88.231160);
    private final LatLng CLYBOURNE = new LatLng(40.109852, -88.230251);
    private final LatLng FIREHAUS = new LatLng(40.109698, -88.230275);

    /* Campus Libraries */
    private final LatLng GRAINGER_LIB = new LatLng(40.112500, -88.226916);
    private final LatLng MAIN_LIB = new LatLng(40.104711, -88.229012);
    private final LatLng UNDERGRAD_LIB = new LatLng(40.104658, -88.227176);
    private final LatLng ACES_LIB = new LatLng(40.102834, -88.225138);

    LocationClient mLocationClient;
    private Marker marker;
    private Marker userMarker;
    ArrayList<Marker> marker_list = new ArrayList<Marker>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUpMapIfNeeded();

        setHasOptionsMenu(true);



        return rootView;
    }



    @Override
    public void onDestroyView() {
        MapFragment f = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_container);
        if (f != null) {
            try {
                getFragmentManager().beginTransaction().remove(f).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mMap != null) {
            if (marker_list != null) {
                for (Marker marker : marker_list) {
                    marker.remove();
                    marker = null;
                }
            }
            if(userMarker != null) {
                userMarker.remove();

            }
        }
        mMap = null;
        super.onDestroyView();

    }

    public void openFilter(){

        new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("Filter")
                .setMessage("Filter categories on the map")
                .setNeutralButton("Study areas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (marker_list != null) {
                            for (Marker marker : marker_list) {
                                marker.remove();
                                marker = null;
                            }
                        }
                        addLibraries();
                    }
                })
                .setNegativeButton("Bars", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (marker_list != null) {
                            for (Marker marker : marker_list) {
                                marker.remove();
                                marker = null;
                            }
                        }
                        addBars();
                    }
                })
                .setPositiveButton("Both", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (marker_list != null) {
                            for (Marker marker : marker_list) {
                                marker.remove();
                                marker = null;
                            }
                        }
                        addLibraries();
                        addBars();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_map)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gotoCurrentLocation:
                goToCurrentLocation();
                return true;
            case R.id.filter:
                openFilter();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map_container))
                    .getMap();

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                // Reuse the frame
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.info_window, null);
                    TextView title = (TextView) v.findViewById(R.id.locality);
                    TextView crowd = (TextView) v.findViewById(R.id.crowd);
                    ImageView image = (ImageView) v.findViewById(R.id.image);

                    title.setText(marker.getTitle());
                    crowd.setText(marker.getSnippet());
                    image.setImageResource(R.drawable.pic_example);

                    return v;
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String name = marker.getTitle();
                    String[] people_str = marker.getSnippet().split(" ");
                    String people = people_str[0];
                    Intent intent = new Intent(getActivity(), PicturesActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("people", people);
                    startActivity(intent);
                    // Go To Picture Activity
                }
            });


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }


        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CHAMPAIGN, 15));

        addBars();
        addLibraries();
    }

    private void addBars(){
        /* BARS */
        setMarker("KAM'S", KAMS.latitude, KAMS.longitude, 217, 87, 5);
        setMarker("Joe's", JOES.latitude, JOES.longitude, 192, 83, 5);
        setMarker("Brothers", BROTHERS.latitude, BROTHERS.longitude, 186, 72, 4);
        setMarker("Red Lion", RED_LION.latitude, RED_LION.longitude, 201, 72, 4);
        setMarker("Clybourne", CLYBOURNE.latitude, CLYBOURNE.longitude, 182, 59, 3);
        setMarker("Legends", LEGENDS.latitude, LEGENDS.longitude, 92, 47, 3);
        setMarker("Firehaus", FIREHAUS.latitude, FIREHAUS.longitude, 87, 35, 2);
        setMarker("Murphy's", MURPHYS.latitude, MURPHYS.longitude, 125, 34, 2);
        setMarker("White Horse", WHITE_HORSE.latitude, WHITE_HORSE.longitude, 39, 20, 1);
    }

    private void addLibraries(){
        /* LIBRARY */
        setMarker("Grainger Library", GRAINGER_LIB.latitude, GRAINGER_LIB.longitude, 218, 71, 4);
        setMarker("Main Library", MAIN_LIB.latitude, MAIN_LIB.longitude, 92, 29, 2);
        setMarker("Undergraduate Library", UNDERGRAD_LIB.latitude, UNDERGRAD_LIB.longitude, 285, 74, 4);
        setMarker("ACES Library", ACES_LIB.latitude, ACES_LIB.longitude, 198, 65, 4);
    }


    private void setMarker(String locality, double lat, double lng, int people, int full, int scale) {
        int resource_id;
        switch (scale){
            case 1:
                resource_id = R.drawable.marker_scale_1;
                break;
            case 2:
                resource_id = R.drawable.marker_scale_2;
                break;
            case 3:
                resource_id = R.drawable.marker_scale_3;
                break;
            case 4:
                resource_id = R.drawable.marker_scale_4;
                break;
            default:
                resource_id = R.drawable.marker_scale_5;
                break;
        }
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat, lng))
                .snippet(people + " People\n" + full + "% Full")
                .icon(BitmapDescriptorFactory.fromResource(resource_id));
        marker = mMap.addMarker(options);
        marker_list.add(marker);
    }

    protected void goToCurrentLocation() {

        LocationManager locationManager;
        locationManager = (LocationManager) getActivity()
                .getSystemService(getActivity().LOCATION_SERVICE);

        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSEnabled) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(location != null && mMap != null) {
                        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 18);
                        mMap.animateCamera(update);

                        MarkerOptions user_loc = new MarkerOptions()
                                .position(new LatLng(ll.latitude, ll.longitude))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location))
                                .title("");
                        if (userMarker != null) {
                            userMarker.remove();
                            userMarker = null;
                        }
                        userMarker = mMap.addMarker(user_loc);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(location != null && mMap != null) {
                        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 18);
                        mMap.animateCamera(update);

                        MarkerOptions user_loc = new MarkerOptions()
                                .position(new LatLng(ll.latitude, ll.longitude))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location))
                                .title("");
                        if (userMarker != null) {
                            userMarker.remove();
                            userMarker = null;
                        }
                        userMarker = mMap.addMarker(user_loc);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        } else{
            Toast.makeText(getActivity(), "Your GPS is disabled. Please enable it to see your location", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
}

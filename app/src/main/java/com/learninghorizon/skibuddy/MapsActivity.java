package com.learninghorizon.skibuddy;

/**
 * Created by ramnivasindani on 11/30/15.
 */
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.learninghorizon.skibuddy.model.TravelPoint;
import com.learninghorizon.skibuddy.utility.DataUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    double latitude;
    double longitude;
    String userName;
    int sessionRecordLocation;
    boolean firstPoint = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        Bundle bundle = getIntent().getExtras();


        userName = bundle.getString("userName");
        sessionRecordLocation = bundle.getInt("sessionRecordLocation");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(null != userName) {
            latitude = Double.valueOf(bundle.getString("latitude"));
            longitude = Double.valueOf(bundle.getString("longitude"));

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(latitude,
                            longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            mapFragment.getMap().moveCamera(center);
            mapFragment.getMap().animateCamera(zoom);
        }else{
            List<LatLng> latLngs = new ArrayList<>();
            List<TravelPoint> travelPoints = DataUtil.getInstance().getSkiSession(sessionRecordLocation).getTravelPoints();
            for(TravelPoint travelPoint : travelPoints){
                latLngs.add(new LatLng(Float.valueOf(travelPoint.getLatitude()),
                        Float.valueOf(travelPoint.getLongitude())));
            }
           /* Polyline line = mapFragment.getMap().addPolyline(new PolylineOptions()
                    .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                    .width(5)
                    .color(Color.RED));*/
            LatLngBounds.Builder b = null;

            for (int i = 0; i < latLngs.size() - 1; i++) {
                LatLng src = latLngs.get(i);
                LatLng dest = latLngs.get(i + 1);

                // mMap is the Map Object\

                    Polyline line = mapFragment.getMap().addPolyline(
                            new PolylineOptions().add(
                                    new LatLng(src.latitude, src.longitude),
                                    new LatLng(dest.latitude, dest.longitude)
                            ).width(10).color(Color.GREEN).geodesic(true)
                    );


                 b = new LatLngBounds.Builder();

                    b.include(new LatLng(src.latitude, src.longitude));
                    //b.include(new LatLng(dest.latitude, dest.longitude));


            }
            try {
                LatLngBounds bounds = b.build();
//Change the padding as per needed
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100, 100, 5);
                mapFragment.getMap().animateCamera(cu);

            }catch(Exception exception){
                exception.printStackTrace();
            }
           // CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
            //mapFragment.getMap().animateCamera(zoom);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if(null != userName) {
            map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(userName));
        }
    }
}

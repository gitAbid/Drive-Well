package com.drivewell.drivewell.ui.roadcondition;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.RoadConditionMapModel;
import com.drivewell.drivewell.repository.IDataProvider;
import com.drivewell.drivewell.repository.MapConditionDataProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abid on 1/24/18.
 */

public class RoadConditionMapPresenter implements OnMapReadyCallback,IRoadConditionMapPresenter, RoutingListener {
    private MapView mRoadConditionMap;
    private Context context;
    LatLng nsu;
    LatLng home;
    LatLng banani;
    private List<Polyline> polylines;
    GoogleMap map;
    private static final int[] COLORS = new int[]{R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorStatusGreen,R.color.colorStatusRed,R.color.colorAccent};
    private boolean s=false;
    private LatLng ziacolony;
    private static String condition;

    private IDataProvider roadConditionProvider=new MapConditionDataProvider();

    @Override
    public void initializeMap(MapView mapView, Context context) {
        mRoadConditionMap=mapView;
        this.context=context;
        if (mRoadConditionMap!=null){
            mRoadConditionMap.onCreate(null);
            mRoadConditionMap.onResume();
            mRoadConditionMap.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(context);
        LatLng sydney = new LatLng(-33.867, 151.206);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        /**
         *
         *Demo Purpose TODO real mechanism to be implemented
         *Working Manually Now need to be automated
         */

        nsu=new LatLng(23.8151079,90.4233493);
        home = new LatLng(23.7633282,90.3934519);
        banani=new LatLng(23.7946976,90.3971412);
        ziacolony=new LatLng(23.815096,90.4039594);


        map=googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 13));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

      /*  googleMap.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));*/


       List<RoadConditionMapModel> roadCondition= (List<RoadConditionMapModel>) roadConditionProvider.getListData();

        for (RoadConditionMapModel roadConditionMapModel:roadCondition) {
            condition=roadConditionMapModel.getCondition();
            drawRoute(roadConditionMapModel.getStartingLocation(),roadConditionMapModel.getEndLocation(),roadConditionMapModel.getCondition().trim());
            Log.i("RoadConditon",roadConditionMapModel.getCondition());
        }





    }

    private synchronized void drawRoute(LatLng startPoint,LatLng endPoint, String con) {
        Routing routing=new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(startPoint,endPoint)
                .build();

        routing.execute();

    }


    public RoadConditionMapPresenter() {
        super();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int index) {


        /**
         *
         *Demo Purpose TODO real mechanism to be implemented
         *Working Manually Now need to be automated
         */


        CameraUpdate center=CameraUpdateFactory.newLatLng(home);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(14);

        map.moveCamera(center);
        map.animateCamera(zoom);


        polylines = new ArrayList<>();
        //add route(s) to the map.

        Log.i("Condition",condition);

        PolylineOptions polyOptions = new PolylineOptions();
       if (condition.equals("b")){
           polyOptions.color(context.getResources().getColor(R.color.colorStatusRed));
       }
       else if (condition.equals("g")){
           polyOptions.color(context.getResources().getColor(R.color.colorStatusGreen));
       }
        polyOptions.width(10);
        polyOptions.addAll(route.get(0).getPoints());
        Polyline polyline = map.addPolyline(polyOptions);
        polylines.add(polyline);


        Log.i("Points", String.valueOf(route.get(0).getPoints().size()));



       /* for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(context.getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = map.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(context,"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }



*/

/*
        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(home);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp));
        map.addMarker(options);
        
        map.addMarker(options);
        options = new MarkerOptions();
        options.position(nsu);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp));
        map.addMarker(options);*/
    }

    @Override
    public void onRoutingCancelled() {

    }


}

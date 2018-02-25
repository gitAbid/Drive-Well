package com.drivewell.drivewell.ui.roadcondition;

import android.content.Context;

import com.google.android.gms.maps.MapView;

/**
 * Created by abid on 1/24/18.
 */

public interface IRoadConditionMapPresenter {

    void initializeMap(MapView mapView, Context context);
}

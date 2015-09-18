package edu.minhld.locationdetector;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac on 9/18/15.
 */
public class LocationRetriever {
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    GpsStatus.Listener mGPSStatusListener;

    TextView infoText;

    public LocationRetriever(Context c, TextView infoText){
        this.infoText = infoText;

        mLocationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                writeLog(location.getProvider() +
                        " (" + location.getLatitude() +
                        "," + location.getLongitude() + ")");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                writeLog(provider + " is enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                writeLog(provider + " is disabled");
            }
        };

        try {
//            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            mGPSStatusListener = new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    switch (event) {
                        case GpsStatus.GPS_EVENT_STARTED : {
                            writeLog("gps started");
                        }
                        case GpsStatus.GPS_EVENT_STOPPED : {
                            writeLog("gps stopped");
                        }
                        case GpsStatus.GPS_EVENT_FIRST_FIX : {
                            try {
                                Location gpsLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                if(gpsLocation != null)
                                {
                                    writeLog("gps info: " +
                                            gpsLocation.getLatitude() + "," +
                                            gpsLocation.getLongitude());

                                    mLocationManager.removeGpsStatusListener(mGPSStatusListener);
                                }
                            }catch(Exception e){
                                writeLog(e.getClass() + ": " + e.getMessage());
                            }
                        }
                        case GpsStatus.GPS_EVENT_SATELLITE_STATUS : {



                        }
                    }
                }
            };

            mLocationManager.addGpsStatusListener(mGPSStatusListener);
        }catch(SecurityException sEx){
            writeLog("[error]" + sEx.getClass() + ": " + sEx.getMessage());
        }


    }

    public void requestLocation(){

    }

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    private void writeLog(String msg){
        this.infoText.append(sdf.format(new Date()) + ": " + msg + "\r\n");
    }
}

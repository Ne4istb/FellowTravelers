package gdgdevfest.walker.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gdgdevfest.walker.R;
import gdgdevfest.walker.geofence.GeofenceRemover;
import gdgdevfest.walker.geofence.GeofenceRequester;
import gdgdevfest.walker.geofence.GeofenceUtils;
import gdgdevfest.walker.geofence.SimpleGeofence;
import gdgdevfest.walker.geofence.SimpleGeofenceStore;

public class ActivityAuto extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private GeofenceUtils.REQUEST_TYPE mRequestType;
    private GeofenceUtils.REMOVE_TYPE mRemoveType;

    private GeofenceSampleReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private SimpleGeofenceStore mPrefs;
    private ArrayList<Geofence> mCurrentGeofences;

    private GeofenceRequester mGeofenceRequester;
    private GeofenceRemover mGeofenceRemover;
    private List<String> mGeofenceIdsToRemove;
    private UUID DRIVER_ID;
    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();

        InitGeofences();

        AddInitialGeofence();

        setContentView(R.layout.activity_auto);
    }

    private void AddInitialGeofence() {
        mRequestType = GeofenceUtils.REQUEST_TYPE.ADD;

        if (!servicesConnected()) return;

        Location currentLocation = GetCurrentLocation();

        float fiveKms = 5000;
        SimpleGeofence geofence = new SimpleGeofence(
                DRIVER_ID.toString(),
                currentLocation.getLatitude(),
                currentLocation.getLongitude(),
                fiveKms,
                0,
                Geofence.GEOFENCE_TRANSITION_EXIT);

        // Store this flat version in SharedPreferences
        mPrefs.setGeofence("1", geofence);

        mCurrentGeofences.add(geofence.toGeofence());

    }

    private Location GetCurrentLocation() {

        Location lastLocation = mLocationClient.getLastLocation();

        Log.d(GeofenceUtils.APPTAG, "location");
        Log.d(GeofenceUtils.APPTAG, lastLocation.toString());

        return lastLocation;
    }

    private boolean servicesConnected() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            Log.d(GeofenceUtils.APPTAG, "service connected");

            return true;

        } else {
            Log.d(GeofenceUtils.APPTAG, "service not availible");
            Toast.makeText(this, "service not availible", Toast.LENGTH_LONG).show();

            return false;
        }
    }


    private void InitGeofences() {

        DRIVER_ID = UUID.randomUUID();

        mBroadcastReceiver = new GeofenceSampleReceiver();

        mIntentFilter = new IntentFilter();

        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_ADDED);
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);
        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);

        mIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);

        mPrefs = new SimpleGeofenceStore(this);

        mCurrentGeofences = new ArrayList<Geofence>();

        mGeofenceRequester = new GeofenceRequester(this);
        mGeofenceRemover = new GeofenceRemover(this);
    }

    /*
 * Handle results returned to this Activity by other Activities started with
 * startActivityForResult(). In particular, the method onConnectionFailed() in
 * GeofenceRemover and GeofenceRequester may call startResolutionForResult() to
 * start an Activity that handles Google Play services problems. The result of this
 * call returns here, to onActivityResult.
 * calls
 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {

            case GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    case Activity.RESULT_OK:

                        if (GeofenceUtils.REQUEST_TYPE.ADD == mRequestType) {

                            // Toggle the request flag and send a new request
                            mGeofenceRequester.setInProgressFlag(false);

                            // Restart the process of adding the current geofences
                            mGeofenceRequester.addGeofences(mCurrentGeofences);

                            // If the request was to remove geofences
                        } else if (GeofenceUtils.REQUEST_TYPE.REMOVE == mRequestType ){

                            // Toggle the removal flag and send a new removal request
                            mGeofenceRemover.setInProgressFlag(false);

                            // If the removal was by Intent
                            if (GeofenceUtils.REMOVE_TYPE.INTENT == mRemoveType) {

                                // Restart the removal of all geofences for the PendingIntent
                                mGeofenceRemover.removeGeofencesByIntent(
                                        mGeofenceRequester.getRequestPendingIntent());

                                // If the removal was by a List of geofence IDs
                            } else {

                                // Restart the removal of the geofence list
                                mGeofenceRemover.removeGeofencesById(mGeofenceIdsToRemove);
                            }
                        }
                        break;

                    // If any other result was returned by Google Play services
                    default:

                        // Report that Google Play services was unable to resolve the problem.
                        Log.d(GeofenceUtils.APPTAG, getString(R.string.no_resolution));
                }

                // If any other request code was received
            default:
                // Report that this Activity received an unknown requestCode
                Log.d(GeofenceUtils.APPTAG,
                        getString(R.string.unknown_activity_request_code, requestCode));

                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public class GeofenceSampleReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {

                handleGeofenceError(context, intent);

            } else if (
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_ADDED)
                            ||
                            TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_REMOVED)) {

                handleGeofenceStatus(context, intent);

            } else if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {

                handleGeofenceTransition(context, intent);

                // The Intent contained an invalid action
            } else {
                Log.e(GeofenceUtils.APPTAG, getString(R.string.invalid_action_detail, action));
                Toast.makeText(context, R.string.invalid_action, Toast.LENGTH_LONG).show();
            }
        }

        /**
         * If you want to display a UI message about adding or removing geofences, put it here.
         *
         * @param context A Context for this component
         * @param intent The received broadcast Intent
         */
        private void handleGeofenceStatus(Context context, Intent intent) {

        }

        /**
         * Report geofence transitions to the UI
         *
         * @param context A Context for this component
         * @param intent The Intent containing the transition
         */
        private void handleGeofenceTransition(Context context, Intent intent) {
            Toast.makeText(context, "got it", Toast.LENGTH_LONG).show();
        }

        /**
         * Report addition or removal errors to the UI, using a Toast
         *
         * @param intent A broadcast Intent sent by ReceiveTransitionsIntentService
         */
        private void handleGeofenceError(Context context, Intent intent) {
            String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
            Log.e(GeofenceUtils.APPTAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}

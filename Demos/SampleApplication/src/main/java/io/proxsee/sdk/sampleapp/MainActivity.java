package io.proxsee.sdk.sampleapp;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

import io.proxsee.sdk.ProxSeePermissionManager;
import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.broadcast.ProxSeeBroadcastReceiver;
import io.proxsee.sdk.broadcast.ProxSeeBroadcaster;
import io.proxsee.sdk.model.BeaconNotificationObject;


public class MainActivity extends ActionBarActivity {
    private LinearLayout layout;
    private ProxSeeBroadcastReceiver proxSeeBroadcastReceiver;

    private ProxSeePermissionManager proxSeePermissionManager = new ProxSeePermissionManager();
    private static final int PROXSEE_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.layout);

        this.proxSeeBroadcastReceiver = new ProxSeeBroadcastReceiver() {
            @Override
            public void didChangeTagsSet(BeaconNotificationObject beaconNotificationObject) {
                Set<String> newTags = beaconNotificationObject.getCurrentTagsChangedSet().getTags();
                Set<String> oldTags = beaconNotificationObject.getCurrentTagsChangedSet().getTags();

                for (String tag : newTags) {
                    if (!oldTags.contains(tag)) onTagDetected(tag);
                }

                for (String tag : oldTags) {
                    if (!newTags.contains(tag)) onTagLost(tag);
                }
            }
        };

        registerReceiver(this.proxSeeBroadcastReceiver, new IntentFilter(ProxSeeBroadcaster.TAGS_CHANGED_ACTION));


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!proxSeePermissionManager.isPermissionGranted(this)) {
            requestPermissions(proxSeePermissionManager.requiredPermissions(), PROXSEE_PERMISSIONS_REQUEST);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.proxSeeBroadcastReceiver);
    }



    public void onTagDetected(String tag) {
        addMessage("Found tag: " + tag);
    }

    public void onTagLost(String tag) {
        addMessage("Lost tag: " + tag);
    }

    private void addMessage(String text){
        TextView valueTV = new TextView(this);
        valueTV.setText(text);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        layout.addView(valueTV);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PROXSEE_PERMISSIONS_REQUEST: {
                boolean isPermissionGranted = true;
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        isPermissionGranted = false;
                        break;
                    }
                }

                ProxSeeSDKManager sdkManager = ProxSeeSDKManager.getInstance();
                if (isPermissionGranted) {
                    if (!sdkManager.isStarted()) {
                        sdkManager.start();
                    }
                 }else {
                    sdkManager.stop();
                }
                break;

            }default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
}

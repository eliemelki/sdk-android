package io.proxsee.sdk.sampleapp;

import android.app.Application;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.broadcast.ProxSeeBroadcastReceiver;
import io.proxsee.sdk.broadcast.ProxSeeBroadcaster;
import io.proxsee.sdk.model.BeaconNotificationObject;

/**
 * Created by Ahmad Shami on 4/15/15.
 */
public class BaseApplication extends Application implements ProxSeeSDKManager.CompletionHandler{

    private final static String API_KEY = "YOUR_API_KEY";

    @Override
    public void onCreate() {
        super.onCreate();

        this.initialiseProxsee();
        this.updateMetadata();
    }



    @Override
    public void onUpdateCompleted(boolean success, Exception error) {
        if(success){
            Toast.makeText(this, "Metadata has been updated successfully!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error has occured: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initialiseProxsee() {
        ProxSeeSDKManager.initialize(this, API_KEY);
        registerReceiver(new ProxSeeBroadcastReceiver() {
            @Override
            public void didChangeTagsSet(BeaconNotificationObject beaconNotificationObject) {
                String previousTags = TextUtils.join(",", beaconNotificationObject.getPreviousTagsChangedSet().getTags());
                String currentTags = TextUtils.join(",", beaconNotificationObject.getCurrentTagsChangedSet().getTags());

                Toast.makeText(BaseApplication.this, String.format("Previous tags: " + previousTags), Toast.LENGTH_SHORT).show();
                Toast.makeText(BaseApplication.this, String.format("Current tags: " + currentTags), Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter(ProxSeeBroadcaster.TAGS_CHANGED_ACTION));
    }

    private void updateMetadata() {
        // Update metadata
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("key", "value");
        ProxSeeSDKManager.getInstance().updateMetadata(metadata, this);

        Toast.makeText(this, "Application started", Toast.LENGTH_SHORT).show();
    }
}

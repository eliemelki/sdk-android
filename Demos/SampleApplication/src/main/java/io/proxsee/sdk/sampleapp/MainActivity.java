package io.proxsee.sdk.sampleapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Set;

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.events.receiver.TagsReceiver;
import io.proxsee.sdk.main.BeaconsManager;
import io.proxsee.sdk.main.MetadataManager;
import io.proxsee.sdk.main.TagsManager;
import io.proxsee.sdk.model.ProxSeeBeacon;
import io.proxsee.sdk.model.Tags;
import io.proxsee.sdk.permissions.PermissionManager;


public class MainActivity extends ActionBarActivity {
    private LinearLayout layout;
    private TagsReceiver tagsReceiver;

    private PermissionManager permissionManager = new PermissionManager();
    private static final int PROXSEE_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.layout);

        this.tagsReceiver = new TagsReceiver() {
            @Override
            public void didChangeTagsSet(Tags tags) {
                Set<String> newTags = tags.getCurrentTagsChangedSet().getTags();
                Set<String> oldTags = tags.getCurrentTagsChangedSet().getTags();

                for (String tag : newTags) {
                    if (!oldTags.contains(tag)) onTagDetected(tag);
                }

                for (String tag : oldTags) {
                    if (!newTags.contains(tag)) onTagLost(tag);
                }
            }
        };
        TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.registerReceiver(this.tagsReceiver);


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!permissionManager.isPermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionManager.requiredPermissions(), PROXSEE_PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.unregisterReceiver(this.tagsReceiver);
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
                    if (!sdkManager.isEnabled()) {
                        sdkManager.enable();
                    }
                 }else {
                    sdkManager.disable();
                }
                break;

            }default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
}

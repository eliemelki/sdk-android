package io.proxsee.sdk.sampleapp;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.events.receiver.TagsReceiver;
import io.proxsee.sdk.main.MetadataManager;
import io.proxsee.sdk.main.TagsManager;
import io.proxsee.sdk.model.Tags;


public class BaseApplication extends Application {

    private final static String API_KEY = "YOUR_API_KEY";

    @Override
    public void onCreate() {
        super.onCreate();

        this.initialiseProxsee();
        this.updateMetadata();
    }




    private void initialiseProxsee() {
        ProxSeeSDKManager.initialize(this, API_KEY);
        TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();

        tagsManager.registerReceiver(new TagsReceiver() {
            @Override
            public void didChangeTagsSet(Tags tags) {
                String previousTags = TextUtils.join(",", tags.getPreviousTagsChangedSet().getTags());
                String currentTags = TextUtils.join(",", tags.getCurrentTagsChangedSet().getTags());

                Toast.makeText(BaseApplication.this, String.format("Previous tags: " + previousTags), Toast.LENGTH_SHORT).show();
                Toast.makeText(BaseApplication.this, String.format("Current tags: " + currentTags), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMetadata() {
        // Update metadata
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("key", "value");
        ProxSeeSDKManager.getInstance().getMetadataManager().updateMetadata(metadata, new MetadataManager.UpdateMetadataCallBack() {
            @Override
            public void onComplete(boolean success, Exception exception) {
                if(success){
                    Toast.makeText(BaseApplication.this, "Metadata has been updated successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BaseApplication.this, "Error has occured: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

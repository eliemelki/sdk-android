# Migration guide to ProxSee SDK 3.0.0


Please follow this guide to upgrade to 3.0.0

## ProxSeeSDKManager api changes

### Changed
- `isStarted` has changed to `isEnabled`
- `start` has changed to `enable`
- `stop` has changed to `disable`
- `updateMetadata` and its callback `ProxSeeSDKManager.CompletionHandler` has been removed. You can now use  `getMetadataManager()` instead to update metadata.
- `fetchDetectedBeacons` and its callback `ProxSeeSDKManager.DetectedBeaconsCallBack` has been removed. You can now use `getBeaconsManager()` to get detected beacons.
- `fetchDeviceId` and its callback `ProxSeeSDKManager.DeviceIdCallBack` has been removed. You can now use `getDataManager()` to get device identifier.


### Added
- `getTagsManager()` is added which returns `TagManager` object. You query `registerReceiver(TagsReceiver receiver)`/`void unregisterReceiver(TagsReceiver receiver)` on `TagManager` to register/unregister for tags. 
- `getBeaconsManager()` is added which returns `BeaconsManager` object. You query `getDetectedBeacons(GetDetectedBeaconsCallBack callBack)` on `BeaconsManager` to get detected beacons. 
- `getDataManager()` is added which returns `DataManager` object. You query `getIdentifier(GetIdentiferCallback callback)`  on `DataManager` to get data such as Identifier.
- `getMetadataManager()` is added which returns `MetadataManager` object. You query `updateMetadata(final HashMap<String, Object> metadata,  UpdateMetadataCallBack callBack)` on `MetadataManager` to update metadata.

## Register for tags changes
In earlier versions we used BroadcastReceiver and context.register/context.unregister to start listening for tags. An example 

```

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.model.BeaconNotificationObject;

....

public class MainActivity extends Activity {
	private ProxSeeBroadcastReceiver proxSeeBroadcastReceiver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proxSeeBroadcastReceiver = new ProxSeeBroadcastReceiver() {
            @Override
            public void didChangeTagsSet(BeaconNotificationObject beaconNotificationObject) {
                // do desired action
            }
        };

 		registerReceiver(proxSeeBroadcastReceiver,new IntentFilter(ProxSeeBroadcaster.TAGS_CHANGED_ACTION));
    }

    @Override
    public void onDestroy() {
        onDestroy();
        unregisterReceiver(proxSeeBroadcastReceiver);
    }
}
```

Now, you can no longer use `Context` and `BroadcastReceiver` to do that. Instead, you query `ProxSeeSDKManager.getInstance().getTagsManager();`
                                                                                                       
- `BeaconNotificationObject` is removed and we use ``Tags`` found in `io.proxsee.sdk.model`
- `ProxSeeBroadcastReceiver` is changed to `TagsReceiver` found in `io.proxsee.sdk.events.receiver`

An example

```
import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.model.Tags;
import io.proxsee.sdk.events.receiver;

public class MainActivity extends Activity {
	private TagsReceiver proxSeeBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proxSeeBroadcastReceiver = new TagsReceiver() {
            @Override
            public void didChangeTagsSet(Tags tags) {
                // do desired action
            }
        };

 		TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.registerReceiver(proxSeeBroadcastReceiver);
    }

    @Override
    public void onDestroy() {
        onDestroy();
        TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.unregisterReceiver(proxSeeBroadcastReceiver);
    }
}
```


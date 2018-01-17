# ProxSee SDK for Android

The following document provides background information on the ProxSee SDK as well as outlines setup and usage instructions.

The content in this document is divided into the following sections:

- [Section 1: Introducing the ProxSee SDK](#section-1-introducing-the-proxsee-sdk)
    - [Background](#background)
    - [How Does the ProxSee SDK Work?](#how-does-the-proxsee-sdk-work)
    - [Key Concepts](#key-concepts)
        - [Beacon](#beacon) 
        - [Virtual Beacon](#virtual-beacon)
        - [Locations](#locations)
        - [Tags](#tags)
        - [Metadata](#metadata)
        - [Check-In/Check-Out](#check-incheck-out)
- [Section 2: Implementing the ProxSee SDK in an Android Project](#section-2-implementing-the-proxsee-sdk-in-an-android-project)
    - [Prerequisites](#prerequisites)
    - [Generate a Mobile API Key](#generate-a-mobile-api-key)
    - [Integrate the ProxSee SDK into Your Android Project](#integrate-the-proxsee-sdk-into-your-android-project)
    - [Complete ProGuard Configuration](#complete-proguard-configuration)
    - [Launch the ProxSee SDK](#launch-the-proxsee-sdk)
- [Section 3: Using the ProxSee SDK](#section-3-using-the-proxsee-sdk)
    - [Handle Tag Changeset Notifications](#handle-tag-changeset-notifications)
    - [Enable/Disable the ProxSee SDK](#enabledisable-the-proxsee-sdk)
        - [Determine the State of the ProxSee SDK](#determine-the-state-of-the-proxsee-sdk)
        - [Enable the ProxSee SDK](#enable-the-proxsee-sdk)
        - [Disable the ProxSee SDK](#disable-the-proxsee-sdk)
    - [Check and Enable Permissions at Runtime](#check-and-enable-permissions-at-runtime)
    - [Update Metadata](#update-metadata)
    - [Get Detected Beacons](#get-detected-beacons)
    - [Get Device ID](#get-device-id)
- [Section 4: FAQs](#section-4-faqs)
 
## Section 1: Introducing the ProxSee SDK

### Background

The ProxSee SDK takes the complexities out of beacon interaction and provides you with a simplified interface to quickly integrate iBeacon™ and virtual beacon (geo-fence) monitoring into your mobile application.

Combined with the ProxSee Admin Portal, the ProxSee SDK allows you to create and manage tags; listen for, receive, and respond to tag changeset notifications; add and associate user metadata to check-ins; and mine resultant data according to your needs (e.g., to determine wait times, travel patterns).

### How Does the ProxSee SDK Work? 

Once initialized, the ProxSee SDK associates a unique identifier with the user's mobile device, which is used in all communications to the central platform. The ProxSee SDK starts monitoring beacons/virtual beacons and sends check-in/check-out information to the central platform whenever an enter or exit event is detected:

- **Enter event**: The user approaches the beacon or enters the virtual beacon (geo-fence) circular boundary. The ProxSee SDK sends check-in information to the central platform when an enter event is detected.
- **Exit event**: The user moves away from the beacon or exits the virtual beacon (geo-fence) circular boundary. The ProxSee SDK sends check-out information to the central platform when an exit event is detected.  

Along with monitoring the beacons/virtual beacons, the ProxSee SDK also queries the central platform for tag information associated with a beacon/virtual beacon and automatically loads and caches information about nearby beacons/virtual beacons.

The ProxSee SDK allows your application to:

- **Listen For and Receive Tag Changeset Notifications**: Your application can listen for and receive tag changeset notifications sent by the ProxSee SDK. You can update the tags and positional information associated to a beacon/virtual beacon through the ProxSee Admin Portal without having to update your ProxSee SDK or the physical, deployed beacons. See [Handle Tag Changeset Notifications](#handle-tag-changeset-notifications).
- **Enable/Disable the ProxSee SDK**: The ProxSee SDK monitors beacons/virtual beacons, broadcasts check-ins/check-outs, sends tag changeset notifications, and updates metadata. At any point in your application, you can enable/disable the ProxSee SDK, which starts/stops monitoring. See [Enable/Disable the ProxSee SDK](#enabledisable-the-proxsee-sdk).
- **Update Metadata**: You can send additional information about a user such as account information and user IDs to the ProxSee SDK. When the ProxSee SDK receives metadata, it associates it with the user's check-ins, which helps you identify users and devices among the collected data. See [Update Metadata](#update-metadata).
- **Get Detected Beacons**: Any time in the application lifecycle after initialization, you can get all detected beacons. See [Get Detected Beacons](#get-detected-beacons).

### Key Concepts

#### Beacon

Also referred to as a "physical beacon" or an "iBeacon™", this is the physical device that you deploy and that the user's mobile device detects. Unlike location-based services on a mobile device, beacon ranging is fairly precise (essentially serving as "indoor GPS") and low-power, leading to its use indoors and where fine-tuned location context is desired.

In general, the closer you are to a beacon the more accurate the reported distance. Because of the factors mentioned above, it’s not possible to provide specific numbers for accuracy. On average, the measurement error can be 20-30% of the actual distance. You can increase signal reliability by increasing the Broadcasting Power.

#### Virtual Beacon

A virtual beacon is a geo-fence that acts like a broad-ranging physical beacon, but is based on the user crossing a circular boundary on a map as opposed to nearing a physical beacon. As such, a virtual beacon can serve as a less accurate beacon in locations where the customer may not have access/permission to add a physical beacon. 

The accuracy of a geo-fence is based on GPS and the network provider. The ProxSee SDK uses PRIORITY_BALANCED_POWER_ACCURACY which provides up to 100 meters accuracy. The ProxSee SDK is also expected to receive a location update within 2 to 5 minutes.

#### Locations

Locations within the ProxSee platform group beacons/virtual beacons by region and establish a default tag for each beacon/virtual beacon within the region. Locations also play a part in the caching of beacon data.

To add/delete locations, see the Locations section of the ProxSee Admin Portal. 

#### Tags

Tags are short descriptions in the form of hashtags that are associated to beacons/virtual beacons for identification and classification purposes.

By default, each beacon/virtual beacon has a “#<Location>” tag (with <Location> being the location of the beacon/virtual beacon). 

You can associate the same tag with multiple beacons/virtual beacons (for instance, you may place beacons at all of your exits and associate them all with an  "#Exit" tag) or you may choose to not specify any tags at all. 

Whereas, whenever a beacon/virtual beacon is seen (or last seen) a check-in/check-out will occur, if a user moves between two beacons/virtual beacons with the same tags, it will not result in a new notification in the ProxSee SDK. Your listening application will only be notified when the tags change (either tags were removed or they were added).

To add and assign/remove tags, see the Tags section of the ProxSee Admin Portal. 

#### Metadata

Metadata can be used to provide additional information about a user and their device, such as user ID and user preferences. The metadata sent to the ProxSee SDK is then associated to each check-in by the user’s device.

See the [Update Metadata](#update-metadata) section in this document for instructions on how to add metadata.  

#### Check-In/Check-Out

A check-in/check-out forms a tuple for a device event and helps track enter and exit events.  
When tracking enter (check-in) and exit (check-out) events, keep in mind, you may have a check-in without a corresponding check-out.   

Data is stored during both a check-in and a check-out.

- **Check-in**: The majority of information is stored during a check-in and includes: 
    - the time
    - the device's unique ID (UUID)
    - additional system information, including the version of the ProxSee SDK used 
- **Check-out**: Only the check-out time is updated and stored

### Section 2: Implementing the ProxSee SDK in an Android Project

Incorporating the ProxSee SDK into your Android project is a simple four-step process:

- [Generate a Mobile API Key](#generate-a-mobile-api-key)
- [Integrate the ProxSee SDK into Your Android Project](#integrate-the-proxsee-sdk-into-your-android-project)
- [Complete ProGuard Configuration](#complete-proguard-configuration)
- [Launch the ProxSee SDK](#launch-the-proxsee-sdk)

### Prerequisites

The ProxSee SDK requires:

- Minimim Proxsee SDK version 9
- Beacon detection, only available starting at OS 5
- An active Bluetooth service in order to function with beacons/virtual beacons
- Active Location services in order to function with virtual beacons
- An Internet connection for the initial run in order to register the device. Note that once the device has been registered, offline support is available. When offline:
    - Check-ins/check-outs will be saved and sent when the Internet connection is restored. 
    - Tags will be unavailable, unless the tags are available in cache. Note: Cached tags may be out-of-date.  

### Generate a Mobile API Key

In order to use the ProxSee SDK, you will need to generate a Mobile API Key.

1. Navigate to the ProxSee Admin Portal at [https://app.proxsee.io/#/login](#https://app.proxsee.io/#/login).
2. From the login page, enter your username and password and then click **Login**.
3. From the navigation bar on the left side of the screen, click **Applications**.
4. Click **Create Application**.
5. From the **API Type** section, select **Mobile**.
6. Copy the generated GUID. This is your Mobile API Key. 

**Note**: If you have multiple applications, you may wish to generate a unique Mobile API Key for each one.

### Integrate the ProxSee SDK into Your Android Project

The Proxsee SDK can be integrated into your Android project using Gradle build script on Android Studio (easiest and most recommended).

#### Assumptions

- You have successfully installed Android Studio
- You have created a new project or are integrating with an existing project
 

To integrate the ProxSee SDK into your Android project using Gradle build script on Android Studio:

1. Click the **Project** tab.
2. Expand the **Gradle Scripts** node.
3. Open **build.gradle (Module: app)**.
4. In the dependencies section of your project’s build.gradle, add the following:


    ```
    compile 'io.proxsee:proxsee-sdk:SDK_VERSION'  #latest released version will be found on http://mvnrepository.com/artifact/io.proxsee/proxsee-sdk


    ```
5. From the main toolbar, click the **Sync Project with Gradle Files** button.


At this point, the ProxSee SDK is ready to use and your project can compile successfully. 

### Complete ProGuard Configuration

If ProGuard is used for obfuscating the source code, no need to specify ProxSee ProGuard rules as we ship the SDK along with a consumer ProGuard.

### Launch the ProxSee SDK

On the application onCreate, initialize the ProxSee SDK with the Mobile API Key you generated in a previous step. See [Generate a Mobile API Key](#generate-a-mobile-api-key). 


On the initial launch, once initialized, the ProxSee SDK will be ON by default and will start automatically. When your application is restarted followed by a call to initialize, the ProxSee SDK will attempt to start depending on the SDK state. Calling to initialize more than once has no effect, unless the Mobile API Key has changed. 


**Note**: In the following code, replace “YourApiKey” with the Mobile API Key you generated in a previous step.

```
import io.proxsee.sdk.ProxSeeSDKManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Need to replace YourApiKey with a real one
        ProxSeeSDKManager.initialize(this, "YourApiKey");
    }
}


```


## Section 3: Using the ProxSee SDK

The following actions can be performed within the ProxSee SDK:

- [Handle Tag Changeset Notifications](#handle-tag-changeset-notifications)
- [Enable/Disable the ProxSee SDK](#enabledisable-the-proxsee-sdk)
- [Check and Enable Permissions at Runtime](#check-and-enable-permissions-at-runtime)
- [Update Metadata](#update-metadata)
- [Get Detected Beacons](#get-detected-beacons)

### Handle Tag Changeset Notifications

The ProxSee SDK broadcasts all tag changes. To start listening for changes, register ```ProxSeeTagsBroadcastReceiver``` and override the ```didChangeTagsSet``` method. The ProxSee SDK will send a ```Tags``` which includes the new and previous tag changesets along with the date captured for each changeset.

Example:

```
import android.app.Activity;

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.model.Tags;
import io.proxsee.sdk.events.receiver;

public class MainActivity extends Activity {
	private ProxSeeTagsBroadcastReceiver TagsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TagsReceiver = new ProxSeeTagsBroadcastReceiver() {
            @Override
            public void didChangeTagsSet(Tags tags) {
                // do desired action
            }
        };

 		TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.registerReceiver(TagsReceiver);
    }

    @Override
    public void onDestroy() {
        onDestroy();
        TagsManager tagsManager = ProxSeeSDKManager.getInstance().getTagsManager();
        tagsManager.unregisterReceiver(TagsReceiver);
    }
}
```

### Enable/Disable the ProxSee SDK

At any point of the application lifecycle you can enable or disable the ProxSee SDK. Stopping the ProxSee SDK will stop beacon/virtual beacon monitoring, check-in/check-out broadcasts, tag changeset notifications, and metadata updates. 

Any explicit call to enable or disable the ProxSee SDK will change the SDK state accordingly. 

#### Determine the State of the ProxSee SDK

To determine if the ProxSee SDK is enabled or disabled:


```
ProxSeeSDKManager.getInstance().isEnabled();


```
#### Enable the ProxSee SDK 

To enable the ProxSee SDK and in turn start the monitoring of beacons/virtual beacons, check-in/check-out broadcasts, tag changeset notifications, and metadata updates:

```
ProxSeeSDKManager.getInstance().enable();


```

#### Disable the ProxSee SDK

To disable the ProxSee SDK and in turn stop the monitoring of beacons/virtual beacons, check-in/check-out broadcasts, tag changeset notifications, and metadata updates:

```
ProxSeeSDKManager.getInstance().disable();


```
### Check and Enable Permissions at Runtime

If you are targeting Android API 23 and above, you will need to check and enable permissions at runtime. 

The ProxSee SDK requires **either** of the following permissions to operate:

- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION 

The ProxSee SDK also has the ability to pick up permission changes and resume/pause automatically depending on the SDK state without an explicit call to disable/enable.

The following sample demonstrates how to request permissions in your activity. The sample below checks permissions on start, but you may modify it as suitable for your project. For that purpose, you can use the PermissionManager.

```
import io.proxsee.sdk.permissions.PermissionManager;


private static final int PROXSEE_PERMISSIONS_REQUEST = 1;
private PermissionManager proxSeePermissionManager = new PermissionManager();

@Override
protected void onStart() {
    super.onStart();
        super.onStart();
        if (!permissionManager.isPermissionGranted(this)) {
             requestPermissions(permissionManager.requiredPermissions(), PROXSEE_PERMISSIONS_REQUEST);
        }

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
                break;
       }
       default:
           super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}

```

### Update Metadata

At any point in the application lifecycle you can update metadata. See the sample below for an example.

```
HashMap<String, Object> metadata = new HashMap<String, Object>();
metadata.put("key", "value");
ProxSeeSDKManager instance = ProxSeeSDKManager.getInstance();
instance.getMetadataManager().updateMetadata(metadata, new MetadataManager.UpdateMetadataCallBack() {
    @Override
    public void onComplete(boolean success, Exception exception) {
                
    }
});


```

### Get Detected Beacons

Any time in the application lifecycle after initialization, you can execute the following code to get all detected beacons.

```
ProxSeeSDKManager instance = ProxSeeSDKManager.getInstance();
instance.getBeaconsManager().getDetectedBeacons(new BeaconsManager.GetDetectedBeaconsCallBack() {
      @Override
      public void onComplete(Collection<ProxSeeBeacon> detectedBeacons) {
      }
});

```


### Get Device ID

Any time in the application lifecycle after initialization, you can execute the following code to get device id that uniquely identifies your app in the ProxSee system.

```
ProxSeeSDKManager.getInstance().getDataManager().getIdentifier(GetIdentiferCallback {
     @Override
     void onComplete(String id)

    }
});    

```


## Section 4: FAQs

**Will the ProxSee SDK impact my mobile device’s battery?** 

Yes. The ProxSee SDK will draw approximately 1-2% of the mobile device’s battery. 

**How long does it take the ProxSee SDK to detect a beacon?**

- **Beacons**: 0 to a few seconds
- **Virtual beacons**: 2.5 to 5 minutes

**As a third-party developer using the ProxSee SDK, do I need to do anything if my application is rebooted?**

Refer to the [Launch the ProxSee SDK](#launch-the-proxsee-sdk) section for details.

**What happens when Bluetooth is disabled?**

Scanning for physical beacons is paused while scanning for virtual beacons will continue. Once Bluetooth is re-enabled, scanning for physical beacons will resume. Note: The ProxSee SDK must have monitoring enabled in order to receive events.

**What happens when Location services are disabled?**

The ProxSee SDK needs to be enabled to receive events. Assuming the ProxSee SDK is enabled, if Location services are disabled, the detection of virtual beacons will be paused. It is noted that on Nexus 5 if Location services are disabled, scanning for physical beacons will be paused as well. Once Location services are re-enabled, the detection of virtual beacons will resume.

**What happens when Location permissions are disabled?**

On Android 6, permissions can be enabled/disabled. When you disable Location permissions the OS will restart the application. Assuming the ProxSee SDK is enabled, it will resume, but the monitoring of beacons and virtual beacons will be paused. Once Location permissions are re-enabled, the monitoring of beacons and virtual beacons will be resumed. 

**How long does it take the ProxSee SDK to confirm a check-out for a beacon?**

- **Physical Beacons**: The time it takes the ProxSee SDK to confirm a check-out for a physical beacon.
    - **Background/Foreground**:  3 minutes.
- **Virtual Beacons**: As a general rule, the ProxSee SDK fetches the mobile device's location every 2.5 to 5 minutes. Whenever the mobile device's location is updated, the ProxSee SDK checks to see if the updated location is within the boundary of a virtual beacon. If the location had previously been within the boundary of a virtual beacon but is no longer, a check-out is directly sent. Refer to the [Virtual Beacon](#virtual-beacon) section for more details.

**How long does it take to for beacons that have just been installed to reach the ProxSee SDK?**

Starting SDK version 3.0.1 both virtual and physical beacons will be updated as follow: 
- Assuming the SDK was enabled, on each app initial launch.
- Each time the SDK has been enabled after it was disabled. 
- For any beacon detection wether virtual or physical, the list will be refreshed only after 24 hours from the last successful call.

For earlier versions the below apply:
- **Physical Beacons**: Once a beacon is installed, if the ProxSee SDK is detected and the mobile device is not nearby, the ProxSee SDK should directly detect it. Note, in the case where you are installing a beacon next to you while installing the ProxSee SDK, a tag and/or check-in may be missed depending on whether the the installation of the ProxSee SDK or the detection of the beacon finishes first. 
- **Virtual Beacons**: Once a virtual beacon has been installed and you are not within its boundaries, any location event (e.g., 5 minutes has elapsed) will update the data and allow the ProxSee SDK to detect it once the mobile device is within the boundary of the virtual beacon. Note, if you are installing the virtual beacon while within the boundary of the beacon while installing the ProxSee SDK, a tag and/or check-in may be missed depending on whether the installation of the ProxSee SDK or the detection of the virtual beacon finishes first. 

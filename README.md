# ProxSee SDK for Android

The following document provides background information on the ProxSee SDK as well as outlines setup and usage instructions.
 
The content in this document is divided into the following sections:
 
- [Section 1: Introducing the ProxSee SDK](#section-1-introducing-the-proxsee-sdk)
    - [Background](#background)
    - [How Does the ProxSee SDK Work?](#how-does-the-proxsee-sdk-work)
    - [Key Concepts](#key-concepts)
        - [Beacon](#beacon) 
        - [Virtual Beacon](#virtual-beacon)
            - [Deployment](#deployment)
            - [Limitations](#limitations)
            - [Accuracy](#accuracy)
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
    - [Start/Stop the ProxSee SDK](#startstop-the-proxsee-sdk)
        - [Determine the State of the ProxSee SDK](#determine-the-state-of-the-proxsee-sdk)
        - [Start the ProxSee SDK](#start-the-proxsee-sdk)
        - [Stop the ProxSee SDK](#stop-the-proxsee-sdk)
    - [Check and Enable Permissions at Runtime](#check-and-enable-permissions-at-runtime)
    - [Update Metadata](#update-metadata)
- [Section 4: FAQs](#section-4-faqs)
 
## Section 1: Introducing the ProxSee SDK

### Background
 
The ProxSee SDK takes the complexities out of beacon interaction and provides you with a simplified interface to quickly integrate iBeacon™ and virtual beacon (geo-fence) monitoring into your mobile application.
 
Combined with the ProxSee Admin Portal, the ProxSee SDK allows you to create and manage tags, listen for, receive, and respond to tag changeset notifications, add and associate user metadata to check-ins, and mine resultant data according to your needs (e.g., to determine wait times, travel patterns).
 
### How Does the ProxSee SDK Work? 
 
Once initialized, the ProxSee SDK associates a unique identifier with the user's mobile device, which is used in all communications to the central platform. The ProxSee SDK starts monitoring beacons/virtual beacons and sends check-in/check-out information to the central platform whenever an enter or exit event is detected:
 
- **Enter event**: The user approaches the beacon or enters the virtual beacon (geo-fence) circular boundary. The ProxSee SDK sends check-in information to the central platform when an enter event is detected.
- **Exit event**: The user moves away from the beacon or exists the virtual beacon (geo-fence) circular boundary. The ProxSee SDK sends check-out information to the central platform when an exit event is detected.

Along with monitoring the beacons/virtual beacons, the ProxSee SDK also queries the central platform for tag information associated with a beacon/virtual beacon and automatically loads and caches information about nearby beacons/virtual beacons.
 
The ProxSee SDK allows your application to:
 
- **Listen For and Receive Tag Changeset Notifications**: Your application can listen for and receive tag changeset notifications sent by the ProxSee SDK. You can update the tags and positional information associated to a beacon/virtual beacon through the ProxSee Admin Portal without having to update your ProxSee SDK or the physical, deployed beacons. See [Handle Tag Changeset Notifications](#handle-tag-changeset-notifications). 
- **Start/Stop the ProxSee SDK**: The ProxSee SDK monitors beacons/virtual beacons, broadcasts check-ins/check-outs, send tag changeset notifications, and update metadata. At any point in your application, you can start/stop the ProxSee SDK, which turns on/off monitoring. See [Start/Stop the ProxSee SDK](#startstop-the-proxsee-sdk). 
- **Update Metadata**: You can add additional information about a user such as account information and user IDs. When the ProxSee SDK receives metadata it associates it with the user's check-ins, which helps you identify users and devices among the collected data. See [Update Metadata](#send-update-metadata).

### Key Concepts
 
#### Beacon
 
Also referred to as a "physical beacon" or an "iBeacon™", this is the physical device that you deploy and that the user's mobile device detects. Unlike location-based services on a mobile device, beacon ranging is fairly precise (essentially serving as "indoor GPS") and low-power, leading to its use indoors and where fine-tuned location context is desired.
 
#### Virtual Beacon
 
A virtual beacon is a geo-fence that behaves  based on the user crossing a circular boundary on a map rather than nearing a physical beacon. s such, it can serve as a less accurate beacon in locations the customer may not have the access/permission to add a physical device. 
 
Virtual beacons work in concert with physical beacons. In order to work properly, virtual beacons should be placed on the map in such a way that a user would hit a physical beacon before hitting a virtual beacon. For example:
 
- **Bad Placement**: Putting a virtual beacon in the parking lot of a mall and a physical beacon inside the mall would not allow you to detect people approaching from the parking lot.  The ProxSee SDK would send notifications of users hitting the beacon in the mall first, not in the parking lot. 
- **Good Placement**: Putting a virtual beacon on Rent a Car Road in Las Vegas and a physical beacon inside the arrival area of the Las Vegas airport would allow you to detect people coming off of an airplane and then detect those that went to the rental car area. 

###### Deployment
 
- For best results, virtual beacons should be deployed with a medium or greater range.
- Virtual beacons should be placed in areas where a user is likely to remain or traverse for several seconds/minutes.

##### Limitations
 
- You are limited to 5 virtual beacons per location.
- The outer radius of a virtual beacon must be more than 200 meters in distance from the outer radius of any other beacon (physical or virtual).

##### Accuracy 
 
The accuracy of virtual beacons is based on the GPS/network provider; however, the following factors may affect the accuracy of a virtual beacon:
 
- Area obstructions
- The capabilities of the user’s mobile device
- The geo-location abilities of the user’s mobile device
- Indoor placement (Note: This may significantly affect the accuracy of the virtual beacon)

The ProxSee SDK is also expected to receive a location update whenever the device is moved approximately 100 meters. in general, the closer you are to a beacon the more accurate the reported distance. 
 
Because of the factors mentioned above, it’s not possible to provide specific numbers for accuracy. On average, the measurement error can be 20-30% of the actual distance. You can increase signal reliability by increasing the Broadcasting Power. For greater accuracy, the use of beacons (physical devices) is recommended.

#### Locations
 
Locations within the ProxSee platform group beacons/virtual beacons by region and establish a default tag for each beacon/virtual beacon within the region. Locations also play a part in the caching of beacon data.
 
To add/delete locations, see the Locations section of the ProxSee Admin Portal. 
 
#### Tags
 
Tags are simply short descriptions in the form of hashtags that are associated to beacons/virtual beacons for identification and classification purposes.
 
By default, each beacon/virtual beacon has a “#<Location>” (with <Location> being the Location of the beacon/virtual beacon) tag. 
 
You can associate the same tag with multiple beacons/virtual beacons.  or instance, you may place beacons at all of your exits and associated them all with an  "#Exit" tag.. However, if a user moves between two beacons associated with the same tag, the ProxSee SDK will not create a tag changeset notification. A tag changeset notification is only generated when the tags change (e.g., tags were removed from or added to the beacon/virtual beacon). 
 
To add and assign/remove tags, see the Tags section of the ProxSee Admin Portal. if a 
 
#### Metadata
 
Metadata can be used to provide additional information about a user and their device, such as user ID and user preferences. The metadata sent to the ProxSee SDK is then associated to each check-in by the user’s device.
 
See the [Update Metadata](#update-metadata) section in this document for instructions on how to add metadata.  
 
#### Check-In/Check-Out
 
A checkin-in/check-out forms a tuple for a device event and helps track enter and exit events.  
When tracking enter (check-in) and exit (check-out) events, keep in mind:
You may have a check-in without a corresponding check-out. The most common cause of this is network interruptions.  
 
When using virtual beacons, check-ins are more likely to occur than check-outs.
 
Data is stored during both a check-in and a check-out.
 
- **Check-in**: The majority of information is stored during a check-in and includes: 
    - the time
    - the device's unique ID (UUID)
    - additional system information, including  the version of the SDK used 
- **Check-out**: Only the check-out time is updated and stored..

### Section 2: Implementing the ProxSee SDK in an Android Project
 
Incorporating the ProxSee SDK into your Android project is a simple four-step process:
 
- [Generate a Mobile API Key](#generate-a-mobile-api-key)
- [Integrate the ProxSee SDK into Your Android Project](#integrate-the-proxsee-sdk-into-your-android-project)
- [Complete ProGuard Configuration](#complete-proguard-configuration)
- [Launch the ProxSee SDK](#launch-the-proxsee-sdk)

### Prerequisites
 
The ProxSee SDK requires:
 
- An active Bluetooth service in order to function with beacons/virtual beacons
- Active Location services in order to function with virtual beacons
- An Internet connection


### Generate a Mobile API Key
 
In order to use the ProxSee SDK, you will need to generate a Mobile API Key.
 
1. Navigate to the ProxSee portal at [https://app.proxsee.io/#/login](#https://app.proxsee.io/#/login).
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
3. Open build.gradle (Module: app).
4. In the dependencies section of your your project’s build.gradle, add the following:


   ```
   compile 'io.proxsee:proxsee-sdk:SDK_VERSION'  #latest released version will be found on http://mvnrepository.com/artifact/io.proxsee/proxsee-sdk


   ```
5. From the main toolbar, click the **Sync Project with Gradle Files** button.


At this point, the SDK is ready to use and your project can compile successfully. 

### Complete ProGuard Configuration


If ProGuard is used for obfuscating the source code, the following rules must be added into the **proguard-rules.pro** file.

```
# Gson specific classes (required for Proxsee)
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.stream.JsonScope
-dontwarn com.google.gson.Gson
-dontwarn com.google.gson.TypeAdapter
-dontwarn com.google.gson.internal.bind.TypeAdapters$20

# Proxsee
-keep class io.proxsee.sdk.** { *; }
-dontwarn javax.annotation.**
-dontwarn javax.lang.model.**
-dontwarn javax.tools.**

# Realm (required for Proxsee)
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# Okhttp (required for Proxsee)
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

```

### Launch the ProxSee SDK

On the application onCreate, initialize the ProxSee SDK with the Mobile API key you generated in a previous step. See [Generate a Mobile API Key](#generate-a-mobile-api-key). 


On the initial launch, once initialized, the ProxSee SDK will be ON by default and will start automatically. When your app is restarted followed by a call to initialize, the ProxSee SDK will attempt to start depending on the SDK state. Calling initialize more than once has no affect, unless the Mobile API Key has changed. 


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
- [Start/Stop the ProxSee SDK](#startstop-the-proxsee-sdk)
- [Check and Enable Permissions at Runtime](#check-and-enable-permissions-at-runtime)
- [Update Metadata](#update-metadata)

### Handle Tag Changeset Notifications

The ProxSee SDK broadcasts all tag changes. To start listening for changes simply register ```ProxSeeBroadcastReceiver``` and override the ```didChangeTagsSet``` method. The ProxSee SDK will send a ```BeaconNotificationObject``` which includes the new and previous tag changesets along with the date captured for each changeset.

Example:

```
import android.app.Activity;

import io.proxsee.sdk.ProxSeeSDKManager;
import io.proxsee.sdk.model.BeaconNotificationObject;

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

### Start/Stop the ProxSee SDK

At any point of the application lifecycle you can start or stop the ProxSee SDK. Stopping the ProxSee SDK will stop beacon/virtual beacon monitoring, check-in/check-out broadcasts, tag changeset notifications, and metadata updates. 

Any explicit call to start or stop the ProxSee SDK will change the SDK state accordingly. 

#### Determine the State of the ProxSee SDK
 
To determine if the ProxSee SDK is in the start or stop state:


```
ProxSeeSDKManager.getInstance().isStarted();


```
#### Start the ProxSee SDK 
 
To start he ProxSee SDK and in turn start the monitoring of beacons/virtual beacons, check-in/check-out broadcasts, tag changeset notifications, and metadata updates:

```
ProxSeeSDKManager.getInstance().start();


```
 
#### Stop the ProxSee SDK
 
To stop the ProxSee SDK and in turn stop the monitoring of beacons/virtual beacons, check-in/check-out broadcasts, tag changeset notifications, and metadata updates:

```
ProxSeeSDKManager.getInstance().stop();


```
### Check and Enable Permissions at Runtime

If you are targeting Android API 23 and above, you will need to check and enable permissions at runtime. 
 
The ProxSee SDK requires **either** of the following permissions to operate:
 
- ACCESS_FINE_LOCATION
- ACCESS_COARSE_LOCATION 

The ProxSee SDK also has the ability to pick up permission changes and resume/pause automatically depending on the SDK state without an explicit call to stop/start.
 
The following sample demonstrates how to request permissions in your activity. The sample below checks permissions on start, but you may modify it as suitable for your project. For that purpose, you can use our ProxSeePermissionManager.

```
import io.proxsee.sdk.ProxSeePermissionManager;


private static final int PROXSEE_PERMISSIONS_REQUEST = 1;
 private ProxSeePermissionManager proxSeePermissionManager = new ProxSeePermissionManager();

@Override
protected void onStart() {
    super.onStart();
    if (!proxSeePermissionManager.isPermissionGranted(this)) {
            requestPermissions(proxSeePermissionManager.requiredPermissions(), PROXSEE_PERMISSIONS_REQUEST);
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
                //you are not required to start explicitly unless you have stopped the SDK earlier. The SDK will be able to resume automatically if it was in a starting state.
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
ProxSeeSDKManager.getInstance().updateMetadata(metadata, new ProxSeeSDKManager.CompletionHandler() {
    @Override
    public void onUpdateCompleted(boolean success, Exception error) {
        // handle response
    }
});


```
 
## Section 4: FAQs
 
**Will the ProxSee SDK impact my mobile phone’s battery?** 
 
Yes. The ProxSee SDK will draw approximately 1-2% of the mobile phone’s battery. 
 
**How long does it take for the ProxSee SDK to detect a beacon?**
 
- **Beacons**: 0 to a few seconds
- **Virtual beacons**: 2.5 to 5 minutes
 
**As a third-party developer using the ProxSee SDK, do I need to do anything if my application is rebooted?**
 
Refer to the [Launch the ProxSee SDK](#launch-the-proxsee-sdk) section for details.
 
**What happens when Bluetooth is disabled?**
 
Scanning for physical beacons is paused while scanning for virtual beacons will continue. Once Bluetooth is re-enabled, scanning for physical beacons will resume. Note: The ProxSee SDK must have monitoring enabled in order to receive events.

**What happens when Location services are disabled?**

The ProxSee SDK needs to be enabled to receive events. Assuming the ProxSee SDK is enabled, if Location services are disabled, the detection of virtual beacons will be paused. It is noted that on Nexus 5 if locations services are disabled, scanning for physical beacons will be paused as well. Once Location services are re-enabled, the detection of virtual beacons will resume. 
 
**What happens when Location permissions are disabled?**
 
On Android 6, permissions can be enabled/disabled. When you disable Location permissions the OS will restart the application. Assuming the ProxSee SDK is enabled,  it will resume, but the monitoring of beacons and virtual beacons will be paused. Once Location permissions are re-enabled, the monitoring of beacons and virtual beacons will be resumed. 

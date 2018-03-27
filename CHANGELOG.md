# ProxSee SDK for Android Change Log


All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## [3.0.4] - 2018-03-26
### Fixed
- fixing bugs and observed crashes

## [3.0.3] - 2018-02-27
### Fixed
- fixing a crash android 5 



## [3.0.2] - 2018-01-20
### Added
- add back beacon support for android 4.4 


## [3.0.1] - 2018-01-08
### Changed
- optimise network calls.


## [3.0.0] - 2017-11-29
### Changed
- Change package and main api interface. Please view [3.0.0 Migration Guide](Docs/migration_to_3.0.0.md). 
- Redesign the SDK architecture
- Add proper Android O support.
- Beacon detection is now only available starting at OS 5

## [2.5.1] - 2017-10-23

### Added
- Adding fetchDeviceId api to get ProxSee identifier

## [2.5.0] - 2017-09-09

### Fixed
- Fixing edge cases where checkout was not being detected 

### Changed
- Enhancing beacons detection in deep sleep mode

## [2.4.7] - 2017-08-10

### Changed
- Refining accuracy detection for virtual beacons

## [2.4.6] - 2017-08-16

### Changed

- Adding proper user agent header
- Remove in-memory cache and use head calls.


## [2.4.5] - 2017-06-01


### Added


- Adding gzip support

## [2.4.4] - 2017-06-01


### Changed


- OkHttp is now used instead of Volley 

### Fixed


- Fixes were made to make sure the SDK starts on the first initialize

## [2.4.3] - 2017-05-15


### Fixed


- Requests have been fixed and optimized 
- Cache time is now properly populated on updates 

### Changed


- Changes have been made to the way device IDs are registered
- AltBeacon has been updated to 2.10


## [2.4.2] - 2017-02-12


### Changed


- Google Play Services (gms play-services) has been updated to 10.0.1

## [2.4.1] - 2017-01-19


### Changed


- Gradle and Google Play Services (gms play-services) have been updated to 10.0.0

## [2.4.0] - 2016-12-20


### Changed


- Virtual regions detection logic has been refactored
- Region limitations and implicit checkouts have been removed
- Intersected regions are now allowed 
- The region minimum limit has been reduced
- Beacon detection has been enhanced and false handshakes reduced
- AltBeacon has been updated to 2.9.2
- Offline support has been increased and the number of requests reduced


## [2.3.8] - 2016-10-02


### Fixed


- Fixes were made to make sure the SDK broadcasts are received by the appropriate app

## [2.3.7] - 2016-09-30


### Fixed


- Only regions that are subscribed to will be processed

## [2.3.6] - 2016-09-19


### Changed


- AltBeacon has been updated to 2.9


### Fixed


- Fixes have been made to make sure that apps other than the intended ones do not receive ProxSee SDK broadcasts

## [2.3.5] - 2016-07-21


### Changed


- The Dagger library has been updated to version 2.5

## [2.3.4] - 2016-07-19


### Changed


- The Dagger library has been updated to version 2.5

## [2.3.3] - 2016-07-04


### Changed


- Bluetooth, location, and SDK metadata flags are no longer sent

## [2.3.2] - 2016-06-30


### Changed


- The Readme file has been updated regarding Android 6.0 permissions


### Added


- Android 6.0 permissions have been added to SampleApplication


## [2.3.1] - 2016-03-14


### Changed


- AARs are now generated instead of Jars


## [2.3.0] - 2016-03-07


### Added


- Offline support has been added for check-ins/check-outs (The SDK will now save any check-in/check-out in offline mode so it can resubmit later when back online)
- Sending Beacon RSSI information is now sent with check-in requests
- The SDK can now run on a single thread

### Changed


- Realm has been updated to version 0.87.4 (Please review your ProGuard rules [here](https://realm.io/docs/java/0.87.4/#getting-started))
- Pushing Bluetooth state change metadata has been disabled
- The AltBeacon hack is no longer used and the SDK is now used out of the box
- A new layer has been introduced to handle detecting beacons nearby while minimising false handshakes
- The SDK no longer has to wait 5 minutes to detect an exit
- Changes have been made to the SDK API interface
- Enhancements have been made in the way Realm is used and connections are now properly closed
- The SDK test app UI has been refactored

## [2.2.3] - 2016-01-13


### Fixed


- Issues with Android 6.0 and how the SDK generates the unique device IDs have been resolved

## [2.2.2] - 2015-12-09


### Fixed


- A Vera code issue where == was used instead of equals has been resolved

## [2.2.1] - 2015-11-27


### Changed


- The Realm dependency has been updated to version 0.85.1

## [2.2.0] - 2015-11-26


### Added


- The ability to start and stop the SDK has been added
- Device information can now be captured through metadata such as location, Bluetooth, and the starting/stopping of the SDK

### Changed


- Metadata is no longer sent when the SDK is turned off
- The SDK now waits 5 minutes instead of 30 seconds before confirming a check-out 
- Battery consumption has been improved


## [2.1.2] - 2015-09-25
### Changed


- The build server has been changed (there should be no functional difference between this and build 2.1.1)

## [2.1.1] - 2015-09-25


### Fixed


- Fixed a crash that could occur when a user upgraded an application containing SDK 2.0.x to 2.1.0 (or higher) 

## [2.1.0] - 2015-09-16


### Added


- Virtual Beacon Support
    - Virtual beacon entry and exit notifications will be attempted within 20 seconds of the trigger
    - If another beacon region is encountered before receiving a notification that the device has exited the current virtual beacon, the device will perform a check-out with the additional flag "context.impliedCheckout=true" in its device event record

### Changed

- The documentation has been updated

### Fixed

- A new URL is referenced for querying nearby beacons 
- The internal hashing algorithm has been updated to SHA-512 
- The ProGuard rules (and README.txt) have been updated to include volley classes, etc.

## [2.0.5] - 2015-09-21


### Fixed


- Corrected the failed Vera code test case (improper Resource Shutdown or release in the BluetoothCrashResolver class)
 

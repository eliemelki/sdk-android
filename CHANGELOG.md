# Android SDK Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## [2.4.0] - NOT YET RELEASED


## [2.3.8] - 2016-10-02
## Fixed
- Make sure sdk broadcasts are received by the appropriate app.

## [2.3.7] - 2016-09-30
## Fixed
- Process only regions we subscribed to.

## [2.3.6] - 2016-09-19
### Changed
- Update altbeacon to 2.9
### Fixed
- Make sure other apps dont receive our broadcast

## [2.3.5] - 2016-07-21
### Changed
- Update dagger library to version 2.5

## [2.3.4] - 2016-07-19
### Changed
- Update dagger library to version 2.5

## [2.3.3] - 2016-07-04
### Changed
- Stop sending bluetooth, location and sdk metadata flags.

## [2.3.2] - 2016-06-30
### Changed
- Update readme and include Android 6.0 permissions
### Added
- Adding android 6.0 permission to SampleApplication


## [2.3.1] - 2016-03-14
### Changed
- Generate aar instead of jar


## [2.3.0] - 2016-03-07
### Added
- Offline support for checkin/out. The SDK will now save any checkin/out in offline mode, so it can resubmit later when its back online.
- Sending beacon rssi information with checkin requests.
- Allow the sdk to run on one single thread

### Changed
- Updated realm to version 0.87.4. Please review your ProGuard rules [here](https://realm.io/docs/java/0.87.4/#getting-started).
- Disabled pushing bluetooth state change metadata until we better understand PROXSEE-664
- No longer use the Alt beacon hack and use the out of the box sdk.
- Introduce a new layer that handle detecting beacon nearby minimising false handshake.
- SDK no longer have to wait 5 minute to detect an exit.
- Change the SDK api Interface.
- Enhance the way we use realm and make sure we properly close connections.
- Refactor the SDK test app UI.

## [2.2.3] - 2016-01-13
### Fixed
- Issue with Android 6.0 and how the sdk was generating the device unique id

## [2.2.2] - 2015-12-09
### Fixed
- Vera code issue where == is used instead of equals

## [2.2.1] - 2015-11-27
### Changed
- Update realm dependency to 0.85.1

## [2.2.0] - 2015-11-26
### Added
- Ability to start / stop the SDK
- Capturing device information through metadata such as location, bluetooth and starting/stopping of the sdk

### Changed
- Metadata are no longer sent when the sdk is turned off
- SDK will wait 5 minutes before confirmung a checkout as opposed to 30 seconds previously
- Improved battery consumption


## [2.1.2] - 2015-09-25
### Changed
- Changed the build server - there should be no functional difference between this and build 2.1.1 [PROXSEE-476]

## [2.1.1] - 2015-09-25
### Fixed
- Fixed a crash that could occur when a user upgraded an application containing SDK 2.0.x to 2.1.0 (or higher) [PROXSEE-415]

## [2.1.0] - 2015-09-16
### Added
- Virtual Beacon support
    - Read more about virtual beacons [here](../features/virtualbeacons.md) - Android-specific features follow.
    - Virtual beacon entry and exit notifications will be attempted within 20 seconds of the trigger
    - If another beacon region is encountered before receiving a notification that the device has exited the current virtual beacon, the device will perform a check-out with an additional flag "context.impliedCheckout=true" in its deviceevent record

### Changed

- Updated documentation [PROXSEE-334]

### Fixed

- Reference new URL for querying nearby beacons [PROXSEE-168]
- Updated internal hashing algorithm to SHA-512 [PROXSEE-349]
- Updated proguard rules (and README.txt) to include volley classes, etc.

## [2.0.5] - 2015-09-21
### Fixed
- Corrected failed Veracode test case - Improper Resource Shutdown or Release in the BluetoothCrashResolver class [PROXSEE-380]

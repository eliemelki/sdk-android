# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/shatazone/Documents/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

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

# Volley (required for Proxsee)
-keepattributes InnerClasses
-keep class com.android.volley.** {*;}
-keep class com.android.volley.toolbox.** {*;}
-keep class com.android.volley.$ {*;}
-keep class org.apache.commons.logging.**
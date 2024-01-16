# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

#region Datastore
-keep public class * extends java.lang.Exception

# AuthSettings
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.auth.AuthSettings**{*;}
-keep class com.yougotagift.app.auth.AuthSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.auth.AuthSettingsOuterClass**{*;}

# StoreSettings
-keep class com.yougotagift.YouGotaGiftApp.StoreSettings**{*;}
-keep class com.yougotagift.YouGotaGiftApp.StoreSettingsOrBuilder**{*;}
-keep class com.yougotagift.YouGotaGiftApp.StoreSettingsOuterClass**{*;}

# UserDetails
-keep class com.yougotagift.app.auth.UserDetails**{*;}
-keep class com.yougotagift.app.auth.UserDetailsOrBuilder**{*;}
-keep class com.yougotagift.app.auth.UserDetailsOuterClass**{*;}

# UserPreferences
-keep class com.yougotagift.app.base.userpreferences.UserPreferences**{*;}
-keep class com.yougotagift.app.base.userpreferences.UserPreferencesOrBuilder**{*;}
-keep class com.yougotagift.app.base.userpreferences.UserPreferencesOuterClass**{*;}

# WalletSettings
-keep class com.yougotagift.app.base.WalletSettings**{*;}
-keep class com.yougotagift.app.base.WalletSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.base.WalletSettingsOuterClass**{*;}

#endregion


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class com.ygag.activities.YGAGHomeActivity { *; }
-keep public class com.ygag.manager.GiftCardsManager { *; }
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.billing.IInAppBillingService
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}


-keep class org.apache.commons.logging.**               { *; }
-keep class com.amazonaws.org.apache.commons.logging.** { *; }
-keep class com.amazonaws.services.sqs.QueueUrlHandler  { *; }
-keep class com.amazonaws.javax.xml.transform.sax.*     { public *; }
-keep class com.amazonaws.javax.xml.stream.**           { *; }
-keep class com.amazonaws.services.**.model.*Exception* { *; }
-keep class com.amazonaws.internal.**                   { *; }
-keep class org.codehaus.**                             { *; }
-keep class org.joda.time.tz.Provider                   { *; }
-keep class org.joda.time.tz.NameProvider               { *; }
-keepattributes Signature,*Annotation*,EnclosingMethod
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class com.amazonaws.** { *; }

-dontwarn com.fasterxml.jackson.databind.**
-dontwarn javax.xml.stream.events.**
-dontwarn org.codehaus.jackson.**
-dontwarn org.apache.commons.logging.impl.**
-dontwarn org.apache.http.conn.scheme.**
-dontwarn org.apache.http.annotation.**
-dontwarn org.ietf.jgss.**
-dontwarn org.joda.convert.**
-dontwarn com.amazonaws.org.joda.convert.**
-dontwarn org.w3c.dom.bootstrap.**



#SDK split into multiple jars so certain classes may be referenced but not used
-dontwarn com.amazonaws.services.s3.**
-dontwarn com.amazonaws.**
-dontwarn com.amazonaws.services.sqs.**
-dontnote com.amazonaws.services.sqs.QueueUrlHandler
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepnames class com.facebook.FacebookActivity
-keepnames class com.facebook.CustomTabActivity
-keep class com.facebook.** { *; }
-keep class com.facebook.all.All
-dontwarn javax.annotation.**
-dontwarn com.googlecode.mp4parser.**
-keep public class com.android.vending.billing.IInAppBillingService {
    public static com.android.vending.billing.IInAppBillingService asInterface(android.os.IBinder);
    public android.os.Bundle getSkuDetails(int, java.lang.String, java.lang.String, android.os.Bundle);
}




#for crashlytics
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**




#for google play services
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep public @com.google.android.gms.common.util.DynamiteApi class * {
  public <fields>;
  public <methods>;
}
-dontwarn android.security.NetworkSecurityPolicy




# for barcode scanner
-keepclassmembers class net.sourceforge.zbar.ImageScanner { *; }
-keepclassmembers class net.sourceforge.zbar.Image { *; }
-keepclassmembers class net.sourceforge.zbar.Symbol { *; }
-keepclassmembers class net.sourceforge.zbar.SymbolSet { *; }


# for gson parser
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.ygag.models.** { *; }
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


#for loopj async client
-keep class cz.msebera.android.httpclient.** { *; }
-keep class com.loopj.android.http.** { *; }



#for glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.volley.VolleyGlideModule
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule



# EventBus
-keep class de.greenrobot.event.** { *; }
-keep class * {
    @de.greenrobot.event.* <methods>;
}
-keepclassmembers class ** {
    public void onEvent*(***);
}
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}




#for facebook
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**


#for mp4parser

-keep class * implements com.coremedia.iso.boxes.Box { *; }
-dontwarn com.coremedia.iso.boxes.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.ttml.**

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-dontwarn carbon.BR
-dontwarn carbon.internal**
-dontwarn java.lang.invoke**

-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

# AuthSettings
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.cart.CartSettings**{*;}
-keep class com.yougotagift.app.cart.CartSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.cart.CartSettingsOuterClass**{*;}

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

#region Datastore
-keep public class * extends java.lang.Exception

# AuthSettings
-keep class androidx.datastore.*.** {*;}
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.auth.AuthSettings**{*;}
-keep class com.yougotagift.app.auth.AuthSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.auth.AuthSettingsOuterClass**{*;}

# StoreSettings
-keep class com.yougotagift.YouGotaGiftApp.StoreSettings**{*;}
-keep class com.yougotagift.YouGotaGiftApp.StoreSettingsOrBuilder**{*;}
-keep class com.yougotagift.YouGotaGiftApp.StoreSettingsOuterClass**{*;}

# UserDetails
-keep class com.yougotagift.app.auth.UserDetails**{*;}
-keep class com.yougotagift.app.auth.UserDetailsOrBuilder**{*;}
-keep class com.yougotagift.app.auth.UserDetailsOuterClass**{*;}

# UserPreferences
-keep class com.yougotagift.app.base.userpreferences.UserPreferences**{*;}
-keep class com.yougotagift.app.base.userpreferences.UserPreferencesOrBuilder**{*;}
-keep class com.yougotagift.app.base.userpreferences.UserPreferencesOuterClass**{*;}
-keepclassmembers class com.yougotagift.app.base.userpreferences.UserPreferences* {
   <fields>;
}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite** {*;}

# WalletSettings
-keep class com.yougotagift.app.base.WalletSettings**{*;}
-keep class com.yougotagift.app.base.WalletSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.base.WalletSettingsOuterClass**{*;}

#endregion

#region Retrofit
-dontnote retrofit2.Platform
-keepattributes Signature
-keepattributes Exceptions
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe**{ *; }
-keep class com.google.gson.stream.**{ *; }

# AuthSettings
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.onboarding.OnBoardingSettings**{*;}
-keep class com.yougotagift.app.onboarding.OnBoardingSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.onboarding.OnBoardingSettingsOuterClass**{*;}


# AuthSettings
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettings**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettingsOuterClass**{*;}


# AuthSettings
-keep class androidx.datastore**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettings**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettingsOrBuilder**{*;}
-keep class com.yougotagift.app.groupgift.GroupGiftSettingsOuterClass**{*;}


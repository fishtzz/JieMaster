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
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#xbanner
-keep class com.stx.xhb.xbanner.**{*;}


# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.

-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#本地文件
-keep class com.szmaster.jiemaster.model.**{*;}
-keep class com.szmaster.jiemaster.network.**{*;}
-keep class com.szmaster.jiemaster.widget.recyclerview.**{*;}
-keep class * extends com.szmaster.jiemaster.widget.recyclerview.CommonHolder{*;}

#保护注解
-keepattributes *Annotation*

#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

#如果引用了v4或者v7包
-dontwarn android.support.**
#如果用用到Gson解析包的，直接添加下面这几行就能成功混淆，不然会报错。
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }


#----------------------- 下面为Android默认选项，请勿修改 -----------------------#
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
}

# keep R.java
-keepclassmembers class **.R$* {
	public static <fields>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
	void set*(***);
}
-keepclasseswithmembers class * {
	public <init>(android.content.Context,android.util.AttributeSet);
}
-keepclasseswithmembers class * {
	public <init>(android.content.Context,android.util.AttributeSet,int);
}

# keep android.os.Parcelable
-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

# Also keep - Serialization code. Keep all fields and methods that are used for
# serialization.
-keepclassmembers class * extends java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
	public static ** valueOf(java.lang.String);
}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
	native <methods>;
}

# Remove debugging - Throwable_printStackTrace calls. Remove all invocations of
# Throwable.printStackTrace().
-assumenosideeffects public class java.lang.Throwable {
	public void printStackTrace();
}

# Remove debugging - Thread_dumpStack calls. Remove all invocations of
# Thread.dumpStack().
-assumenosideeffects public class java.lang.Thread {
	public static void dumpStack();
}
# Proguard config for SQLCipher
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

# Prpguard config for Gson
-keepattributes Signature
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Proguard config for Retrofit
-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable, SourceFile, EnclosingMethod, *Annotation*
#-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions*

-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Proguard config for Okhttp
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# Proguard config for Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
  <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Proguard config for Koin
-keepnames class androidx.lifecycle.ViewModel
-keepclassmembers public class * extends androidx.lifecycle.ViewModel { public <init>(...); }
-keepclassmembers class com.lebao.app.domain.** { public <init>(...); }
-keepclassmembers class * { public <init>(...); }

# Proguard config material design
-keep class com.google.android.material.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Proguard config for CircleImageView and SpinKit
-dontwarn de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.** { *; }
-dontwarn com.github.ybq.**
-keep class com.github.ybq.** { *; }

# Proguard config Kotlin
-dontwarn org.jetbrains.kotlinx.**
-keep class kotlin.jvm.** { *; }

# Proguard config for Coroutines
-dontwarn kotlin.coroutines.**
-dontwarn kotlinx.coroutines.**

# Proguard config for Parcelable
-keep class **$$Parcelable { *; }

-keep class com.syntia.moviecatalogue.base.data.remote.response.* { *; }
-keep class com.syntia.moviecatalogue.core.data.source.remote.response.* { *; }

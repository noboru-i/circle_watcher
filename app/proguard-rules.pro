-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions

# gson
-keepnames class hm.orz.chaos114.android.circlewatcher.entity.** { *; }

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

# Retrolambda
-dontwarn java.lang.invoke.*

-dontwarn java.beans.**

# ============================================================
# REGLAS DE PROGUARD / R8 PARA SMARTPOINT
# ============================================================

# 1. Mantener modelos de datos y anotaciones de Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Proteger todas las clases del paquete Modelo (data classes y serializers)
-keep,includedescriptorclasses class com.example.smartpoint.Modelo.**$$serializer { *; }
-keepclassmembers class com.example.smartpoint.Modelo.** {
    *** Companion;
}
-keepclasseswithmembers class com.example.smartpoint.Modelo.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep class com.example.smartpoint.Modelo.** { *; }

# 2. Proteger la interfaz ApiService de Retrofit
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep interface com.example.smartpoint.interfaceApi.** { *; }

# Evitar advertencias de OkHttp / Okio
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

# 3. Proteger Glide (carga de imágenes desde Hostinger)
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
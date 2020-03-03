# Root proguand file applied to all modules
# Keep the important stuff in release version
#-printusage  ~/tmp/full-r8-config.txt
-keepattributes *Annotation*

-keeppackagenames com.toolsfordevs.fast.core.**
-keeppackagenames com.toolsfordevs.fast.**

#-keeppackagenames com.toolsfordevs.fast.modules.androidx.**
#-keeppackagenames com.toolsfordevs.fast.modules.androidx.recyclerview.**
#-keeppackagenames com.toolsfordevs.fast.modules.recyclerview.**
#-keeppackagenames com.toolsfordevs.fast.modules.resourcepicker.**
#-keeppackagenames com.toolsfordevs.fast.modules.resources.**
#-keeppackagenames com.toolsfordevs.fast.modules.subheader.**
#-keeppackagenames com.toolsfordevs.fast.modules.viewhierarchyexplorer.**

#-keeppackagenames com.toolsfordevs.fast.plugins.actions.**
#-keeppackagenames com.toolsfordevs.fast.plugins.crashinfo.**
#-keeppackagenames com.toolsfordevs.fast.plugins.logger.**
#-keeppackagenames com.toolsfordevs.fast.plugins.overlay.**
#-keeppackagenames com.toolsfordevs.fast.plugins.resourceexplorer.**
#-keeppackagenames com.toolsfordevs.fast.plugins.viewinspector.**

-keepclasseswithmembers class com.toolsfordevs.fast.core.processor.BaseProcessor
-keepclasseswithmembers class com.toolsfordevs.fast.core.processor.ModulesProcessor
-keepclasseswithmembers class com.toolsfordevs.fast.core.processor.PluginProcessor

-keep class com.toolsfordevs.fast.core.annotation.Keep
-keep @com.toolsfordevs.fast.core.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.Keep <init>(...);
}

-keep @interface com.toolsfordevs.fast.core.annotation.FastIncludeModule
-keep class com.toolsfordevs.fast.core.annotation.FastIncludeModule
-keep @com.toolsfordevs.fast.core.annotation.FastIncludeModule class * {*;}

-keep @interface com.toolsfordevs.fast.core.annotation.FastIncludePlugin
-keep class com.toolsfordevs.fast.core.annotation.FastIncludePlugin
-keep @com.toolsfordevs.fast.core.annotation.FastIncludePlugin class * {*;}

# Save the obfuscation mapping to a file, so we can de-obfuscate any stack
# traces later on. Keep a fixed source file attribute and all line number
# tables to get line numbers in the stack traces.
# You can comment this out if you're not interested in stack traces.

#-printmapping out.map
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod

# Preserve all annotations.

#-keepattributes *Annotation*

# Preserve all public classes, and their public and protected fields and
# methods.

-keep public class * {
    public protected *;
}

# Preserve all .class method names.

-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}

# Preserve all native method names and the names of their classes.

-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve the special static methods that are required in all enumeration
# classes.

-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
# You can comment this out if your library doesn't use serialization.
# If your code contains serializable classes that have to be backward
# compatible, please refer to the manual.

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.FastIncludePlugin <methods>;
}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.FastIncludePlugin <fields>;
}

-keepclasseswithmembers class * {
    @com.toolsfordevs.fast.core.annotation.FastIncludePlugin <init>(...);
}

-keepdirectories resources/META-INF
-keepdirectories resources/META-INF/**

-keep class kotlin.Metadata {
    *;
}

-keep class com.toolsfordevs.fast.plugins.viewinspector.ViewInspectorPlugin
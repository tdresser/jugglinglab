# For java 8.
# -libraryjar <java.home>/lib/rt.jar

-libraryjar  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
-libraryjar  <java.home>/jmods/java.xml.jmod(!**.jar;!module-info.class)

-printmapping myapplication.map

# These are scary...
-dontwarn com.google.ortools.**
-dontwarn com.google.protobuf.**
-dontwarn java.awt.geom.**
-dontwarn com.sun.jna.**


-keep public class jugglinglab.JugglingLab {
    public static void main(java.lang.String[]);
}
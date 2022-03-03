# For java 8.
# -libraryjar <java.home>/lib/rt.jar
-libraryjar /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar

#-libraryjar  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
#-libraryjar  <java.home>/jmods/java.xml.jmod(!**.jar;!module-info.class)

-printmapping myapplication.map

# -optimizationpasses 3
# -overloadaggressively
# -repackageclasses ''
# -allowaccessmodification

-dontwarn java.util.logging.**
-dontwarn java.awt.geom.**
-dontwarn com.sun.jna.**
-dontwarn sun.misc.**


-keep public class jugglinglab.JugglingLab {
    public static void main(java.lang.String[]);
}
-injars       bin/JugglingLabMonolith.jar
-outjars      bin/JugglingLabMonolithProguarded.jar

# For java > 8.
# -libraryjar  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
# -libraryjar  <java.home>/jmods/java.xml.jmod(!**.jar;!module-info.class)

# For java 8.
-libraryjar <java.home>/lib/rt.jar

-printmapping myapplication.map

# These are scary...
-dontwarn com.google.ortools.**
-dontwarn java.awt.geom.**


-keep public class jugglinglab.JugglingLab {
    public static void main(java.lang.String[]);
}
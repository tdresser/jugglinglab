-injars       bin/JugglingLabMonolith.jar
-outjars      bin/JugglingLabMonolithProguarded.jar

-libraryjar  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
-libraryjar  <java.home>/jmods/java.xml.jmod(!**.jar;!module-info.class)

-printmapping myapplication.map

# These are scary...
-dontwarn com.google.ortools.**
-dontwarn java.awt.geom.**


-keep public class jugglinglab.JugglingLab {
    public static void main(java.lang.String[]);
}
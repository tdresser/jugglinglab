-injars       bin/JugglingLab.jar
-outjars      bin/JugglingLabProguarded.jar

-libraryjar  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
-libraryjar  <java.home>/jmods/java.xml.jmod(!**.jar;!module-info.class)
-libraryjar  bin/commons-math3-3.6.1.jar
-libraryjar  bin/ortools-lib/com.google.ortools.jar

-printmapping myapplication.map

-keep public class jugglinglab.JugglingLab {
    public static void main(java.lang.String[]);
}
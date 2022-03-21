# For java 8.
# -libraryjar <java.home>/lib/rt.jar
-libraryjar /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar

# -injars /home/tim/jugglinglab/build/libs/jugglinglab-1.6.2-uber-ant.jar
# -outjar /home/tim/jugglinglab/build/libs/jugglinglab-1.6.2-uber-proguard-commandline.jar

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
    public static byte[] getPatternProto(java.lang.String);
}

# Copied from cjGetProguardConfiguration().
-keepattributes Signature,*Annotation*
-keep class jugglinglab.prop.Prop
-keepnames class jugglinglab.prop.Prop { *; }
-keep class jugglinglab.prop.BallProp
-keepnames class jugglinglab.prop.BallProp { *; }
-keep class alternatives.Color
-keepnames class alternatives.Color { *; }
-keep class jugglinglab.util.Coordinate
-keepnames class jugglinglab.util.Coordinate { *; }
-keep class jugglinglab.util.JLFunc
-keepnames class jugglinglab.util.JLFunc { *; }
-keep class jugglinglab.util.ParameterList
-keepnames class jugglinglab.util.ParameterList { *; }
-keep class jugglinglab.notation.MHNThrow
-keepnames class jugglinglab.notation.MHNThrow { *; }
-keep class jugglinglab.notation.SiteswapPattern
-keepnames class jugglinglab.notation.SiteswapPattern { *; }
-keep class jugglinglab.notation.MHNPattern
-keepnames class jugglinglab.notation.MHNPattern { *; }
-keep class jugglinglab.notation.MHNSymmetry
-keepnames class jugglinglab.notation.MHNSymmetry { *; }
-keep class jugglinglab.notation.ssparser.SiteswapParser
-keepnames class jugglinglab.notation.ssparser.SiteswapParser { *; }
-keep class jugglinglab.notation.ssparser.SiteswapTreeItem
-keepnames class jugglinglab.notation.ssparser.SiteswapTreeItem { *; }
-keep class jugglinglab.util.Permutation
-keepnames class jugglinglab.util.Permutation { *; }
-keep class jugglinglab.notation.Pattern
-keepnames class jugglinglab.notation.Pattern { *; }
-keep class jugglinglab.notation.ssparser.Token
-keepnames class jugglinglab.notation.ssparser.Token { *; }
-keep class jugglinglab.notation.ssparser.SiteswapParser$LookaheadSuccess
-keepnames class jugglinglab.notation.ssparser.SiteswapParser$LookaheadSuccess { *; }
-keep class jugglinglab.notation.ssparser.SiteswapParserTokenManager
-keepnames class jugglinglab.notation.ssparser.SiteswapParserTokenManager { *; }
-keep class jugglinglab.notation.ssparser.SiteswapParser$JJCalls
-keepnames class jugglinglab.notation.ssparser.SiteswapParser$JJCalls { *; }
-keep class jugglinglab.notation.ssparser.SimpleCharStream
-keepnames class jugglinglab.notation.ssparser.SimpleCharStream { *; }
-keep class jugglinglab.jml.JMLEvent
-keepnames class jugglinglab.jml.JMLEvent { *; }
-keep class jugglinglab.jml.JMLPattern
-keepnames class jugglinglab.jml.JMLPattern { *; }
-keep class jugglinglab.jml.JMLSymmetry
-keepnames class jugglinglab.jml.JMLSymmetry { *; }
-keep class jugglinglab.jml.JMLTransition
-keepnames class jugglinglab.jml.JMLTransition { *; }
-keep class jugglinglab.jml.PropDef
-keepnames class jugglinglab.jml.PropDef { *; }
-keep class jugglinglab.JugglingLab
-keepnames class jugglinglab.JugglingLab { *; }
-keep class jugglinglab.path.Path
-keepnames class jugglinglab.path.Path { *; }
-keep class jugglinglab.path.TossPath
-keepnames class jugglinglab.path.TossPath { *; }
-keep class jugglinglab.jml.EventImages
-keepnames class jugglinglab.jml.EventImages { *; }
-keep class jugglinglab.jml.HandLink
-keepnames class jugglinglab.jml.HandLink { *; }
-keep class jugglinglab.jml.VelocityRef
-keepnames class jugglinglab.jml.VelocityRef { *; }
-keep class jugglinglab.jml.PathLink
-keepnames class jugglinglab.jml.PathLink { *; }
-keep class jugglinglab.curve.SplineCurve
-keepnames class jugglinglab.curve.SplineCurve { *; }
-keep class jugglinglab.curve.Curve
-keepnames class jugglinglab.curve.Curve { *; }
-keep class org.apache.commons.math3.linear.Array2DRowRealMatrix
-keepnames class org.apache.commons.math3.linear.Array2DRowRealMatrix { *; }
-keep class org.apache.commons.math3.linear.ArrayRealVector
-keepnames class org.apache.commons.math3.linear.ArrayRealVector { *; }
-keep class org.apache.commons.math3.linear.LUDecomposition
-keepnames class org.apache.commons.math3.linear.LUDecomposition { *; }
-keep class org.apache.commons.math3.util.FastMath
-keepnames class org.apache.commons.math3.util.FastMath { *; }
-keep class org.apache.commons.math3.util.MathUtils
-keepnames class org.apache.commons.math3.util.MathUtils { *; }
-keep class org.apache.commons.math3.linear.RealVector
-keepnames class org.apache.commons.math3.linear.RealVector { *; }
-keep class org.apache.commons.math3.util.CompositeFormat
-keepnames class org.apache.commons.math3.util.CompositeFormat { *; }
-keep class org.apache.commons.math3.linear.AbstractRealMatrix
-keepnames class org.apache.commons.math3.linear.AbstractRealMatrix { *; }
-keep class org.apache.commons.math3.linear.RealLinearOperator
-keepnames class org.apache.commons.math3.linear.RealLinearOperator { *; }
-keep class org.apache.commons.math3.linear.RealMatrixFormat
-keepnames class org.apache.commons.math3.linear.RealMatrixFormat { *; }
-keep class org.apache.commons.math3.linear.RealVectorFormat
-keepnames class org.apache.commons.math3.linear.RealVectorFormat { *; }
-keep class org.apache.commons.math3.linear.LUDecomposition$Solver
-keepnames class org.apache.commons.math3.linear.LUDecomposition$Solver { *; }
-keepclassmembers class jugglinglab.JugglingLab { *; }

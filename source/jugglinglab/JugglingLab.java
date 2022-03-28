// JugglingLab.java
//
// Copyright 2002-2022 Jack Boyce and the Juggling Lab contributors

package jugglinglab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import jugglinglab.jml.JMLPattern;
import jugglinglab.protos.PatternOuterClass.Pattern;
import jugglinglab.util.JuggleExceptionInternal;
import jugglinglab.util.JuggleExceptionUser;
import web_bindings.Serializer;

public class JugglingLab { 
    public static byte[] getPatternProto(String siteswap) {
        try {
            JMLPattern pattern = JMLPattern.fromBasePattern("siteswap", siteswap);
            pattern.layoutPattern();
            Pattern proto = Serializer.serializePattern(pattern);            
            System.out.println("Pattern max x: " + proto.getMin().getX());
            return proto.toByteArray();
        } catch (JuggleExceptionUser e) {
            e.printStackTrace();
        } catch (JuggleExceptionInternal e) {
            e.printStackTrace();
        }
        return new byte[0];
    }  

    public static String getPatternProtoBase64(String siteswap) {        
        System.out.println("getPatternProtoBase64 Java side " + siteswap);
        byte[] proto = getPatternProto(siteswap);
        System.out.println("getPatternProtoBase64 Java side got proto");
        System.out.println(Base64.getEncoder().encodeToString(proto));
        return Base64.getEncoder().encodeToString(proto);
    }  

    public static void test() {
        System.out.println("MAIN 4");
        byte[] bytes = getPatternProto("531");
        System.out.println("TRY");
        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println(encoded);

        Path path = Paths.get("www/resources/test2.js");
        try {
            Files.write(path, encoded.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // We need to call the main function for cheerpj to work. Not sure why...
    public static void main(String[] args) {    
        //test();    
    }

    // Verify the validity of JML file(s) whose paths are given as command-line
    // arguments. For pattern lists the validity of each line within the list is
    // verified.
    /*
     * private static void doVerify(Path outpath, AnimationPrefs jc) {
     * JMLParser parser = new JMLParser();
     * try {
     * parser.parse(new FileReader(file));
     * }
     * 
     * if (parser.getFileType() == JMLParser.JML_PATTERN) {
     * try {
     * JMLPattern pat = new JMLPattern(parser.getTree());
     * // JMLPattern.fromBasePattern("siteswap", config);
     * 
     * pat.layoutPattern();
     * }
     * }
     * }
     * }
     */

    // Output an animated GIF of the pattern
    /*
     * private static void doTogif(JMLPattern pat, Path outpath, AnimationPrefs jc)
     * {
     * Animator anim = new Animator();
     * if (jc == null) {
     * jc = anim.getAnimationPrefs();
     * jc.fps = 33.3; // default frames per sec for GIFs
     * }
     * anim.setDimension(new Dimension(jc.width, jc.height));
     * anim.restartAnimator(pat, jc);
     * anim.writeGIF(new FileOutputStream(outpath.toFile()), null, jc.fps);
     * }
     */
}

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
    public static void main(String[] args) {
        System.out.println("MAIN 4");
        try {
            System.out.println("TRY");
            JMLPattern pattern = JMLPattern.fromBasePattern("siteswap", "531");
            pattern.layoutPattern();
            Pattern proto = Serializer.serializePattern(pattern);
            System.out.println("MADE PATTERN");
            System.out.println(proto);
            String encoded = Base64.getEncoder().encodeToString(proto.toByteArray());
            System.out.println(encoded);

            Path path = Paths.get("www/resources/test2.js");
            try {
                Files.write(path, encoded.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }


            /*System.out.println("Paths: " + pattern.getNumberOfPaths());
            Coordinate c = new Coordinate();
            pattern.getHandCoordinate(1, 0, 0.1, c);
            System.out.println(c.toString());*/
        } catch (JuggleExceptionUser e) {
            System.out.println("Failure" + e.getMessage());
        } catch (JuggleExceptionInternal e) {
            System.out.println("Failure" + e.getMessage());
        }
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

// JugglingLab.java
//
// Copyright 2002-2022 Jack Boyce and the Juggling Lab contributors

package jugglinglab;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.xml.sax.SAXException;

import jugglinglab.core.*;
import jugglinglab.jml.JMLParser;
import jugglinglab.jml.JMLPattern;
import jugglinglab.jml.JMLPatternList;
import jugglinglab.generator.SiteswapGenerator;
import jugglinglab.generator.SiteswapTransitioner;
import jugglinglab.generator.GeneratorTarget;
import jugglinglab.notation.Pattern;
import jugglinglab.notation.SiteswapPattern;
import jugglinglab.util.*;

public class JugglingLab {
    // TODO(tdresser): eliminate these globals.
    public static final ResourceBundle guistrings;
    public static final ResourceBundle errorstrings;
    public static final boolean isMacOS = false;
    public static final boolean isWindows = false;
    public static final boolean isLinux = false;

    static {
        guistrings = JLLocale.getBundle("GUIStrings");
        errorstrings = JLLocale.getBundle("ErrorStrings");
    }

    // Command line arguments as an ArrayList that we trim as portions are parsed
    private static ArrayList<String> jlargs;

    public static void main(String[] args) {
        System.out.println("MAIN");
        try {
            JMLPattern pattern = JMLPattern.fromBasePattern("siteswap", "531");
            pattern.layoutPattern();
            System.out.println("Paths: " + pattern.getNumberOfPaths());
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

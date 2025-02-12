// Pattern.java
//
// Copyright 2002-2022 Jack Boyce and the Juggling Lab contributors

package jugglinglab.notation;

import jugglinglab.jml.JMLPattern;
import jugglinglab.util.*;


// This is the base class for all non-JML pattern types in Juggling Lab.
// It parses from a string representation and creates a JMLPattern version of
// itself for the animator.

public abstract class Pattern {
    // The built-in notations
    public static final String[] builtinNotations = { "Siteswap" };

    // these should be in the same order as in the builtinNotations array
    public static final int NOTATION_NONE = 0;
    public static final int NOTATION_SITESWAP = 1;

    // creates a new blank pattern in the given notation
    public static Pattern newPattern(String notation) throws JuggleExceptionUser,
                                                JuggleExceptionInternal {
        System.out.println("newPattern A");

        if (notation == null)
            throw new JuggleExceptionUser("Notation type not specified");

        System.out.println("newPattern B");


        if (notation.equalsIgnoreCase("siteswap")) {
            System.out.println("It's an SSP");
            return new SiteswapPattern();
        }

        System.out.println("newPattern C");

        throw new JuggleExceptionUser("Notation type '"+notation+"' not recognized");
    }

    // returns the notation name with canonical capitalization
    public static String canonicalNotation(String notation) {
        for (String n : builtinNotations) {
            if (n.equalsIgnoreCase(notation))
                return n;
        }
        return null;
    }

    // return the notation name
    public abstract String getNotationName();

    // define pattern from textual representation
    public abstract Pattern fromString(String config) throws
                        JuggleExceptionUser, JuggleExceptionInternal;

    // define pattern from ParameterList input
    public abstract Pattern fromParameters(ParameterList pl) throws
                        JuggleExceptionUser, JuggleExceptionInternal;

    // canonical string representation
    public abstract String toString();

    // convert pattern to JML
    public abstract JMLPattern asJMLPattern() throws
                        JuggleExceptionUser, JuggleExceptionInternal;
}

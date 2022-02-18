// Prop.java
//
// Copyright 2002-2022 Jack Boyce and the Juggling Lab contributors

package jugglinglab.prop;

import alternatives.Color;
import alternatives.Dimension;

import jugglinglab.util.*;


// This is the base type of all props in Juggling Lab.

public abstract class Prop {
    protected String initString;

    public static final String[] builtinProps =
        {
            "Ball",
            "Image",
            "Ring",
        };


    // Creates a new prop of the given type
    public static Prop newProp(String type) throws JuggleExceptionUser {
        // TODO(tdresser): re-add support for other prop types.
        return new BallProp();
    }

    public void initProp(String st) throws JuggleExceptionUser {
        initString = st;
        init(st);
    }

    //-------------------------------------------------------------------------
    // Abstract methods defined by subclasses
    //-------------------------------------------------------------------------

    public abstract String getType();

    public abstract Color getEditorColor();

    public abstract ParameterDescriptor[] getParameterDescriptors();

    protected abstract void init(String st) throws JuggleExceptionUser;

    public abstract Coordinate getMax();  // in cm

    public abstract Coordinate getMin();  // in cm

    public abstract double getWidth();  // prop width in cm

    public abstract Dimension getProp2DSize(double zoom);

    public abstract Dimension getProp2DCenter(double zoom);

    public abstract Dimension getProp2DGrip(double zoom);
}

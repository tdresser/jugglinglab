// MHNBody.java
//
// Copyright 2019 by Jack Boyce (jboyce@gmail.com)

package jugglinglab.notation;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import jugglinglab.jml.JMLPosition;
import jugglinglab.util.*;


// This class parses the "body" parameter in MHN notation.

public class MHNBody {
    static final ResourceBundle guistrings = jugglinglab.JugglingLab.guistrings;
    static final ResourceBundle errorstrings = jugglinglab.JugglingLab.errorstrings;

    protected int jugglers = 0;
    protected int[] size;
    protected int[][] coords;
    protected double[][][][] bodypath;

    public MHNBody(String bodies) throws JuggleExceptionUser, JuggleExceptionInternal {
        // delete the '<' and '>' characters
        String pat = "[" + Pattern.quote("<>{}") + "]";
        bodies = bodies.replaceAll(pat, "");

        StringTokenizer st1 = new StringTokenizer(bodies, "|!", false);
        jugglers = st1.countTokens();

        size = new int[jugglers];
        coords = new int[jugglers][];
        bodypath = new double[jugglers][][][];

        for (int j = 0; j < jugglers; j++) {
            String str = st1.nextToken();
            // System.out.println("str["+j+"] = "+str);

            for (int k = 0; k < 3; k++) {
                int pos = 0;
                int numcoords = 0;

                for (int l = 0; l < str.length(); ) {
                    char ch = str.charAt(l);

                    if (ch == ' ') {
                        l++;
                        continue;
                    }
                    if (ch == '.') {
                        if (numcoords == 0) {
                            if (k == 1) {
                                coords[j][pos] = 1;
                                bodypath[j][pos] = new double[1][];
                                bodypath[j][pos][0] = null;
                            }
                        } else if (k == 1) {
                            coords[j][pos] = numcoords;
                            bodypath[j][pos] = new double[numcoords][];
                        }
                        pos++;
                        numcoords = 0;
                        l++;
                        continue;
                    }
                    if (ch == '-') {
                        if (k == 2)
                            bodypath[j][pos][numcoords] = null;
                        numcoords++;
                        l++;
                        continue;
                    }
                    if (ch == '(') {
                        int endindex = str.indexOf(')', l+1);
                        if (endindex < 0)
                            throw new JuggleExceptionUser(errorstrings.getString("Error_body_noparen"));
                        if (k == 2) {
                            bodypath[j][pos][numcoords] = new double[4];
                            bodypath[j][pos][numcoords][3] = 100.0;     // default z

                            String str2 = str.substring(l+1, endindex);

                            try {
                                StringTokenizer st4 = new StringTokenizer(str2, ",", false);
                                bodypath[j][pos][numcoords][0] =
                                    Double.valueOf(st4.nextToken()).doubleValue();
                                if (st4.hasMoreTokens())
                                    bodypath[j][pos][numcoords][1] =
                                        Double.valueOf(st4.nextToken()).doubleValue();
                                if (st4.hasMoreTokens())
                                    bodypath[j][pos][numcoords][2] =
                                        Double.valueOf(st4.nextToken()).doubleValue();
                                if (st4.hasMoreTokens())
                                    bodypath[j][pos][numcoords][3] =
                                        Double.valueOf(st4.nextToken()).doubleValue();
                            } catch (NumberFormatException e) {
                                throw new JuggleExceptionUser(errorstrings.getString("Error_body_coordinate"));
                            } catch (NoSuchElementException e) {
                                throw new JuggleExceptionInternal("No such element exception in MHNBody");
                            }
                        }
                        numcoords++;
                        l = endindex + 1;
                        continue;
                    }

                    String template = errorstrings.getString("Error_body_character");
                    Object[] arguments = { Character.toString(ch) };
                    throw new JuggleExceptionUser(MessageFormat.format(template, arguments));
                }

                if (k == 0) {
                    size[j] = pos;
                    coords[j] = new int[pos];
                    bodypath[j] = new double[pos][][];
                }

                if (numcoords != 0)
                    throw new JuggleExceptionUser(errorstrings.getString("Error_body_badending"));
            }
        }

    }

    public int getNumberOfJugglers() {
        return jugglers;
    }

    public int getPeriod(int juggler) {
        int j = (juggler - 1) % jugglers;
        return size[j];
    }

    public int getNumberOfPositions(int juggler, int pos) {
        int j = (juggler - 1) % jugglers;
        return coords[j][pos];
    }

    // pos and index start from 0:
    public JMLPosition getPosition(int juggler, int pos, int index) {
        if (pos >= getPeriod(juggler) || index >= getNumberOfPositions(juggler, pos))
            return null;
        int j = (juggler - 1) % jugglers;
        if (bodypath[j][pos][index] == null)
            return null;
        JMLPosition result = new JMLPosition();
        result.setJuggler(juggler);
        result.setCoordinate(new Coordinate(bodypath[j][pos][index][1], bodypath[j][pos][index][2],
                                            bodypath[j][pos][index][3]));
        result.setAngle(bodypath[j][pos][index][0]);
        return result;
    }
}

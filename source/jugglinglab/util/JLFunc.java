// JLFunc.java
//
// Copyright 2002-2022 Jack Boyce and the Juggling Lab contributors

package jugglinglab.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Some useful functions

public class JLFunc {
    // Binomial coefficient (a choose b)
    public static int binomial(int a, int b) {
        int result = 1;

        for (int i = 0; i < b; i++) {
            result *= (a - i);
            result /= (i + 1);
        }

        return result;
    }

    // Throughout Juggling Lab we use a notation within strings to indicate
    // repeated sections:  `...(stuff)^n...`. This function expands all such
    // repeats (including nested ones) to produce a fully-expanded string.
    //
    // NOTE: A limitation is that if `stuff` contains parentheses they must
    // be balanced. Otherwise we get ambiguous cases like '(()^5' --> does this
    // expand to '(((((' or '('?
    public static String expandRepeats(String str) {
        StringBuffer sb = new StringBuffer();
        addExpansionToBuffer(str, sb);
        return sb.toString();

        /*
        System.out.println(JLFunc.expandRepeats("hello"));
        System.out.println(JLFunc.expandRepeats("he(l)^2o"));
        System.out.println(JLFunc.expandRepeats("hel(lo)^2"));
        System.out.println(JLFunc.expandRepeats("(hello)^0world"));
        System.out.println(JLFunc.expandRepeats("((hello )^2there)^2"));
        System.out.println(JLFunc.expandRepeats("((hello )there)^2"));
        System.out.println(JLFunc.expandRepeats("((hello )^2there)"));
        */
    }

    protected static void addExpansionToBuffer(String str, StringBuffer sb) {
        for (int pos = 0; pos < str.length(); ) {
            char ch = str.charAt(pos);

            if (ch == '(') {
                int[] result = tryParseRepeat(str, pos);

                if (result == null) {
                    // no repeat found, treat like a normal character
                    sb.append(ch);
                    pos++;
                } else {
                    int repeat_end = result[0];
                    int repeats = result[1];
                    int resume_start = result[2];

                    // snip out the string to be repeated:
                    String str2 = str.substring(pos + 1, repeat_end);

                    for (int i = 0; i < repeats; i++)
                        addExpansionToBuffer(str2, sb);

                    pos = resume_start;
                }
            } else {
                sb.append(ch);
                pos++;
            }
        }
    }

    // Scan forward in the string to find:
    // (1) the end of the repeat (buffer position of ')' where depth returns to 0)
    // (2) the number of repeats
    //     - if the next non-whitespace char after (a) is not '^' -> no repeat
    //     - if the next non-whitespace char after '^' is not a number -> no repeat
    //     - parse the numbers after '^' up through the first non-number (or end
    //       of string) into an int = `repeats`
    // (3) the buffer position of the first non-numeric character after the
    //     repeat number (i.e. where to resume) = `resume_start`
    //     (=str.length() if hit end of buffer)
    //
    // We always call this function with `fromPos` sitting on the '(' that starts
    // the repeat section.
    protected static int[] tryParseRepeat(String str, int fromPos) {
        int depth = 0;

        for (int pos = fromPos; pos < str.length(); pos++) {
            char ch = str.charAt(pos);

            if (ch == '(')
                depth++;
            else if (ch == ')') {
                depth--;
                if (depth == 0) {
                    // see if we match the form '^(int)...' after the closing
                    // parenthesis
                    Pattern pat = Pattern.compile("^\\s*\\^\\s*(\\d+).*");
                    Matcher m = pat.matcher(str.substring(pos + 1, str.length()));

                    if (!m.matches())
                        return null;

                    int repeat_end = pos;
                    int repeats = Integer.parseInt(m.group(1));
                    int resume_start = m.end(1) + pos + 1;

                    int[] result = new int[3];
                    result[0] = repeat_end;
                    result[1] = repeats;
                    result[2] = resume_start;
                    return result;
                }
            }
        }
        return null;
    }

    // Compare two version numbers
    //
    // returns 0 if equal, less than 0 if v1 < v2, greater than 0 if v1 > v2
    public static int compareVersions(String v1, String v2) {
        String[] components1 = v1.split("\\.");
        String[] components2 = v2.split("\\.");
        int length = Math.min(components1.length, components2.length);
        for (int i = 0; i < length; i++) {
            int result = Integer.valueOf(components1[i]).compareTo(Integer.parseInt(components2[i]));
            if (result != 0)
                return result;
        }
        return Integer.compare(components1.length, components2.length);
    }

    // Check if point (x, y) is near a line segment connecting (x1, y1) and
    // (x2, y2). "Near" means shortest distance is less than `slop`.
    public static boolean isNearLine(int x, int y,
                                int x1, int y1, int x2, int y2, int slop) {
        if (x < (Math.min(x1, x2) - slop) || x > (Math.max(x1, x2) + slop))
            return false;
        if (y < (Math.min(y1, y2) - slop) || y > (Math.max(y1, y2) + slop))
            return false;

        double d = (x2 - x1)*(y - y1) - (x - x1)*(y2 - y1);
        d = Math.abs(d) / Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));

        return (int)d <= slop;
    }

    //-------------------------------------------------------------------------
    // Helpers for converting numbers to/from strings
    //-------------------------------------------------------------------------

    protected static NumberFormat nf;

    public static NumberFormat nf() {
        if (nf == null) {
            // use US-style number formatting for interoperability of JML
            // files across Locales
            nf = NumberFormat.getInstance(Locale.US);
        }
        return nf;
    }

    public static double parseDouble(String s) throws NumberFormatException {
        try {
            return nf().parse(s).doubleValue();
        } catch (ParseException pe) {
            throw new NumberFormatException();
        }
    }

    // Convert a double value to a String, rounding to `digits` places after
    // the decimal point, with trailing '.' and '0's suppressed
    public static String toStringRounded(double val, int digits) {
        String fmt = "###.##########".substring(0, digits <= 0 ? 3 : 4 + Math.min(10, digits));
        DecimalFormat formatter = new DecimalFormat(fmt, new DecimalFormatSymbols(Locale.US));
        String result = formatter.format(val);

        if (result.equals("-0"))  // strange quirk
            result = "0";

        return result;
    }
}

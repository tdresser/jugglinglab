package web_bindings;

import jugglinglab.util.Coordinate;

public class CoordinateSerializer {
    public static int length() {
        return 3;
    }
    public static int serializeTo(double[] result, int start, Coordinate it) {
        result[start++] = it.x;
        result[start++] = it.y;
        result[start++] = it.z;
        return start;
    }
}

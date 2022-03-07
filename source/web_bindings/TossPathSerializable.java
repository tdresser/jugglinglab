package web_bindings;

import jugglinglab.path.TossPath;


public class TossPathSerializable extends TossPath {
    static int length() {
        return 7 + CoordinateSerializer.length() * 2;
    }
    int serializeTo(double[] result, int start) {
        result[start++] = bx;
        result[start++] = cx;
        result[start++] = by;
        result[start++] = cy;
        result[start++] = az;
        result[start++] = bz;
        result[start++] = cz;
        result[start] = CoordinateSerializer.serializeTo(result, start, getMax());
        result[start] = CoordinateSerializer.serializeTo(result, start, getMin());
        return start;
    }
}

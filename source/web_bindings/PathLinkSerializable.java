package web_bindings;

import jugglinglab.jml.JMLEvent;
import jugglinglab.jml.PathLink;

public class PathLinkSerializable extends PathLink {
    public PathLinkSerializable(int pathnum, JMLEvent startevent, JMLEvent endevent) {
        super(pathnum, startevent, endevent);
    }
    
    static int length() {
        return 0; // TODO
    }
    int serializeTo(double[] result, int start) {
        result[start++] = getStartEvent().getT();
        result[start++] = getEndEvent().getT();
        return start;
    }
}

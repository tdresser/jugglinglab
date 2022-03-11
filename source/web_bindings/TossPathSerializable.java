package web_bindings;

import jugglinglab.jml.PathLink;
import jugglinglab.path.TossPath;
import jugglinglab.protos.PatternOuterClass.Path;

public class TossPathSerializable extends TossPath {
    // It's a bit weird to take a pathLink here, but it's better than splitting up initialization.
    Path.Builder serialize (PathLink pathLink) {
        return Path.newBuilder()
            .setStartTime(pathLink.getStartEvent().getT())
            .setEndTime(pathLink.getEndEvent().getT())
            .setStart(Serializer.vec3(cx, cy, cz))
            .setVel(Serializer.vec3(bx, by, bz))
            .setAcc(Serializer.vec3(0.0, 0.0, az));
    }
}

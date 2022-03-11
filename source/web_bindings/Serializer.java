package web_bindings;

import java.util.ArrayList;

import jugglinglab.jml.JMLPattern;
import jugglinglab.jml.PathLink;
import jugglinglab.protos.PatternOuterClass.Path;
import jugglinglab.protos.PatternOuterClass.Pattern;
import jugglinglab.protos.PatternOuterClass.Vec3;
import jugglinglab.util.Coordinate;

public class Serializer {

  static Vec3.Builder vec3(double x, double y, double z) {
    return Vec3.newBuilder().setX(x).setY(y).setZ(z);
  }

  public static Pattern serializePattern(JMLPattern pattern) {
    Pattern.Builder builder = Pattern.newBuilder();

    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;

    for (ArrayList<PathLink> pathLinks : pattern.getPathLinks()) {
      for (PathLink pathLink : pathLinks) {
        TossPathSerializable path = (TossPathSerializable) pathLink.getPath();
        if (path == null) {
          continue;
        }
        builder.addPaths(path.serialize(pathLink));
        Coordinate min = path.getMin();
        Coordinate max = path.getMin();
        minX = Math.min(minX, min.x);
        minY = Math.min(minY, min.y);
        maxX = Math.max(maxX, max.x);
        maxY = Math.max(maxY, max.y);
      }
    }

    builder.setMinX(minX);
    builder.setMinY(minY);
    builder.setMaxX(maxX);
    builder.setMaxY(maxY);

    return builder.build();
  }

  Path path = Path.newBuilder()
      .setAcc(Vec3.newBuilder()
          .setX(0)
          .setY(0)
          .setZ(0)
          .build())
      .build();
}

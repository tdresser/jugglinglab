import {Vec3} from 'vec3';
import {Pattern as PatternProto} from '../resources/protos/Pattern.proto';
import {Path} from './path';

export class Pattern {
  readonly paths: Path[];
  readonly min: Vec3;
  readonly max: Vec3;

  constructor(proto: PatternProto) {
    this.paths = proto.paths.map((x) => new Path(x));
    this.min = Vec3.fromProto(proto.min);
    this.max = Vec3.fromProto(proto.max);
  }
}

import {Path as PathProto} from '../resources/protos/Pattern.proto';
import {Vec3} from './vec3';

export class Path {
  start : Vec3;
  vel : Vec3;
  acc : Vec3;
  startTime: number;
  endTime: number;

  constructor(proto: PathProto) {
    this.start = Vec3.fromProto(proto.start);
    this.vel = Vec3.fromProto(proto.vel);
    this.acc = Vec3.fromProto(proto.acc);

    this.startTime = proto.startTime || 0;
    this.endTime = proto.endTime || 0;

    console.log('START TIME IN PROTO: ' + proto.startTime);
    console.log('END TIME IN PROTO: ' + proto.endTime);
  }

  getPositionAtTime(time: number) : Vec3 | null {
    if ((time < this.startTime) || (time > this.endTime)) {
      return null;
    }
    time -= this.startTime;
    return this.start.add(
        this.vel.mul(time)).add(
        this.acc.mul(time*time));
  }
}

import { Path as PathProto } from "../resources/protos/Pattern.proto";
import { Vec3 } from "./vec3";

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
    }

    getPositionAtTime(time: number) : Vec3 | null {
        const t = this;
        if ((time < t.startTime) || (time > t.endTime))
            return null;
        time -= this.startTime;
        return t.start.add(t.vel.mul(time)).add(t.acc.mul(time*time));
    }
}
import { Vec3 as Vec3Proto } from "../resources/protos/Pattern.proto";

export class Vec3 {
    x:number;
    y:number;
    z:number;

    static fromProto(proto: Vec3Proto) : Vec3 {
        return new Vec3(proto.x, proto.y, proto.z);
    }

    constructor(x:number, y:number, z:number) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    mul(m: number): Vec3 {
        return new Vec3(this.x * m, this.y * m, this.z * m);
    }
    add(o: Vec3) : Vec3 {
        return new Vec3(this.x + o.x, this.y + o.y, this.z + o.z);
    }
}
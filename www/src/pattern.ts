import { Pattern as PatternProto, Path as PathProto } from "../resources/protos/Pattern.proto";
import { Path } from "./path";

export class Pattern {
    #proto: PatternProto;
    paths: Path[];

    constructor(proto: PatternProto) {
        this.#proto = proto;
        this.paths = this.#proto.paths.map(x => new Path(x));
    }

    getBoundingRect() : number[] {
        return [this.#proto.minX, this.#proto.maxX, this.#proto.minY, this.#proto.maxY];
    }

    getPaths(): Path[] {
        return this.paths;
    }
}
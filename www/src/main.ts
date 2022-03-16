import {decodePattern} from '../resources/protos/Pattern.proto';
import {getTestBase64Proto} from '../resources/test';
import {decode} from 'base64-arraybuffer';
import {Pattern} from 'pattern';

const b64 = getTestBase64Proto();
const buffer = decode(b64);
const patternProto = decodePattern(new Uint8Array(buffer));

const pattern = new Pattern(patternProto);

for (let i = 0; i < 1000; ++i) {
  for (const path of pattern.getPaths()) {
    const v = path.getPositionAtTime(i++/100.0);
    if (v != null) {
      console.log(v);
    }
  }
}


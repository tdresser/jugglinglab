import {decodePattern} from '../resources/protos/Pattern.proto';
// import {getTestBase64Proto} from '../resources/test';
// import {decode} from 'base64-arraybuffer';
import {Pattern} from 'pattern';


async function getPattern(ss:string):Promise<Pattern> {
  const protoArray : Uint8Array = await cjCall(
      'jugglinglab.JugglingLab', 'getPatternProto', ss);
  const patternProto = decodePattern(protoArray);
  const pattern = new Pattern(patternProto);
  console.log(pattern);
  return pattern;
}
/*

for (let i = 0; i < 1000; ++i) {
  for (const path of pattern.paths) {
    const v = path.getPositionAtTime(i++/100.0);
    if (v != null) {
      console.log(v);
    }
  }
}*/

/* for (int i = 0; i < pat.getPeriod(); i++)  {
            double time = pat.getLoopStartTime();

            for (int j = 0; j < gif_num_frames; j++) {
                drawFrame(time, g, false, true);*/

// console.log(pattern.min);
// console.log(pattern.max);



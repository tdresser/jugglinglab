import {decodePattern} from '../resources/protos/Pattern.proto';
// import {getTestBase64Proto} from '../resources/test';
import {decode} from 'base64-arraybuffer';
import {Pattern} from 'pattern';

async function getPattern(ss:string):Promise<Pattern> {
  const protoArray : Int8Array = await cjCall(
      'jugglinglab.JugglingLab', 'getPatternProto', ss);
  // cheerpj uses the first byte. See https://github.com/leaningtech/cheerpj-meta/issues/91.
  const protoArrayHack : Int8Array = protoArray.subarray(1);
  console.log('no hack');
  console.log(protoArray);
  console.log('hack');
  console.log(protoArrayHack);


  const b64 = await cjCall(
      'jugglingLab.JugglingLab', 'getPatternProtoBase64', ss);
  const protoArrayFromBase64 = decode(b64);
  console.log('from base64');
  console.log(protoArrayFromBase64);

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const patternProto = decodePattern(protoArrayFromBase64 as any as Uint8Array);
  const pattern = new Pattern(patternProto);
  console.log(pattern);
  return pattern;
}

export async function main() {
  await cheerpjInit();
  await cheerpjRunMain('jugglinglab.JugglingLab', 'resources/JugglingLab.jar');
  const pattern = await getPattern('441');
  console.log(pattern);
}
export function assert(condition:boolean) {
  if (!condition) {
    console.warn('Assertion failed');
  }
}
main();
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



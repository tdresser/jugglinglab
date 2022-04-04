import {decodePattern} from '../resources/protos/Pattern.proto';
// import {getTestBase64Proto} from '../resources/test';
import {decode} from 'base64-arraybuffer';
import {Pattern} from 'pattern';
// import '../resources/loader';

// TODO: maybe prevent bare use of cjCall, and add a shim
// that makes sure data type conversions have taken place.

async function getPattern(ss:string):Promise<Pattern> {
  console.log('get pattern: ' + ss);
  const ssJavaStr = cjStringJsToJava(ss);
  console.log('java str: ' + ssJavaStr);
  console.log('Get pattern proto');
  // TODO: figure out what's going on here. The result of cjCall has no buffer.
  const protoArray : Uint8Array = new Uint8Array(await cjCall(
      'jugglinglab.JugglingLab', 'getPatternProto', ssJavaStr));
  // cheerpj uses the first byte. See https://github.com/leaningtech/cheerpj-meta/issues/91.
  const protoArrayHack : Uint8Array = protoArray.subarray(1);

  console.log('About to cjCall');
  const b64 = await cjCall(
      'jugglinglab.JugglingLab', 'getPatternProtoBase64', ssJavaStr);
  console.log('Done cjCall');
  console.log('base 64');
  console.log(cjStringJavaToJs(b64));

  const protoArrayFromBase64 = new Uint8Array(decode(cjStringJavaToJs(b64)));
  console.log('no hack');
  console.log(protoArray);
  console.log('hack');
  console.log(protoArrayHack);
  console.log('from base64');
  console.log(protoArrayFromBase64);

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const patternProto = decodePattern(protoArrayHack);
  console.log('Decode Result');
  console.log(patternProto);
  const pattern = new Pattern(patternProto);
  console.log(pattern);

  cjCall('jugglinglab.JugglingLab', 'test');
  return pattern;
}

export async function main() {
  await cheerpjInit();
  console.log('ABOUT TO RUN MAIN');
  // Don't know why, but this is required.
  await cheerpjRunMain('jugglinglab.JugglingLab', 'resources/JugglingLab.jar');
  console.log('RAN MAIN');
  const pattern = await getPattern('531');
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



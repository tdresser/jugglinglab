import { decodePattern, Pattern } from "../resources/protos/Pattern.proto";
import { getTestBase64Proto } from "../resources/test";

const b64 = getTestBase64Proto();
const buffer = Buffer.from(b64, 'base64');
const pattern = decodePattern(buffer);

console.log(pattern);
import { mkdir, cp, readdir, writeFile, readFile } from 'fs/promises';

import { parseSchema } from 'pbjs';

/* require('esbuild').build({
    entryPoints: ['app.jsx'],
    bundle: true,
    outfile: 'out.js',
  }).catch(() => process.exit(1))
  */

async function main() {
    const PROTO_PATH = "../source/protos/";
    const protos = await readdir(PROTO_PATH)
    for (const proto of protos) {
        console.log(proto);
        const protoContents = await readFile(PROTO_PATH + proto);
        const schema = parseSchema(protoContents);
        const ts = schema.toTypeScript();
        await writeFile("resources/protos/" + proto + ".ts", ts);
    }
}

main();
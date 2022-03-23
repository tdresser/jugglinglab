import {readdir, writeFile, readFile} from 'fs/promises';
import {parseSchema} from 'pbjs';
import {build, serve} from 'esbuild';
import path from 'path';

const BUILD_OPTIONS = {
  entryPoints: ['src/main.ts'],
  bundle: true,
  outfile: 'build/out.js',
};

async function main() {
  const PROTO_PATH = '../source/protos/';
  const protos = await readdir(PROTO_PATH);
  for (const proto of protos) {
    console.log(proto);
    const protoContents = await readFile(PROTO_PATH + proto);
    const schema = parseSchema(protoContents);
    const ts = schema.toTypeScript();
    await writeFile('resources/protos/' + proto + '.ts', ts);
  }

  serve({
    servedir: path.resolve(),
  }, BUILD_OPTIONS).then((result) => {
    console.log(result);
  });

  /* watch: {
    onRebuild(error, result) {
      if (error) console.error('watch build failed:', error);
      else console.log('watch build succeeded:', result);
    },
  },*/

  /* build(BUILD_OPTIONS).catch(() => { // TODO:, readd watch
    console.log('Something terrible happened');
  });*/
}

main();



import {readdir, writeFile, readFile} from 'fs/promises';
import {parseSchema} from 'pbjs';
import {build} from 'esbuild';

// Can't use esbuild serve, as it doesn't support HEAD requests,
// which cheerpj requires.

const BUILD_OPTIONS = {
  entryPoints: ['src/main.ts'],
  bundle: true,
  outfile: 'build/out.js',
  sourcemap: 'inline',
  watch: {
    onRebuild(error, result) {
      if (error) console.error('watch build failed:', error);
      else console.log('watch build succeeded:', result);
    },
  },
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

  build(BUILD_OPTIONS).catch(() => {
    console.log('Something terrible happened');
  });
}

main();



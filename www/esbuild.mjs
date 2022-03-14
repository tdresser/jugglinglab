import { mkdir, cp, readdir, writeFile, readFile } from "fs/promises";
import { parseSchema } from "pbjs";
import { build } from "esbuild";

async function main() {
  const PROTO_PATH = "../source/protos/";
  const protos = await readdir(PROTO_PATH);
  for (const proto of protos) {
    console.log(proto);
    const protoContents = await readFile(PROTO_PATH + proto);
    const schema = parseSchema(protoContents);
    const ts = schema.toTypeScript();
    await writeFile("resources/protos/" + proto + ".ts", ts);
  }
}

main();

build({
  entryPoints: ["src/main.ts"],
  bundle: true,
  outfile: "build/out.js",
}).catch(() => {
    console.log("Something terrible happened");
});

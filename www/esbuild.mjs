import { mkdir, cp } from 'fs/promises';

/* require('esbuild').build({
    entryPoints: ['app.jsx'],
    bundle: true,
    outfile: 'out.js',
  }).catch(() => process.exit(1))
  */

async function main() {
    try {
        await mkdir("resources");
    } catch {
    }
    //await cp("../bin/JugglingLabMonolithProguarded.jar", "resources/JugglingLab.jar");
    //await cp("../bin/JugglingLabMonolithProguarded.jar.js", "resources/JugglingLab.jar.js");
    await cp("../bin/JugglingLabMonolith.jar", "resources/JugglingLab.jar");
    await cp("../bin/JugglingLabMonolith.jar.js", "resources/JugglingLab.jar.js");
}

main();
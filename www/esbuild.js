const fs = require('fs/promises');

/* require('esbuild').build({
    entryPoints: ['app.jsx'],
    bundle: true,
    outfile: 'out.js',
  }).catch(() => process.exit(1))
  */

async function main() {
    try {
        await fs.mkdir("resources");
    } catch {
    }
    await fs.cp("../bin/JugglingLabMonolithProguarded.jar", "resources/JugglingLab.jar");
    await fs.cp("../bin/JugglingLabMonolithProguarded.jar.js", "resources/JugglingLab.jar.js");
}

main();
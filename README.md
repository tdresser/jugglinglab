# Working on getting JugglingLab doing something in the browser

# Juggling Lab juggling animator

Juggling Lab is an open-source application for creating and animating juggling patterns. Its main goals are to help people learn juggling patterns, and to assist with inventing new ones.

The [project site](https://jugglinglab.org) has more information and download links.

There is also a [web service](https://jugglinglab.org/html/animinfo.html) to generate animated GIFs from a pattern description.

## The code

Juggling Lab is written in Java and uses standard Swing components, and it should run on any platform supported by Java 11 or later. The ``build.xml`` file defines Ant build targets to compile and run the code.

Please feel free to clone the repository and play around with it. Even better, contribute a bug report, bug fix, pattern file, or a new feature!

## Contributors

Juggling Lab has been in development since 1997 – the earliest days of the Java language. It started as an AWT applet running in a browser, then migrated to Swing with the release of Java 1.2. Eventually Juggling Lab morphed into the desktop application it is today.

Over that long span of time the project has seen contributions from many people, including:

- Jack Boyce – Most Juggling Lab code, project administration
- Roman Auvrey – Fixes to language localization code, French language translation
- Daniel Bareket – Hebrew translations
- Dominik Braun – Fun With Juggling Lab patterns, and many design ideas including camangle, showground, and hidejugglers settings, and the '^' repeat notation
- Vincent Bruel – Suggestions for improved bouncing support (hyperlift/hyperforce patterns), ball-bounce audio sample
- Brian Campbell – Bookmarklet
- Jason Haslam – Ring prop, bitmapped-image prop, improved ball graphic, visual editor enhancements, internationalization of user interface including Spanish and Portuguese translations, and many bug fixes
- Steve Healy (JAG) – Many invaluable design suggestions and bug reports, especially of siteswap notation component
- Anselm Heaton – Orbit-finding code, other design suggestions
- Lewis Jardine – Apache Ant build file, GPL clarifications
- [Roman Karavia](https://github.com/rkaravia) – CheerpJ conversion to run Juggling Lab in the browser
- Ken Matsuoka – JuggleMaster pattern library, used here with his permission
- Rupert Millard – Implementation of '*' shortcut for sync notation
- Herve Nicol – Bug fixes
- Denis Paumier – Suggestions for passing and multiplexing improvements to siteswap generator
- Andrew Peterson – Performance profiling of animation routines
- Romain Richard and Frédéric Rayar – [Android version](https://code.google.com/archive/p/jugglinglab/) ([Google Play](https://play.google.com/store/apps/details?id=com.jonglen7.jugglinglab))
- Frédéric Roudaut – Design ideas for siteswap notation component, French language translation
- Daniel Simu – Bug reports, many design suggestions
- P. R. Vaidyanathan – Hand Siteswap feature input
- Xavier Verne – French translation of user interface
- Johannes Waldmann – Source code documentation
- Mahit Warhadpande – Hand Siteswap feature design and implementation
- Alan Weathers – Alanz pattern files, bug reports, many application improvement ideas

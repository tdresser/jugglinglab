// Gradle: https://docs.gradle.org/7.4/userguide/building_java_projects.html
// Proguard: https://www.guardsquare.com/manual/setup/gradle
// Kotlin DSL: https://docs.gradle.org/current/userguide/kotlin_dsl.html

import de.undercouch.gradle.tasks.download.Download

val cheerpjfyPy = File(buildDir, "cheerpJ/cheerpj_2.2/cheerpjfy.py")

plugins {
    // TODO - switch to library.
    // Apply the java-library plugin for API and implementation separation.
    //`java-library`
    application
    id("de.undercouch.download") version "5.0.1"
}

// APPLICATION BITS
application {
    mainClass.set("jugglingLab.JugglingLab")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "jugglinglab.JugglingLab"
    }
}

// END APPLICATION BITS

buildscript {
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.1.0")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
   mavenCentral()
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("source"))
        }
    }
}

// https://kotlinlang.org/docs/gradle.html#dependency-types
dependencies {
    // Should this be api? api doesn't exist for an application.
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.ortools:ortools-java:9.2.9972")
}

// TODO - why doesn't this work?
// defaultTasks("upper")

val uberJar = tasks.register<Jar>("uberJar") {
    dependsOn("jar")
    archiveClassifier.set("uber")

    // manifest {
    //    attributes["Main-Class"] = "jugglinglab.JugglingLab"
    //}

    from(sourceSets.main.get().output)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

val proguard = tasks.register<proguard.gradle.ProGuardTask>("proguard") {
    val input = uberJar.get().archiveFile.get()
    val result by extra(File(buildDir, "Proguarded.jar"))
    dependsOn("uberJar")
    injars(input)
    outjars(result)
    configuration("buildconfig/proguard_config.pro")
}

val downloadCheerpJ = tasks.register<Download>("downloadCheerpJ") {
    src("https://d3415aa6bfa4.leaningtech.com/cheerpj_linux_2.2.tar.gz")
    dest(File(buildDir, "cheerpj_linux_2.2.tar.gz"))
    onlyIfModified(true)
    // useETag(true) // TODO: do we want this?
}

tasks.register<Copy>("unzipCheerpJ") {
    dependsOn("downloadCheerpJ")
    from(tarTree(downloadCheerpJ.get().dest))
    into(File(buildDir, "cheerpJ"))
}

val cheerpjfyRelease = tasks.register<Exec>("cheerpjfyRelease") {
    val result by extra(File(buildDir, "Cheerpjfied.jar"))
    val inJar = proguard.get().extra.get("result") as File
    // Can't customize this, it's based on the input jar.
    val jsResult by extra(File(inJar.path + ".js")) 
    dependsOn("unzipCheerpJ")
    dependsOn("proguard")
    executable("python3")
    args(
            cheerpjfyPy,
            inJar,
            "--pack-jar", result,
            "--pack-strip-binaries"
    )
}

val cheerpjfyDev = tasks.register<Exec>("cheerpjfyDev") {
    val inJar : File = tasks.named<Jar>("jar").get().archiveFile.get().asFile
    // Can't customize this, it's based on the input jar.
    val jsResult : File by extra(File(inJar.path + ".js")) 
    val jar : File by extra(inJar)
    dependsOn("unzipCheerpJ")
    dependsOn("jar")
    executable("python3")
    args(
            cheerpjfyPy,
            inJar,
    )
}

val nameCheerpjfyRelease = tasks.register<Copy>("nameCheerpjfyRelease") {
    dependsOn("cheerpjfyRelease")
    val defaultCheerpjOutput = cheerpjfyRelease.get().extra.get("jsResult") as File
    val jsResult by extra(File((cheerpjfyRelease.get().extra.get("result") as File).path + ".js"))
    from(buildDir)
    include(defaultCheerpjOutput.name)
    into(buildDir)
    rename(defaultCheerpjOutput.name, jsResult.name)
}

tasks.register<Copy>("outputWWWRelease") {
    dependsOn("nameCheerpjfyRelease")
    val jsFile = nameCheerpjfyRelease.get().extra.get("jsResult") as File;
    val jar = cheerpjfyRelease.get().extra.get("result") as File;
    from(buildDir)
    include(listOf(jsFile.name, jar.name))
    rename(jar.name + "(.*)", "JugglingLab$1")
    into(File("www/resources"))
}

tasks.register<Copy>("outputWWWDev") {
    dependsOn("cheerpjfyDev")
    val jsFile = cheerpjfyDev.get().extra.get("jsResult") as File;
    val jar = cheerpjfyDev.get().extra.get("jar") as File;
    from(jar.parent)
    include(listOf(jsFile.name, jar.name))
    rename(jar.name + "(.*)", "JugglingLab.jar$1")
    into(File("www/resources"))
}

tasks.register("release") {
    dependsOn("outputWWWRelease")
}

tasks.register("dev") {
    dependsOn("outputWWWDev")
}

tasks.register("debug") {
    //val defaultCheerpjOutput = cheerpjfy.get().extra.get("jsResult") as File
    //doLast {
    //    println(defaultCheerpjOutput.name)
    //}
}


version = "1.6.2"
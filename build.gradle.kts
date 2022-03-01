/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4/userguide/building_java_projects.html
 */

// Proguard: https://www.guardsquare.com/manual/setup/gradle
// Kotlin DSL: https://docs.gradle.org/current/userguide/kotlin_dsl.html

import de.undercouch.gradle.tasks.download.Download

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
            setSrcDirs(listOf("source/jugglinglab", "source/alternatives"))
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
    archiveClassifier.set("uber")

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
}

tasks.register<Copy>("unzipCheerpJ") {
    dependsOn("downloadCheerpJ")
    from(tarTree(downloadCheerpJ.get().dest))
    into(File(buildDir, "cheerpJ"))
}

val cheerpjfy = tasks.register<Exec>("cheerpjfy") {
    val result by extra(File(buildDir, "Cheerpjfied.jar"))
    val proguardJar = proguard.get().extra.get("result") as File
    // Can't customize this, it's based on the input jar.
    val jsResult by extra(File(proguardJar.path + ".js")) 
    dependsOn("unzipCheerpJ")
    dependsOn("proguard")
    executable("python3")
    args(
            File(buildDir, "cheerpJ/cheerpj_2.2/cheerpjfy.py"),
            (proguard.get().extra.get("result") as File),
            "--pack-jar", result,
            "--pack-strip-binaries"
    )
}

val nameCheerpjfy = tasks.register<Copy>("nameCheerpjfy") {
    dependsOn("cheerpjfy")
    val defaultCheerpjOutput = cheerpjfy.get().extra.get("jsResult") as File
    val jsResult by extra(File((cheerpjfy.get().extra.get("result") as File).path + ".js"))
    from(buildDir)
    include(defaultCheerpjOutput.name)
    into(buildDir)
    rename(defaultCheerpjOutput.name, jsResult.name)
}

tasks.register<Copy>("outputWWW") {
    dependsOn("nameCheerpjfy")
    val jsFile = nameCheerpjfy.get().extra.get("jsResult") as File;
    val jar = cheerpjfy.get().extra.get("result") as File;
    from(buildDir)
    include(listOf(jsFile.name, jar.name))
    rename(jar.name + "(.*)", "JugglingLab$1")
    into(File("www/resources"))
}

tasks.register("dev") {
    dependsOn("outputWWW")
}

tasks.register("debug") {
    val defaultCheerpjOutput = cheerpjfy.get().extra.get("jsResult") as File
    doLast {
        println(defaultCheerpjOutput.name)
    }
}


version = "1.6.2"
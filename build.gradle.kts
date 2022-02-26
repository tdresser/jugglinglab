/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4/userguide/building_java_projects.html
 */

// Proguard: https://www.guardsquare.com/manual/setup/gradle
// Kotlin DSL: https://docs.gradle.org/current/userguide/kotlin_dsl.html

plugins {
    // TODO - switch to library.
    // Apply the java-library plugin for API and implementation separation.
    //`java-library`
    application
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

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")

    from(sourceSets.main.get().output)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.register<proguard.gradle.ProGuardTask>("proguard") {
    dependsOn("uberJar")
    injars(tasks.named<Jar>("uberJar").get().archiveFileName.get())
    outjars(tasks.named<Jar>("jar").get().ar)
    configuration("buildconfig/proguard_config.pro")
}

tasks.register("dev") {
    dependsOn("proguard")
}

version = "1.6.2"
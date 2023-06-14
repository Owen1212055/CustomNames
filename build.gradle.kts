plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.owen1212055.customname"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    paperweight.paperDevBundle("1.20-R0.1-SNAPSHOT")

    implementation("xyz.jpenilla", "reflection-remapper", "0.1.0-SNAPSHOT")
}
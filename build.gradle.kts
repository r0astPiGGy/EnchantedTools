import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    application
}

group = "com.rodev.etools"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:13.0")
    compileOnly(files("/home/andrushka/libs/GuiLibrary-1.0-SNAPSHOT-all.jar"))
    compileOnly(files("/home/andrushka/libs/BUtils-1.0-API.jar"))
    compileOnly(files("/home/andrushka/libs/InterAPI-API.jar"))
    compileOnly(files("/home/andrushka/libs/GraYaml-API.jar"))
    compileOnly(files("/home/andrushka/libs/BUtils-1.0-sources.jar"))
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.10.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}
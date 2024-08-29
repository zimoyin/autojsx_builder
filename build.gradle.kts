import groovy.xml.dom.DOMCategory.attributes

plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.github.zimoyin"
version = "1.0.4-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}


dependencies {
    implementation(files("libs/apksigner/0.9/apksigner.jar"))
//    implementation(files("libs/apktool/2.9.3/apktool.jar"))

    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
    implementation("org.dom4j:dom4j:2.1.4")
    implementation("org.apktool:apktool-cli:2.9.3")
    implementation("com.formdev:flatlaf:3.5.1")
//    runtimeOnly("com.formdev:flatlaf-intellij-themes:3.5.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
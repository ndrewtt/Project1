plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    //testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("net.minidev:json-smart:2.6.0")
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-graphics:21")
    implementation("org.openjfx:javafx-base:21")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.json:json:20231013")
}

javafx {
    modules = listOf("javafx.controls", "javafx.fxml")
    version = "21"
}

tasks.test {
    useJUnitPlatform()
}

application {
   mainClass = "bsu.edu.cs.GUI"
}
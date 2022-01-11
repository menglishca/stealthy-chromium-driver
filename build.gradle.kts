plugins {
    java
    id("io.freefair.lombok") version "5.3.3.3"
}

group = "ca.menglish"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("org.seleniumhq.selenium:selenium-java:4.1.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
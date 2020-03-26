plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
    id("com.google.cloud.tools.jib") version "2.0.0"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    val jerseyVer = "2.30"
    val googleVer = "1.30.+"

    implementation(kotlin("stdlib"))

    // jersey bom
    implementation(platform("org.glassfish.jersey:jersey-bom:$jerseyVer"))
    // grizzly container
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http")
    // hk2 is jersey's own DI framework. :(
    implementation("org.glassfish.jersey.inject:jersey-hk2")
    // suppress complains
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // google api
    implementation(platform("com.google.api-client:google-api-client-bom:$googleVer"))
    implementation("com.google.api-client:google-api-client")
    implementation("com.google.api-client:google-api-client-gson")
}

val main_class by extra("io.github.arthurkamwt.myembeddedtomcat.Main")
val port by extra("8080")

application {
    mainClassName = main_class
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir("src")
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

jib {
    container {
        mainClass = main_class
        args = listOf(port)
        ports = listOf(port)

        // good defaults intended for Java 8 (>= 8u191) containers
        jvmFlags = listOf(
                "-server",
                "-Djava.awt.headless=true",
                "-XX:InitialRAMFraction=2",
                "-XX:MinRAMFraction=2",
                "-XX:MaxRAMFraction=2",
                "-XX:+UseG1GC",
                "-XX:MaxGCPauseMillis=100",
                "-XX:+UseStringDeduplication"
        )
    }
}

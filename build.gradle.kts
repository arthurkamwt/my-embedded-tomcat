import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

repositories {
    mavenCentral()
    google()
}

application {
    mainClassName = "io.github.arthurkamwt.myembeddedtomcat.Main"
}

dependencies {
    val jerseyVer = "2.30"
    val gapiVer = "1.30.+"

    implementation(kotlin("stdlib"))

    // jersey bom
    implementation(platform("org.glassfish.jersey:jersey-bom:$jerseyVer"))
    // grizzly container
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http")
    // hk2 is jersey's own DI framework. Eww!
    implementation("org.glassfish.jersey.inject:jersey-hk2")
    // suppress complains
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // google api
    implementation(platform("com.google.api-client:google-api-client-bom:$gapiVer"))
    implementation("com.google.api-client:google-api-client")
    implementation("com.google.api-client:google-api-client-gson")
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
}

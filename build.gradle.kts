import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "io.github.arthurkamwt.myembeddedtomcat.Main"
}

dependencies {
    val jerseyVer = "2.30"

    implementation(kotlin("stdlib"))

    // jersey bom
    implementation(platform("org.glassfish.jersey:jersey-bom:$jerseyVer"))
    // jersey backfill
    implementation("org.glassfish.jersey.inject:jersey-hk2")
    // grizzly container
    implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
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

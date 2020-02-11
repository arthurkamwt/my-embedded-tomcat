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
    val tomcatVer = "8.5.50"
    val jerseyVer = "2.30"

    implementation(kotlin("stdlib"))

    // embeded tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVer")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVer")
    // implementation("org.apache.tomcat.embed:tomcat-embed-logging-juli:${tomcatVer}")

    // jersey servlet
    implementation("org.glassfish.jersey.containers:jersey-container-servlet:$jerseyVer")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVer")
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
    filter {
        exclude("**/style-violations.kt")
    }
}

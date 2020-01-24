plugins {
    kotlin("jvm") version "1.3.61"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // val servletVer = "4.0.1"
    val tomcatVer = "8.5.50"
    val jerseyVer = "2.30"

    implementation(kotlin("stdlib"))

    // implementation("javax.servlet:javax.servlet-api:${servletVer}")

    // embeded tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:${tomcatVer}")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:${tomcatVer}")
    // implementation("org.apache.tomcat.embed:tomcat-embed-logging-juli:${tomcatVer}")

    // jersey servlet
    implementation("org.glassfish.jersey.containers:jersey-container-servlet:${jerseyVer}")
    implementation("org.glassfish.jersey.inject:jersey-hk2:${jerseyVer}")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
}

application {
    mainClassName = "io.github.arthurkamwt.myembeddedtomcat.Main"
}

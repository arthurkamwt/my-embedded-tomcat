package io.github.arthurkamwt.myembeddedtomcat

import java.io.File
import org.apache.catalina.startup.Tomcat
import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.servlet.ServletContainer

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val tomcat = Tomcat().apply {
            setPort(8080)
        }

        val context = tomcat.addWebapp("/", File(".").getAbsolutePath())
        Tomcat.addServlet(context, "jersey-container-servlet",
            ServletContainer(ResourceConfig().packages("io.github.arthurkamwt.myembeddedtomcat")))
        context.addServletMappingDecoded("/*", "jersey-container-servlet")

        tomcat.start()

        tomcat.getServer().await()
    }
}

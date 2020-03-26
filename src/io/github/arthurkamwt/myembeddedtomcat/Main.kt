package io.github.arthurkamwt.myembeddedtomcat

import java.net.URI
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val uri = URI.create("http://0.0.0.0:8080")
        val resourceConfig = ResourceConfig().packages("io.github.arthurkamwt.myembeddedtomcat")
        val server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false)

        // other setups if required
        server.start()

        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
        })
    }
}

package io.github.arthurkamwt.myembeddedtomcat

import java.net.URI
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = args.getOrElse(0) { "8080" }
        val resourceConfig = ResourceConfig().packages(this.javaClass.`package`.toString()).register(ConfigBinder())
        val server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:$port"), resourceConfig, false)

        // other setups if required
        server.start()

        Runtime.getRuntime().addShutdownHook(Thread {
            server.shutdown()
        })
    }
}

class ConfigBinder : AbstractBinder() {

    val ENVVARS = listOf("GOOGLE_CLIENT_ID")

    override fun configure() {
        for (envvar in ENVVARS) {
            bind(System.getenv(envvar)).to(String::class.java).named(envvar)
        }
    }
}

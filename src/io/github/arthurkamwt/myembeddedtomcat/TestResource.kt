package io.github.arthurkamwt.myembeddedtomcat

import io.github.arthurkamwt.myembeddedtomcat.annotation.RequiresAuthentication
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestResource {

    @GET
    @RequiresAuthentication
    fun test(): String {
        return "test!"
    }
}

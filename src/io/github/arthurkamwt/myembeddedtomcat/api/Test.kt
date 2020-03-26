package io.github.arthurkamwt.myembeddedtomcat.api

import io.github.arthurkamwt.myembeddedtomcat.annotation.RequiresAuthentication
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class Test {

    @GET
    @RequiresAuthentication
    fun test(): String {
        return "test!"
    }
}

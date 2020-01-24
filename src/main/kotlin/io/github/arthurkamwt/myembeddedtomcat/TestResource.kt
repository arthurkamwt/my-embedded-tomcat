package io.github.arthurkamwt.myembeddedtomcat

import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/")
class TestResource {

    @GET
    fun test(): String {
        return "test!"
    }
}

package io.github.arthurkamwt.myembeddedtomcat.filter

import javax.ws.rs.HttpMethod
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.container.PreMatching
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@PreMatching
class CorsFilter : ContainerRequestFilter, ContainerResponseFilter {

    private fun isPreflightRequest(request: ContainerRequestContext): Boolean {
        return request.getHeaderString("Origin") != null &&
            HttpMethod.OPTIONS.equals(request.getMethod())
    }

    override fun filter(request: ContainerRequestContext) {
        if (isPreflightRequest(request)) {
            request.abortWith(Response.ok().build())
        }
    }

    override fun filter(request: ContainerRequestContext, response: ContainerResponseContext) {
        if (request.getHeaderString("Origin") != null) {
            response.getHeaders().apply {
                add("Access-Control-Allow-Origin", "*")
            }
        }

        if (isPreflightRequest(request)) {
            response.getHeaders().apply {
                // add("Access-Control-Allow-Credentials", "true") // if need cookie creds
                add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            }
        }
    }
}

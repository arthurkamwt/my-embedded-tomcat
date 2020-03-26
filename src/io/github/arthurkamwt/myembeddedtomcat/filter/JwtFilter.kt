package io.github.arthurkamwt.myembeddedtomcat.filter

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.arthurkamwt.myembeddedtomcat.annotation.RequiresAuthentication
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@Priority(Priorities.AUTHORIZATION)
@RequiresAuthentication
class JwtFilter : ContainerRequestFilter {

    companion object {
        val HEADER = "Authorization"
        val SCHEME = "Bearer "
        val AUDIENCE = "1058013747467-gtq3bdfqeb4vlfqke3hue4bljop0r1ks.apps.googleusercontent.com"
    }

    // DI me
    val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf(AUDIENCE))
        .build()

    override fun filter(ctx: ContainerRequestContext) {
        ctx.getHeaderString(HEADER)
            ?.replaceFirst(SCHEME, "")
            ?.let {
                try {
                    // verify and return GoogleIdToken
                    verifier.verify(it)
                } catch (e: Exception) {
                    null
                }
            }
            ?.payload
            ?.also { println(it) }
            ?: ctx.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity("No token no access")
                    .build()
            )
    }
}

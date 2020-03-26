package io.github.arthurkamwt.myembeddedtomcat.filter

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.arthurkamwt.myembeddedtomcat.annotation.RequiresAuthentication
import javax.annotation.Priority
import javax.inject.Inject
import javax.inject.Named
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@Priority(Priorities.AUTHORIZATION)
@RequiresAuthentication
class JwtFilter @Inject constructor(@Named("GOOGLE_CLIENT_ID") val GOOGLE_CLIENT_ID: String) : ContainerRequestFilter {

    companion object {
        val HEADER = "Authorization"
        val SCHEME = "Bearer "
    }

    val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
        .setAudience(listOf(GOOGLE_CLIENT_ID))
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

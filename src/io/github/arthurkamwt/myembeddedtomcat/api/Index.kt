package io.github.arthurkamwt.myembeddedtomcat.api

import javax.inject.Inject
import javax.inject.Named
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class Index @Inject constructor(@Named("GOOGLE_CLIENT_ID") val GOOGLE_CLIENT_ID: String) {

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun index(): String {
        return """
<html lang="en">
  <head>
    <meta name="google-signin-scope" content="openid">
    <meta name="google-signin-client_id" content="$GOOGLE_CLIENT_ID">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
  </head>
  <body>
    <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
    <script>
      function onSignIn(googleUser) {
        console.log("onSignIn");
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());

        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
        window.sessionStorage.setItem('token', id_token);
      }

      function test() {
        var id_token = window.sessionStorage.getItem('token')
        fetch('http://localhost:8080/test', {
          headers: {
            'Authorization': 'Bearer ' + id_token,
            'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'application/json',
          }
        }).then((response) => {
          console.log(response);
        })
      }
    </script>
  </body>
</html>
        """.trimIndent()
    }
}

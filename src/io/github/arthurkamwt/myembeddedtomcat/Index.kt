package io.github.arthurkamwt.myembeddedtomcat

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
class Index {

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun index(): String {
        return "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta name=\"google-signin-scope\" content=\"openid\">\n" +
                "    <meta name=\"google-signin-client_id\" content=\"1058013747467-gtq3bdfqeb4vlfqke3hue4bljop0r1ks.apps.googleusercontent.com\">\n" +
                "    <script src=\"https://apis.google.com/js/platform.js\" async defer></script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"g-signin2\" data-onsuccess=\"onSignIn\" data-theme=\"dark\"></div>\n" +
                "    <script>\n" +
                "      function onSignIn(googleUser) {\n" +
                "        console.log(\"onSignIn\");\n" +
                "        // Useful data for your client-side scripts:\n" +
                "        var profile = googleUser.getBasicProfile();\n" +
                "        console.log(\"ID: \" + profile.getId()); // Don't send this directly to your server!\n" +
                "        console.log('Full Name: ' + profile.getName());\n" +
                "        console.log('Given Name: ' + profile.getGivenName());\n" +
                "        console.log('Family Name: ' + profile.getFamilyName());\n" +
                "        console.log(\"Image URL: \" + profile.getImageUrl());\n" +
                "        console.log(\"Email: \" + profile.getEmail());\n" +
                "\n" +
                "        // The ID token you need to pass to your backend:\n" +
                "        var id_token = googleUser.getAuthResponse().id_token;\n" +
                "        console.log(\"ID Token: \" + id_token);\n" +
                "        window.sessionStorage.setItem('token', id_token);\n" +
                "      }\n" +
                "\n" +
                "      function test() {\n" +
                "        var id_token = window.sessionStorage.getItem('token')\n" +
                "        fetch('/test', {\n" +
                "          headers: {\n" +
                "            'Authorization': 'Bearer ' + id_token,\n" +
                "            'Content-Type': 'application/json; charset=utf-8',\n" +
                "            'Accept': 'application/json',\n" +
                "          }\n" +
                "        }).then((response) => {\n" +
                "          console.log(response);\n" +
                "        })\n" +
                "      }\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>\n"
    }
}

package charchat.plugins

import charchat.routes.login
import charchat.routes.loginForm
import charchat.routes.main
import charchat.routes.respondPage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import kotlinx.html.p

fun Application.configureRouting() {
    install(Resources) {
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondPage {
                content {
                    p {
                        +"There is nothing here"
                    }
                }
            }
        }
    }

    install(Webjars) {
        path = "assets"
    }

    routing {
        main()
        login()
        loginForm()

        static("assets") {
            resources("js")
            resources("css")
            resources("img")
        }
    }
}

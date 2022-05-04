package charchat.plugins

import charchat.html.Layout
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import kotlinx.html.h1
import kotlinx.html.p

fun Application.configureRouting() {
    install(Resources) {
    }

    val layout = Layout()

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondHtmlTemplate(layout) {
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

        get("/") {
            call.respondHtmlTemplate(layout) {
                content {
                    h1 {
                        +"CharChat"
                    }
                }
            }
        }

        static("assets") {
            resources("js")
            resources("css")
            resources("img")
        }
    }
}

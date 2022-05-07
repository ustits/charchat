package charchat.plugins

import charchat.AppDeps
import charchat.html.pages.NotFoundPage
import charchat.routes.logout
import charchat.routes.signIn
import charchat.routes.signInForm
import charchat.routes.main
import charchat.routes.respondPage
import charchat.routes.signUp
import charchat.routes.signUpForm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*

fun Application.configureRouting(appDeps: AppDeps) {
    install(Resources) {
    }

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondPage(NotFoundPage())
        }
    }

    install(Webjars) {
        path = "assets"
    }

    routing {
        main()
        signIn()
        signUp(appDeps.userRepository())
        signInForm()
        signUpForm()
        logout()

        static("assets") {
            resources("js")
            resources("css")
            resources("img")
        }
    }
}

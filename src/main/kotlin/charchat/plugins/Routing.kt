package charchat.plugins

import charchat.AppDeps
import charchat.html.pages.NotFoundPage
import charchat.routes.campaignPage
import charchat.routes.characterPage
import charchat.routes.createCampaign
import charchat.routes.createCharacter
import charchat.routes.inviteForm
import charchat.routes.joinCharacter
import charchat.routes.wsChat
import charchat.routes.logout
import charchat.routes.signIn
import charchat.routes.signInForm
import charchat.routes.main
import charchat.routes.respondPage
import charchat.routes.signUp
import charchat.routes.signUpForm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration

private lateinit var baseURL: String

fun Application.baseURL(): String {
    return baseURL
}

fun Application.configureRouting(appDeps: AppDeps) {
    baseURL = appDeps.config.server.baseURL

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

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false

        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    routing {
        main(appDeps.playerRepository(), appDeps.dungeonMasterRepository())
        signIn()
        signUp(appDeps.userPrincipalRepository())
        signInForm()
        signUpForm()
        logout()
        wsChat()
        createCampaign(appDeps.dungeonMasterRepository())
        campaignPage(appDeps.campaignRepository())
        createCharacter(appDeps.playerRepository())
        characterPage(appDeps.playerRepository())
        inviteForm(appDeps.playerRepository(), appDeps.inviteRepository())
        joinCharacter(appDeps.inviteRepository(), appDeps.characterRepository())

        static("assets") {
            resources("js")
            resources("css")
            resources("img")
        }
    }
}

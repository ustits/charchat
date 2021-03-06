package charchat.routes

import charchat.auth.PlaintextPassword
import charchat.auth.UserPrincipal
import charchat.auth.UserPrincipalRepository
import charchat.domain.DungeonMasterRepository
import charchat.domain.PlayerRepository
import charchat.html.Layout
import charchat.html.pages.MainPageForUser
import charchat.html.pages.MainPageForGuest
import charchat.html.pages.Page
import charchat.html.pages.SignInPage
import charchat.html.pages.SignUpPage
import charchat.plugins.AppSession
import charchat.plugins.FORM_LOGIN_CONFIGURATION_NAME
import charchat.plugins.FORM_LOGIN_PASSWORD_FIELD
import charchat.plugins.FORM_LOGIN_EMAIL_FIELD
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/signIn")
class SignIn(val failed: Boolean? = null)

@Serializable
@Resource("/signUp")
class SignUp(val userExists: Boolean? = null)

@Serializable
@Resource("/logout")
object Logout

fun Route.main(playerRepository: PlayerRepository, dungeonMasterRepository: DungeonMasterRepository) {
    val createCampaignURL = application.href(CampaignResource)
    val createCharacterURL = application.href(CharacterResource())
    get("/") {
        val session = call.sessions.get<AppSession>()
        if (session == null) {
            call.respondPage(MainPageForGuest())
        } else {
            val characters = playerRepository.findByUser(session.user)!!.characters()
            val campaigns = dungeonMasterRepository.findByUser(session.user)!!.campaigns()
            call.respondPage(
                MainPageForUser(
                    createCampaignURL = createCampaignURL,
                    createCharacterURL = createCharacterURL,
                    characters = characters,
                    campaigns = campaigns
                )
            )
        }
    }
}

fun Route.signInForm() {
    get<SignIn> {
        call.respondPage(SignInPage(it))
    }
}

fun Route.signUpForm() {
    get<SignUp> {
        call.respondPage(SignUpPage(it))
    }
}

fun Route.signIn() {
    authenticate(FORM_LOGIN_CONFIGURATION_NAME) {
        post<SignIn> {
            val principal = call.principal<UserPrincipal>()!!
            call.setSessionAndRedirect(principal.toAppSession())
        }
    }
}

fun Route.signUp(userPrincipalRepository: UserPrincipalRepository) {
    post<SignUp> {
        val params = call.receiveParameters()
        val email = params[FORM_LOGIN_EMAIL_FIELD]
        val password = params[FORM_LOGIN_PASSWORD_FIELD]
        if (email != null && password != null) {
            val existingUser = userPrincipalRepository.findByEmailOrNull(email)
            if (existingUser == null) {
                val user = userPrincipalRepository.addOrUpdate(email, PlaintextPassword(password))
                call.setSessionAndRedirect(user.toAppSession())
            } else if (existingUser.hasSamePassword(password)) {
                call.setSessionAndRedirect(existingUser.toAppSession())
            } else {
                val redirect = application.href(SignUp(userExists = true))
                call.respondRedirect(redirect)
            }
        } else {
            throw BadRequestException("Email and password are required fields")
        }
    }
}

fun Route.logout() {
    get<Logout> {
        call.sessions.clear<AppSession>()
        call.respondRedirect("/")
    }
}

private suspend fun ApplicationCall.setSessionAndRedirect(session: AppSession) {
    sessions.set(session)
    respondRedirect("/")
}

suspend fun ApplicationCall.respondPage(page: Page) {
    val session = sessions.get<AppSession>()
    val signInURL = application.href(SignIn())
    val signUpURL = application.href(SignUp())
    val logoutURL = application.href(Logout)
    respondHtmlTemplate(
        Layout(
            signInURL = signInURL,
            signUpURL = signUpURL,
            logoutURL = logoutURL,
            appSession = session,
            application = application
        )
    ) {
        insert(page) {}
    }
}

package charchat.routes

import charchat.html.Layout
import charchat.plugins.AppPrincipal
import charchat.plugins.FORM_LOGIN_CONFIGURATION_NAME
import charchat.plugins.FORM_LOGIN_PASSWORD_FIELD
import charchat.plugins.FORM_LOGIN_USERNAME_FIELD
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.FormMethod
import kotlinx.html.emailInput
import kotlinx.html.form
import kotlinx.html.label
import kotlinx.html.passwordInput
import kotlinx.html.submitInput
import kotlinx.serialization.Serializable

@Serializable
@Resource("/login")
object Login

fun Route.loginForm(layout: Layout) {
    val loginURL = application.href(Login)
    get<Login> {
        call.respondHtmlTemplate(layout) {
            content {
                form(action = loginURL, method = FormMethod.post) {
                    label {
                        +"Email"
                        emailInput(name = FORM_LOGIN_USERNAME_FIELD) {
                            placeholder = "gendalf@tatooine.rick"
                        }
                    }

                    label {
                        +"Password"
                        passwordInput(name = FORM_LOGIN_PASSWORD_FIELD)
                    }

                    submitInput {
                        value = "Login"
                    }
                }
            }
        }
    }
}

fun Route.login() {
    authenticate(FORM_LOGIN_CONFIGURATION_NAME) {
        post<Login> {
            val principal = call.principal<AppPrincipal>()!!
            call.sessions.set(principal.toAppSession())
            call.respondRedirect("/")
        }
    }
}

package charchat.routes

import charchat.auth.PlaintextPassword
import charchat.auth.SqliteUsers
import charchat.auth.User
import charchat.html.Layout
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
import kotlinx.html.FormMethod
import kotlinx.html.emailInput
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.label
import kotlinx.html.passwordInput
import kotlinx.html.submitInput
import kotlinx.serialization.Serializable

@Serializable
@Resource("/signIn")
object SignIn

@Serializable
@Resource("/signUp")
object SignUp

fun Route.main() {
    get("/") {
        call.respondPage {
            content {
                h1 {
                    +"CharChat"
                }
            }
        }
    }
}

fun Route.signInForm() {
    get<SignIn> {
        call.respondPage {
            content {
                form(action = signInURL, method = FormMethod.post) {
                    label {
                        +"Email"
                        emailInput(name = FORM_LOGIN_EMAIL_FIELD) {
                            placeholder = "gendalf@tatooine.rick"
                            required = true
                        }
                    }

                    label {
                        +"Password"
                        passwordInput(name = FORM_LOGIN_PASSWORD_FIELD) {
                            required = true
                        }
                    }

                    submitInput {
                        value = "Sign In"
                    }
                }
            }
        }
    }
}

fun Route.signUpForm() {
    get<SignUp> {
        call.respondPage {
            content {
                form(action = signUpURL, method = FormMethod.post) {
                    label {
                        +"Email"
                        emailInput(name = FORM_LOGIN_EMAIL_FIELD) {
                            placeholder = "gendalf@tatooine.rick"
                            required = true
                        }
                    }

                    label {
                        +"Password"
                        passwordInput(name = FORM_LOGIN_PASSWORD_FIELD) {
                            required = true
                        }
                    }

                    submitInput {
                        value = "Sign Up"
                    }
                }
            }
        }
    }
}

fun Route.signIn() {
    authenticate(FORM_LOGIN_CONFIGURATION_NAME) {
        post<SignIn> {
            val principal = call.principal<User>()!!
            call.setSessionAndRedirect(principal.toAppSession())
        }
    }
}

fun Route.signUp() {
    post<SignUp> {
        val users = SqliteUsers()
        val params = call.receiveParameters()
        val email = params[FORM_LOGIN_EMAIL_FIELD]
        val password = params[FORM_LOGIN_PASSWORD_FIELD]
        if (email != null && password != null) {
            val existingUser = users.findByEmailOrNull(email)
            if (existingUser == null) {
                val user = users.addOrUpdate(email, PlaintextPassword(password))
                val session = AppSession(user.id, user.name)
                call.setSessionAndRedirect(session)
            } else if (existingUser.hasSamePassword(password)) {
                val session = AppSession(existingUser.id, existingUser.name)
                call.setSessionAndRedirect(session)
            } else {
                val signInURL = application.href(SignIn)
                call.respondRedirect(signInURL)
            }
        } else {
            throw BadRequestException("Email and password are required fields")
        }
    }
}

private suspend fun ApplicationCall.setSessionAndRedirect(session: AppSession) {
    sessions.set(session)
    respondRedirect("/")
}

suspend fun ApplicationCall.respondPage(block: Layout.() -> Unit) {
    val session = sessions.get<AppSession>()
    val signInURL = application.href(SignIn)
    val signUpURL = application.href(SignUp)
    respondHtmlTemplate(
        Layout(
            signInURL = signInURL,
            signUpURL = signUpURL,
            appSession = session
        ),
        body = block
    )
}

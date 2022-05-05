package charchat.plugins

import charchat.auth.SqliteUsers
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import kotlin.time.Duration

const val FORM_LOGIN_CONFIGURATION_NAME = "form-auth"
const val FORM_LOGIN_EMAIL_FIELD = "email"
const val FORM_LOGIN_PASSWORD_FIELD = "password"

fun Application.configureAuth() {
    val sqliteUsers = SqliteUsers()

    install(Authentication) {
        form(FORM_LOGIN_CONFIGURATION_NAME) {
            userParamName = FORM_LOGIN_EMAIL_FIELD
            passwordParamName = FORM_LOGIN_PASSWORD_FIELD
            validate { credentials ->
                val user = sqliteUsers.findByEmailOrNull(credentials.name)
                if (user != null && user.hasSamePassword(credentials.password)) {
                    user
                } else {
                    null
                }
            }
        }
    }

    install(Sessions) {
        cookie<AppSession>("charchat_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAge = Duration.parse("7d")
        }
    }
}

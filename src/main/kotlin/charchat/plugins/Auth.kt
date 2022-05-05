package charchat.plugins

import charchat.auth.SqliteUsers
import io.ktor.server.application.*
import io.ktor.server.auth.*

const val FORM_LOGIN_CONFIGURATION_NAME = "form-auth"
const val FORM_LOGIN_USERNAME_FIELD = "username"
const val FORM_LOGIN_PASSWORD_FIELD = "password"

fun Application.configureAuth() {
    val sqliteUsers = SqliteUsers()

    install(Authentication) {
        form(FORM_LOGIN_CONFIGURATION_NAME) {
            userParamName = FORM_LOGIN_USERNAME_FIELD
            passwordParamName = FORM_LOGIN_PASSWORD_FIELD
            validate { credentials ->
                val user = sqliteUsers.findByLogin(credentials.name)
                val samePassword = user.password.matches(credentials.password)
                if (samePassword) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}

package charchat.plugins

import io.ktor.server.auth.*

data class AppPrincipal(private val userID: Int, private val name: String?) : Principal {

    fun toAppSession(): AppSession {
        return AppSession(userID, name)
    }

}

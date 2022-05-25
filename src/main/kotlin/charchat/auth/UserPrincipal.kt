package charchat.auth

import charchat.domain.ID
import charchat.domain.User
import charchat.plugins.AppSession
import io.ktor.server.auth.*
import org.mindrot.jbcrypt.BCrypt

data class UserPrincipal(val id: Int, val name: String?, val email: String, private val password: Password) : Principal {

    fun hasSamePassword(plain: String): Boolean {
        return BCrypt.checkpw(plain, password.print())
    }

    fun toAppSession(): AppSession {
        return AppSession(User(ID(id), name ?: ""))
    }

}

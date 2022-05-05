package charchat.auth

import charchat.plugins.AppSession
import io.ktor.server.auth.*
import org.mindrot.jbcrypt.BCrypt

data class User(val id: Int, val name: String?, val email: String, private val password: Password) : Principal {

    fun hasSamePassword(plain: String): Boolean {
        return BCrypt.checkpw(plain, password.print())
    }

    fun toAppSession(): AppSession {
        return AppSession(id, name)
    }

}

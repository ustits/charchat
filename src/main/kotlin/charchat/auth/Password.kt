package charchat.auth

import org.mindrot.jbcrypt.BCrypt

sealed interface Password {

    fun print(): String

}

@JvmInline
value class PlaintextPassword(private val text: String): Password {

    override fun print(): String {
        return BCrypt.hashpw(text, BCrypt.gensalt())
    }
}

@JvmInline
value class HashedPassword(private val hash: String): Password {

    override fun print(): String {
        return hash
    }
}

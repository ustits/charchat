package charchat.auth

import org.mindrot.jbcrypt.BCrypt

sealed interface Password {

    fun print(): String

    fun matches(plain: String): Boolean

}

@JvmInline
value class PlaintextPassword(private val text: String): Password {

    override fun print(): String {
        return BCrypt.hashpw(text, BCrypt.gensalt())
    }

    override fun matches(plain: String): Boolean {
        return text == plain
    }
}

@JvmInline
value class HashedPassword(private val hash: String): Password {

    override fun print(): String {
        return hash
    }

    override fun matches(plain: String): Boolean {
        return BCrypt.checkpw(plain, hash)
    }
}

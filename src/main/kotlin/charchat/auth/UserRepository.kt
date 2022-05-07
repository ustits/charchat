package charchat.auth

interface UserRepository {

    fun findByEmailOrNull(email: String): User?

    fun addOrUpdate(email: String, password: Password): User

}

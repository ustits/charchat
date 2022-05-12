package charchat.auth

interface UserPrincipalRepository {

    fun findByEmailOrNull(email: String): UserPrincipal?

    fun addOrUpdate(email: String, password: Password): UserPrincipal

}

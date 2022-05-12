package charchat.domain

interface UserRepository {

    fun findByIDOrNull(id: ID): User?

}

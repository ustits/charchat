package charchat.domain

interface UserRepository {

    fun findByID(id: ID): User?

}

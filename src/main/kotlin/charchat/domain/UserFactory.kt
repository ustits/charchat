package charchat.domain

interface UserFactory {

    fun create(name: String): User

}

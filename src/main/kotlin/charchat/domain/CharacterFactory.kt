package charchat.domain

interface CharacterFactory {

    fun create(user: User, name: String): Character

}

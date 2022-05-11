package charchat.domain

interface CharacterFactory {

    fun create(owner: User, name: String): Character

}

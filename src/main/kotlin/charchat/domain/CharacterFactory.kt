package charchat.domain

interface CharacterFactory {

    fun create(user: User, characterSpec: CharacterSpec): Character

}

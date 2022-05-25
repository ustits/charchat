package charchat.domain

class User(
    val id: ID,
    val name: String,
    private val characterFactory: CharacterFactory,
    private val characterRepository: CharacterRepository
) {

    fun createCharacter(characterSpec: CharacterSpec): Character {
        return characterFactory.create(this, characterSpec)
    }

    fun characters(): List<Character> {
        return characterRepository.findAllByUser(this)
    }



}

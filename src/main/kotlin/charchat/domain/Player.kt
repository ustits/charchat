package charchat.domain

class Player(
    val user: User,
    private val characterFactory: CharacterFactory,
    private val characterRepository: CharacterRepository
) {

    fun createCharacter(characterSpec: CharacterSpec): Character {
        return characterFactory.create(user, characterSpec)
    }

    fun characters(): List<Character> {
        return characterRepository.findAllByPlayer(this)
    }

}

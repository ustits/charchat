package charchat.domain

class Scene(val id: ID, private val characterRepository: CharacterRepository) {

    fun characters(): List<Character> {
        return characterRepository.findBySceneID(id)
    }

    fun addEvent(event: Event) {
        TODO()
    }

    fun addCharacter(character: Character) {
        TODO()
    }

}
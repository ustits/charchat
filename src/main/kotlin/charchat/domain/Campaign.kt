package charchat.domain

class Campaign(
    val id: ID,
    val dungeonMaster: User,
    val name: String,
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository
) {

    fun characters(): List<Character> {
        return characterRepository.findByCampaign(this)
    }

    fun addCharacter(character: Character) {
        TODO()
    }

    fun deleteCharacter(character: Character) {
        TODO()
    }

    fun startScene(): Scene {
        return sceneFactory.create(this, "tmp")
    }

}

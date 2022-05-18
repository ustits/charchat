package charchat.domain

class Campaign(
    val id: ID,
    val name: String,
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository,
    private val inviteFactory: InviteFactory
) {

    fun characters(): List<Character> {
        return characterRepository.findAllByCampaign(this)
    }

    fun invite(): Invite {
        return inviteFactory.create(this)
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

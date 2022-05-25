package charchat.domain

class Campaign(
    val id: ID,
    val name: String,
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository,
    private val inviteFactory: InviteFactory,
    private val partyMemberRepository: PartyMemberRepository
) {

    fun partyMembers(): List<PartyMember> {
        return characterRepository.findAllByCampaign(this)
            .map { PartyMember(this, it) }
    }

    fun invite(): Invite {
        return inviteFactory.create(this)
    }

    fun addCharacter(character: Character) {
        partyMemberRepository.add(
            PartyMember(this, character)
        )
    }

    fun deleteCharacter(character: Character) {
        partyMemberRepository.delete(
            PartyMember(this, character)
        )
    }

    fun startScene(): Scene {
        return sceneFactory.create(this, "tmp")
    }

}

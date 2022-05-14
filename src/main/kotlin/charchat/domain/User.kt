package charchat.domain

class User(
    val id: ID,
    val name: String,
    private val campaignFactory: CampaignFactory,
    private val characterFactory: CharacterFactory
) {

    fun createCampaign(): Campaign {
        return campaignFactory.create(this, "tmp")
    }

    fun createCharacter(): Character {
        return characterFactory.create(this, "tmp")
    }

    fun joinParty(invite: Invite) {
        val char = createCharacter()
        invite.addToCampaign(char)
    }

}

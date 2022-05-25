package charchat.domain

class User(
    val id: ID,
    val name: String,
    private val campaignFactory: CampaignFactory,
    private val campaignRepository: CampaignRepository,
    private val characterFactory: CharacterFactory,
    private val characterRepository: CharacterRepository
) {

    fun createCampaign(): Campaign {
        return campaignFactory.create(
            dm = DungeonMaster(this),
            name = "tmp"
        )
    }

    fun createCharacter(characterSpec: CharacterSpec): Character {
        return characterFactory.create(this, characterSpec)
    }

    fun characters(): List<Character> {
        return characterRepository.findAllByUser(this)
    }

    fun campaigns(): List<Campaign> {
        return campaignRepository.findAllByDungeonMaster(
            DungeonMaster(this)
        )
    }

}

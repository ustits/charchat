package charchat.domain

class DungeonMaster(
    val user: User,
    private val campaignFactory: CampaignFactory,
    private val campaignRepository: CampaignRepository,
) {

    fun createCampaign(): Campaign {
        return campaignFactory.create(
            dm = this,
            name = "tmp"
        )
    }

    fun campaigns(): List<Campaign> {
        return campaignRepository.findAllByDungeonMaster(this)
    }

}

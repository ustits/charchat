package charchat.domain

interface DungeonMasterRepository {

    fun findByUser(user: User): DungeonMaster?

    class ByUser(
        private val campaignFactory: CampaignFactory,
        private val campaignRepository: CampaignRepository
    ) : DungeonMasterRepository {

        override fun findByUser(user: User): DungeonMaster {
            return DungeonMaster(
                user = user,
                campaignFactory = campaignFactory,
                campaignRepository = campaignRepository
            )
        }
    }
}

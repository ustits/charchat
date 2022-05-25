package charchat.domain

interface DungeonMasterRepository {

    fun findByID(id: ID): DungeonMaster?

    class ByUser(
        private val userRepository: UserRepository,
        private val campaignFactory: CampaignFactory,
        private val campaignRepository: CampaignRepository
    ) : DungeonMasterRepository {

        override fun findByID(id: ID): DungeonMaster? {
            return userRepository.findByID(id)
                ?.let {
                    DungeonMaster(
                        user = it,
                        campaignFactory = campaignFactory,
                        campaignRepository = campaignRepository
                    )
                }
        }
    }
}

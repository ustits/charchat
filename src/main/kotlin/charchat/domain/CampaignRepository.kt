package charchat.domain

interface CampaignRepository {

    fun findByID(id: ID): Campaign?

    fun findAllByDungeonMaster(dm: DungeonMaster): List<Campaign>

}

package charchat.domain

interface CampaignFactory {

    fun create(dm: DungeonMaster, name: String): Campaign

}

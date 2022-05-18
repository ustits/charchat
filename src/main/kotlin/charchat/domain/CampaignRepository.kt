package charchat.domain

interface CampaignRepository {

    fun findByID(id: ID): Campaign?

    fun findByUser(user: User): List<Campaign>

}

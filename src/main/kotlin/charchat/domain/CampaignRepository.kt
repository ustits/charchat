package charchat.domain

interface CampaignRepository {

    fun findByID(id: ID): Campaign?

    fun findAllByUser(user: User): List<Campaign>

}

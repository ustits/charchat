package charchat.domain

interface CampaignRepository {

    fun findByUser(user: User): List<Campaign>

}

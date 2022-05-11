package charchat.domain

interface CampaignFactory {

    fun create(dm: User, name: String): Campaign

}

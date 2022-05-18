package charchat.adapters

import charchat.domain.CampaignRepository
import charchat.domain.ID
import charchat.domain.Invite
import charchat.domain.InviteRepository
import org.hashids.Hashids

class HashidsInviteRepository(
    private val hashids: Hashids,
    private val campaignRepository: CampaignRepository
) : InviteRepository {

    override fun findByText(str: String): Invite? {
        val campaign = hashids.decodeOneOrNull(str)
            ?.let { ID(it.toInt()) }
            ?.let { campaignRepository.findByID(it) }
        return if (campaign == null) {
            null
        } else {
            Invite(campaign = campaign, id = str)
        }
    }
}

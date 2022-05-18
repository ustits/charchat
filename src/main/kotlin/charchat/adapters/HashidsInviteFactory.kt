package charchat.adapters

import charchat.domain.Campaign
import charchat.domain.Invite
import charchat.domain.InviteFactory
import org.hashids.Hashids

class HashidsInviteFactory(private val hashids: Hashids) : InviteFactory {

    override fun create(campaign: Campaign): Invite {
        val campaignID = campaign.id.value
        val hash = hashids.encode(campaignID.toLong())
        return Invite(campaign = campaign, id = hash)
    }
}

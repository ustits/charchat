package charchat.domain

interface InviteFactory {

    fun create(campaign: Campaign): Invite

}

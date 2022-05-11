package charchat.domain

import java.net.URL

class Invite(val url: URL, private val campaign: Campaign) {

    fun addToCampaign(character: Character) {
        campaign.addCharacter(character)
    }

}
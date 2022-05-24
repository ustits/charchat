package charchat.adapters

import charchat.db.sql
import charchat.domain.PartyMember
import charchat.domain.PartyMemberRepository

class DBPartyMemberRepository : PartyMemberRepository {

    override fun add(partyMember: PartyMember) {
        sql("""
            INSERT OR IGNORE INTO campaign_characters (campaign, character)
            VALUES (?, ?)
        """.trimIndent()) {
            setInt(1, partyMember.campaign.id.value)
            setInt(2, partyMember.character.id.value)
            execute()
        }
    }

    override fun delete(partyMember: PartyMember) {
        sql("""
            DELETE FROM campaign_characters 
            WHERE campaign = ? AND character = ?
        """.trimIndent()) {
            setInt(1, partyMember.campaign.id.value)
            setInt(2, partyMember.character.id.value)
            execute()
        }
    }
}

package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CampaignFactory
import charchat.domain.ID
import charchat.domain.User

class DBCampaignFactory : CampaignFactory {

    override fun create(dm: User, name: String): Campaign {
        return transaction {
            val statement = prepareStatement("""
                INSERT INTO campaigns (name, dm, created_at) 
                VALUES (?, ?, date('now'))
                RETURNING id, name
            """.trimIndent())
            statement.setString(1, name)
            statement.setInt(2, dm.id.value)
            val campaign = statement.executeQuery().toSequence {
                Campaign(
                    id = ID(getInt(1)),
                    dungeonMaster = dm,
                    name = getString(2)
                )
            }.first()
            statement.close()
            campaign
        }
    }
}

package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CampaignFactory
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.InviteFactory
import charchat.domain.SceneFactory
import charchat.domain.User

class DBCampaignFactory(
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository,
    private val inviteFactory: InviteFactory
) : CampaignFactory {

    override fun create(dm: User, name: String): Campaign {
        return transaction {
            val statement = prepareStatement(
                """
                INSERT INTO campaigns (name, dm, created_at) 
                VALUES (?, ?, date('now'))
                RETURNING id, name
            """.trimIndent()
            )
            statement.setString(1, name)
            statement.setInt(2, dm.id.value)
            val campaign = statement.executeQuery().toSequence {
                Campaign(
                    id = ID(getInt("id")),
                    name = getString("name"),
                    sceneFactory = sceneFactory,
                    characterRepository = characterRepository,
                    inviteFactory = inviteFactory
                )
            }.first()
            statement.close()
            campaign
        }
    }
}

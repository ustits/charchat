package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.InviteFactory
import charchat.domain.SceneFactory
import charchat.domain.User
import java.sql.ResultSet

class DBCampaigns(
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository,
    private val inviteFactory: InviteFactory
) : CampaignFactory, CampaignRepository {

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
                toCampaign()
            }.first()
            statement.close()
            campaign
        }
    }

    override fun findByID(id: ID): Campaign? {
        return transaction {
            val statement = prepareStatement(
                """
                SELECT id, name FROM campaigns
                WHERE dm = ?
            """.trimIndent()
            )
            statement.setInt(1, id.value)
            val campaign = statement.executeQuery().toSequence {
                toCampaign()
            }.firstOrNull()
            statement.close()
            campaign
        }
    }

    override fun findByUser(user: User): List<Campaign> {
        return transaction {
            val statement = prepareStatement(
                """
                SELECT id, name FROM campaigns
                WHERE dm = ?
            """.trimIndent()
            )
            statement.setInt(1, user.id.value)
            val campaigns = statement.executeQuery().toSequence {
                toCampaign()
            }.toList()
            statement.close()
            campaigns
        }
    }

    private fun ResultSet.toCampaign(): Campaign {
        return Campaign(
            id = ID(getInt("id")),
            name = getString("name"),
            sceneFactory = sceneFactory,
            characterRepository = characterRepository,
            inviteFactory = inviteFactory
        )
    }
}

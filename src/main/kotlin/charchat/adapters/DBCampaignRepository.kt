package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CampaignRepository
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.SceneFactory
import charchat.domain.User

class DBCampaignRepository(
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository
) : CampaignRepository {

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
                Campaign(
                    id = ID(getInt("id")),
                    dungeonMaster = user,
                    name = getString("name"),
                    sceneFactory = sceneFactory,
                    characterRepository = characterRepository
                )
            }.toList()
            statement.close()
            campaigns
        }
    }
}

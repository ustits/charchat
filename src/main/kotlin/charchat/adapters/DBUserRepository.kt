package charchat.adapters

import charchat.db.firstOrNull
import charchat.db.sql
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.User
import charchat.domain.UserRepository

class DBUserRepository(
    private val campaignFactory: CampaignFactory,
    private val campaignRepository: CampaignRepository,
    private val characterFactory: CharacterFactory,
    private val characterRepository: CharacterRepository
) : UserRepository {

    override fun findByID(id: ID): User? {
        return sql(
            """
                SELECT name FROM users WHERE id = ?
            """.trimIndent()
        ) {
            setInt(1, id.value)
            executeQuery().firstOrNull {
                User(
                    id = id,
                    name = getString("name") ?: "",
                    campaignFactory = campaignFactory,
                    campaignRepository = campaignRepository,
                    characterFactory = characterFactory,
                    characterRepository = characterRepository
                )
            }
        }
    }
}

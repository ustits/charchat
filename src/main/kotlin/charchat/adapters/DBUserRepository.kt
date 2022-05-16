package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.CampaignFactory
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.User
import charchat.domain.UserRepository

class DBUserRepository(
    private val campaignFactory: CampaignFactory,
    private val characterFactory: CharacterFactory,
    private val characterRepository: CharacterRepository
) : UserRepository {

    override fun findByIDOrNull(id: ID): User? {
        return transaction {
            val statement = prepareStatement("""
                SELECT name FROM users WHERE id = ?
            """.trimIndent())
            statement.setInt(1, id.value)
            val user = statement.executeQuery().toSequence {
                User(
                    id = id,
                    name = getString("name") ?: "",
                    campaignFactory = campaignFactory,
                    characterFactory = characterFactory,
                    characterRepository = characterRepository
                )
            }.firstOrNull()
            statement.close()
            user
        }
    }
}

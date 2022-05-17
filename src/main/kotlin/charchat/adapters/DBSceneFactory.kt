package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.Scene
import charchat.domain.SceneFactory

class DBSceneFactory(private val characterRepository: CharacterRepository) : SceneFactory {

    override fun create(campaign: Campaign, name: String): Scene {
        return transaction {
            val statement = prepareStatement("""
                INSERT INTO scenes(name, campaign, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id
            """.trimIndent())
            statement.setString(1, name)
            statement.setInt(2, campaign.id.value)
            val id = statement.executeQuery().toSequence {
                getInt("id")
            }.first()
            statement.close()
            Scene(
                id = ID(id),
                characterRepository = characterRepository
            )
        }
    }
}

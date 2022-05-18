package charchat.adapters

import charchat.db.first
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.Scene
import charchat.domain.SceneFactory

class DBSceneFactory(private val characterRepository: CharacterRepository) : SceneFactory {

    override fun create(campaign: Campaign, name: String): Scene {
        return transaction(
            """
                INSERT INTO scenes(name, campaign, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id
            """.trimIndent()
        ) {
            setString(1, name)
            setInt(2, campaign.id.value)
            executeQuery().first {
                Scene(
                    id = ID(getInt("id")),
                    characterRepository = characterRepository
                )
            }
        }
    }
}

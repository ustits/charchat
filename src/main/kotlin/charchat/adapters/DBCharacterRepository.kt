package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Campaign
import charchat.domain.Character
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.Scene
import charchat.domain.User
import java.sql.ResultSet

class DBCharacterRepository : CharacterRepository {

    override fun findByID(id: ID): Character? {
        return findAllByIDAndStatement(
            id,
            """
                SELECT id, name FROM characters
                WHERE id = ?
            """.trimIndent()
        ).firstOrNull()
    }

    override fun findByUser(user: User): List<Character> {
        return findAllByIDAndStatement(
            user.id,
            """
                SELECT id, name FROM characters
                WHERE player = ?
            """.trimIndent()
        )
    }

    override fun findByCampaign(campaign: Campaign): List<Character> {
        return findAllByIDAndStatement(
            campaign.id,
            """
                SELECT characters.id, characters.name FROM characters, campaign_characters
                WHERE campaign_characters.campaign = ? 
            """.trimIndent()
        )
    }

    override fun findByScene(scene: Scene): List<Character> {
        return findAllByIDAndStatement(
            scene.id,
            """
                SELECT characters.id, characters.name FROM characters, scene_characters
                WHERE scene_characters.scene = ? 
            """.trimIndent()
        )
    }

    private fun findAllByIDAndStatement(id: ID, sql: String): List<Character> {
        return transaction {
            val statement = prepareStatement(sql)
            statement.setInt(1, id.value)
            val chars = statement.executeQuery().toSequence {
                toCharacter()
            }.toList()
            statement.close()
            chars
        }
    }

    private fun ResultSet.toCharacter(): Character {
        return Character(
            id = ID(getInt("id")),
            name = getString("name")
        )
    }
}

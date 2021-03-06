package charchat.adapters

import charchat.db.first
import charchat.db.toSequence
import charchat.db.sql
import charchat.domain.Campaign
import charchat.domain.Character
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.CharacterSpec
import charchat.domain.ID
import charchat.domain.Player
import charchat.domain.Scene
import charchat.domain.User
import java.sql.ResultSet

class DBCharacters : CharacterFactory, CharacterRepository {

    override fun create(user: User, characterSpec: CharacterSpec): Character {
        return sql(
            """
                INSERT INTO characters(name, player, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id, name
            """.trimIndent()
        ) {
            setString(1, characterSpec.name)
            setInt(2, user.id.value)
            executeQuery().first {
                toCharacter()
            }
        }
    }

    override fun findByID(id: ID): Character? {
        return findAllByIDAndStatement(
            id,
            """
                SELECT id, name FROM characters
                WHERE id = ?
            """.trimIndent()
        ).firstOrNull()
    }

    override fun findAllByPlayer(player: Player): List<Character> {
        return findAllByIDAndStatement(
            player.user.id,
            """
                SELECT id, name FROM characters
                WHERE player = ?
            """.trimIndent()
        )
    }

    override fun findAllByCampaign(campaign: Campaign): List<Character> {
        return findAllByIDAndStatement(
            campaign.id,
            """
                SELECT characters.id, characters.name FROM characters, campaign_characters
                WHERE campaign_characters.campaign = ? 
                AND campaign_characters.character = characters.id
            """.trimIndent()
        )
    }

    override fun findAllByScene(scene: Scene): List<Character> {
        return findAllByIDAndStatement(
            scene.id,
            """
                SELECT characters.id, characters.name FROM characters, scene_characters
                WHERE scene_characters.scene = ? 
            """.trimIndent()
        )
    }

    private fun findAllByIDAndStatement(id: ID, sql: String): List<Character> {
        return sql(sql) {
            setInt(1, id.value)
            executeQuery().toSequence {
                toCharacter()
            }.toList()
        }
    }

    private fun ResultSet.toCharacter(): Character {
        return Character(
            id = ID(getInt("id")),
            name = getString("name")
        )
    }
}

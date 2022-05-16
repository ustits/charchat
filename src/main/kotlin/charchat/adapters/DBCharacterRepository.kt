package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Character
import charchat.domain.CharacterRepository
import charchat.domain.ID
import java.sql.ResultSet

class DBCharacterRepository : CharacterRepository {

    override fun findByID(id: ID): Character? {
        return transaction {
            val statement = prepareStatement(
                """
                SELECT id, name FROM characters
                WHERE id = ?
            """.trimIndent()
            )
            statement.setInt(1, id.value)
            val character = statement.executeQuery().toSequence {
                toCharacter()
            }.firstOrNull()
            statement.close()
            character
        }
    }

    override fun findByUserID(userID: ID): List<Character> {
        return transaction {
            val statement = prepareStatement("""
                SELECT id, name FROM characters
                WHERE player = ?
            """.trimIndent())
            statement.setInt(1, userID.value)
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

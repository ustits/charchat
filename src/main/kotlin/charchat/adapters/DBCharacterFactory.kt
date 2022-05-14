package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Character
import charchat.domain.CharacterFactory
import charchat.domain.ID
import charchat.domain.User

class DBCharacterFactory : CharacterFactory {

    override fun create(user: User, name: String): Character {
        return transaction {
            val statement = prepareStatement("""
                INSERT INTO characters(name, player, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id
            """.trimIndent())
            statement.setString(1, name)
            statement.setInt(2, user.id.value)
            val id = statement.executeQuery().toSequence {
                getInt("id")
            }.first()
            val char = Character(ID(id), name)
            statement.close()
            char
        }
    }
}

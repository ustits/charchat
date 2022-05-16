package charchat.adapters

import charchat.db.toSequence
import charchat.db.transaction
import charchat.domain.Character
import charchat.domain.CharacterFactory
import charchat.domain.CharacterSpec
import charchat.domain.ID
import charchat.domain.User

class DBCharacterFactory : CharacterFactory {

    override fun create(user: User, characterSpec: CharacterSpec): Character {
        return transaction {
            val statement = prepareStatement("""
                INSERT INTO characters(name, player, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id
            """.trimIndent())
            statement.setString(1, characterSpec.name)
            statement.setInt(2, user.id.value)
            val id = statement.executeQuery().toSequence {
                getInt("id")
            }.first()
            val char = Character(ID(id), characterSpec.name)
            statement.close()
            char
        }
    }
}

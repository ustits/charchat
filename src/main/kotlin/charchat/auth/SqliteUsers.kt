package charchat.auth

import charchat.db.toSequence
import charchat.db.transaction
import java.sql.Connection
import java.sql.ResultSet

class SqliteUsers {

    fun findByEmailOrNull(email: String): User? {
        return transaction {
            val statement = prepareStatement("SELECT id, email, name, password FROM users WHERE email = ?")
            statement.setString(1, email)
            val user = statement.executeQuery().toSequence {
                toUser(this)
            }.firstOrNull()
            statement.close()
            user
        }
    }

    fun addOrUpdate(email: String, password: Password): User {
        return transaction {
            if (existsByEmail(email)) {
                updateAndReturn(email, password.print())
            } else {
                add(email, password.print())
            }
        }
    }

    private fun Connection.add(email: String, password: String): User {
        val statement = prepareStatement("""
            INSERT INTO users (email, password) VALUES (?, ?) 
            RETURNING id, email, name, password
        """.trimIndent())
        statement.setString(1, email)
        statement.setString(2, password)
        val user = statement.executeQuery().toSequence {
            toUser(this)
        }.first()
        statement.close()
        return user
    }

    private fun Connection.existsByEmail(email: String): Boolean {
        val statement = prepareStatement("SELECT COUNT (*) FROM users WHERE email = ?")
        statement.setString(1, email)
        val count = statement.executeQuery().toSequence {
            getLong(1)
        }.first()
        statement.close()
        return count > 0
    }

    private fun Connection.updateAndReturn(email: String, password: String): User {
        val statement = prepareStatement("""
            UPDATE users SET password = ? 
            WHERE email = ?
            RETURNING id, email, name, password
        """.trimIndent())
        statement.setString(1, password)
        statement.setString(2, email)
        val user = statement.executeQuery().toSequence {
            toUser(this)
        }.first()
        statement.close()
        return user
    }

    private fun toUser(rs: ResultSet): User {
        return User(
            id = rs.getInt("id"),
            email = rs.getString("email"),
            name = rs.getString("name"),
            password = HashedPassword(rs.getString("password"))
        )
    }

}

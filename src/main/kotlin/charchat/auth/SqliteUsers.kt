package charchat.auth

import charchat.db.toSequence
import charchat.db.transaction
import java.sql.Connection

class SqliteUsers {

    fun findByLogin(login: String): User {
        return transaction {
            val statement = prepareStatement("SELECT id, email, name, password FROM users WHERE email = ?")
            statement.setString(1, login)
            val user = statement.executeQuery().toSequence {
                User(
                    id = getInt(1),
                    email = getString(2),
                    name = getString(3),
                    password = HashedPassword(getString(4))
                )
            }.first()
            statement.close()
            user
        }
    }

    fun addOrUpdate(login: String, password: Password) {
        transaction {
            if (existsByLogin(login)) {
                update(login, password.print())
            } else {
                add(login, password.print())
            }
        }
    }

    private fun Connection.add(login: String, password: String) {
        val statement = prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)")
        statement.setString(1, login)
        statement.setString(2, password)
        statement.execute()
    }

    private fun Connection.existsByLogin(login: String): Boolean {
        val statement = prepareStatement("SELECT COUNT (*) FROM users WHERE email = ?")
        statement.setString(1, login)
        val count = statement.executeQuery().toSequence {
            getLong(1)
        }.first()
        statement.close()
        return count > 0
    }

    private fun Connection.update(login: String, password: String) {
        val statement = prepareStatement("UPDATE users SET password = ? WHERE email = ?")
        statement.setString(1, password)
        statement.setString(2, login)
        statement.execute()
    }

}

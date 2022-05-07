package charchat

import charchat.auth.SqliteUserRepository
import charchat.auth.UserRepository

class AppDeps {

    fun userRepository(): UserRepository {
        return SqliteUserRepository()
    }

}

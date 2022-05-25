package charchat.plugins

import charchat.domain.User
import io.ktor.server.auth.*

data class AppSession(val user: User) : Principal

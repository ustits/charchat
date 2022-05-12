package charchat.plugins

import io.ktor.server.auth.*

data class AppSession(val userID: Int, val name: String?) : Principal

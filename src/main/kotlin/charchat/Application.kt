package charchat

import charchat.auth.PlaintextPassword
import charchat.auth.UserPrincipalRepository
import charchat.config.readConfiguration
import charchat.db.configureDatabase
import charchat.plugins.configureAuth
import charchat.plugins.configureCallLogging
import charchat.plugins.configureMetrics
import charchat.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val cfg = readConfiguration()
    val appDeps = AppDeps(cfg.app)

    embeddedServer(Netty, port = cfg.server.port, host = "0.0.0.0") {
        configureDatabase(cfg.database)
        configureRouting(appDeps)
        configureAuth(appDeps)
        configureCallLogging()
        configureMetrics()

        populateTestUsers(appDeps.userPrincipalRepository())
    }.start(wait = true)
}

private fun populateTestUsers(userPrincipalRepository: UserPrincipalRepository) {
    mapOf(
        "ivan@ivan.dev" to "ivan",
        "peter@peter.dev" to "peter",
        "nick@nick.dev" to "nick",
        "andy@andy.dev" to "andy",
    ).forEach { (u, p) ->
        val password = PlaintextPassword(p)
        userPrincipalRepository.addOrUpdate(u, password)
    }
}

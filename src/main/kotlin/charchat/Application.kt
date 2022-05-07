package charchat

import charchat.auth.PlaintextPassword
import charchat.auth.UserRepository
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
    val appDeps = AppDeps()

    embeddedServer(Netty, port = cfg.server.port, host = "0.0.0.0") {
        configureDatabase(cfg.database)
        configureRouting(appDeps)
        configureAuth(appDeps)
        configureCallLogging()
        configureMetrics()

        populateTestUsers(appDeps.userRepository())
    }.start(wait = true)
}

private fun populateTestUsers(userRepository: UserRepository) {
    mapOf(
        "ivan@ivan.dev" to "ivan",
        "peter@peter.dev" to "peter",
        "nick@nick.dev" to "nick",
        "andy@andy.dev" to "andy",
    ).forEach { (u, p) ->
        val password = PlaintextPassword(p)
        userRepository.addOrUpdate(u, password)
    }
}

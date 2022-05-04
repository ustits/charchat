package charchat

import charchat.config.readConfiguration
import charchat.plugins.configureCallLogging
import charchat.plugins.configureMetrics
import charchat.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val cfg = readConfiguration()

    embeddedServer(Netty, port = cfg.server.port, host = "0.0.0.0") {
//        configureDatabase(cfg.database)

        configureRouting()
        configureCallLogging()
        configureMetrics()
    }.start(wait = true)
}

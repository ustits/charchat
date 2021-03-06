package charchat.config

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.PropertySource
import java.io.File

data class Configuration(
    val server: Server = Server(),
    val database: Database = Database(),
    val app: AppConfig = AppConfig(
        salt = "charchat",
        length = 24
    )
)

fun readConfiguration(): Configuration {
    return configLoader().loadConfigOrThrow()
}

private fun configLoader(): ConfigLoader {
    return ConfigLoaderBuilder.default()
        .also {
            val path = System.getenv("CONFIG_PATH")
            if (path != null) {
                it.addPropertySource(PropertySource.file(file = File(path), optional = true))
            }
        }
        .addPropertySource(PropertySource.resource("/application.toml"))
        .build()
}

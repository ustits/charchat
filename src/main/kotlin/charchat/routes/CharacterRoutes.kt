package charchat.routes

import charchat.domain.CharacterSpec
import charchat.domain.ID
import charchat.domain.PlayerRepository
import charchat.html.pages.CharacterPage
import charchat.plugins.AppSession
import charchat.plugins.SESSION_LOGIN_CONFIGURATION_NAME
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/character")
class CharacterResource {

    @Serializable
    @Resource("/{id}")
    class ByID(val root: CharacterResource = CharacterResource(), val id: Int)

}

fun Route.createCharacter(playerRepository: PlayerRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        post<CharacterResource> {
            val session = call.principal<AppSession>()!!
            val player = playerRepository.findByUser(session.user)!!
            val params = call.receiveParameters()
            val charSpec = parseCharacterSpec(params)
            val character = player.createCharacter(charSpec)
            val redirect = application.href(CharacterResource.ByID(id = character.id.value))
            call.respondRedirect(redirect)
        }
    }
}

fun Route.characterPage(playerRepository: PlayerRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CharacterResource.ByID> { resource ->
            val session = call.principal<AppSession>()!!
            val player = playerRepository.findByUser(session.user)!!
            val character = player.characters().firstOrNull { char ->
                char.id == ID(resource.id)
            }
            if (character == null) {
                throw NotFoundException()
            } else {
                call.respondPage(CharacterPage(character))
            }
        }
    }
}

private fun parseCharacterSpec(parameters: Parameters): CharacterSpec {
    val name = parameters["name"]
    if (name == null) {
        error("Name parameter mustn't be null")
    } else {
        return CharacterSpec(name = name)
    }
}

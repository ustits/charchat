package charchat.routes

import charchat.domain.ID
import charchat.domain.UserRepository
import charchat.plugins.AppSession
import charchat.plugins.SESSION_LOGIN_CONFIGURATION_NAME
import io.ktor.resources.*
import io.ktor.server.resources.post
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/campaign")
object CampaignResource

fun Route.createCampaign(userRepository: UserRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        post<CampaignResource> {
            val session = call.principal<AppSession>()!!
            val user = userRepository.findByIDOrNull(ID(session.userID))!!
            user.createCampaign()
            call.respondRedirect("/")
        }
    }
}
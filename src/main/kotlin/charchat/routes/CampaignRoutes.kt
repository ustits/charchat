package charchat.routes

import charchat.domain.ID
import charchat.domain.UserRepository
import charchat.html.pages.CampaignPage
import charchat.plugins.AppSession
import charchat.plugins.SESSION_LOGIN_CONFIGURATION_NAME
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/campaign")
object CampaignResource {

    @Serializable
    @Resource("/{id}")
    class ByID(val root: CampaignResource = CampaignResource, val id: Int)

}

fun Route.createCampaign(userRepository: UserRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        post<CampaignResource> {
            val session = call.principal<AppSession>()!!
            val user = userRepository.findByID(ID(session.userID))!!
            val campaign = user.createCampaign()
            val redirect = application.href(CampaignResource.ByID(id = campaign.id.value))
            call.respondRedirect(redirect)
        }
    }
}

fun Route.campaignPage(userRepository: UserRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CampaignResource.ByID> { resource ->
            val session = call.principal<AppSession>()!!
            val user = userRepository.findByID(ID(session.userID))!!
            val campaign = user.campaigns().firstOrNull { campaign ->
                campaign.id.value == resource.id
            }
            if (campaign == null) {
                throw NotFoundException()
            } else {
                call.respondPage(CampaignPage(campaign))
            }
        }
    }
}

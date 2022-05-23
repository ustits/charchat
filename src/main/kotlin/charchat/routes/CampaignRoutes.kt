package charchat.routes

import charchat.domain.ID
import charchat.domain.UserRepository
import charchat.html.pages.CampaignPage
import charchat.html.pages.InvitePage
import charchat.plugins.AppSession
import charchat.plugins.SESSION_LOGIN_CONFIGURATION_NAME
import charchat.plugins.baseURL
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
    class ByID(val root: CampaignResource = CampaignResource, val id: Int) {

        @Serializable
        @Resource("/invite/{inviteID}")
        class WithInvite(val root: ByID, val inviteID: String)

    }

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
                val invite = campaign.invite().print()
                val inviteURL = application.baseURL() + application.href(
                    CampaignResource.ByID.WithInvite(resource, invite)
                )
                call.respondPage(CampaignPage(campaign, inviteURL))
            }
        }
    }
}

fun Route.inviteForm(userRepository: UserRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CampaignResource.ByID.WithInvite> {
            val session = call.principal<AppSession>()!!
            val user = userRepository.findByID(ID(session.userID))!!
            call.respondPage(InvitePage(user))
        }
    }
}

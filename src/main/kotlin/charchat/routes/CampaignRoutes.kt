package charchat.routes

import charchat.domain.CampaignRepository
import charchat.domain.CharacterRepository
import charchat.domain.ID
import charchat.domain.InviteRepository
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
        class WithInvite(val root: ByID, val inviteID: String) {

            @Serializable
            @Resource("character/{characterID}")
            class Join(val root: WithInvite, val characterID: Int)

        }

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

fun Route.campaignPage(campaignRepository: CampaignRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CampaignResource.ByID> { resource ->
            val campaign = campaignRepository.findByID(ID(resource.id))
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

fun Route.inviteForm(userRepository: UserRepository, inviteRepository: InviteRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CampaignResource.ByID.WithInvite> { resource ->
            val session = call.principal<AppSession>()!!
            val user = userRepository.findByID(ID(session.userID))!!
            val invite = inviteRepository.findByText(resource.inviteID)
            if (invite == null) {
                throw NotFoundException()
            } else {
                call.respondPage(InvitePage(user, invite))
            }

        }
    }
}

fun Route.joinCharacter(inviteRepository: InviteRepository, characterRepository: CharacterRepository) {
    authenticate(SESSION_LOGIN_CONFIGURATION_NAME) {
        get<CampaignResource.ByID.WithInvite.Join> { resource ->
            val invite = inviteRepository.findByText(resource.root.inviteID)
            val character = characterRepository.findByID(ID(resource.characterID))
            if (invite == null || character == null) {
                throw NotFoundException()
            } else {
                val campaign = invite.campaign
                campaign.addCharacter(character)
                val redirect = application.href(CampaignResource.ByID(id = campaign.id.value))
                call.respondRedirect(redirect)
            }
        }
    }
}

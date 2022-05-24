package charchat.html.pages

import charchat.domain.Invite
import charchat.domain.User
import charchat.html.Layout
import charchat.routes.CampaignResource
import io.ktor.server.resources.*
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.role

class InvitePage(private val user: User, private val invite: Invite) : Page {

    override fun Layout.apply() {
        val characters = user.characters()

        val inviteURL = CampaignResource.ByID.WithInvite(
            root = CampaignResource.ByID(id = invite.campaign.id.value),
            inviteID = invite.print()
        )

        content {
            h2 {
                +"Join with"
            }
            characters.forEach { char ->
                article {
                    p {
                        +char.name
                    }
                    a(
                        href = application.href(
                            CampaignResource.ByID.WithInvite.Join(inviteURL, char.id.value)
                        )
                    ) {
                        role = "button"
                        +"Join"
                    }
                }
            }
        }
    }
}

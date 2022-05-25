package charchat.html.pages

import charchat.domain.Invite
import charchat.html.Layout
import charchat.routes.CampaignResource
import io.ktor.server.resources.*
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.role

class InvitePage(
    private val characters: List<charchat.domain.Character>,
    private val invite: Invite
) : Page {

    override fun Layout.apply() {
        val inviteURL = CampaignResource.ByID.WithInvite(
            root = CampaignResource.ByID(id = invite.campaign.id.value),
            inviteID = invite.print()
        )

        content {
            h2 {
                +"Join campaign \"${invite.campaign.name}\" with"
            }
            if (characters.isEmpty()) {
                p {
                    +"You have no characters to add. Try creating one first"
                }
            } else {
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
}

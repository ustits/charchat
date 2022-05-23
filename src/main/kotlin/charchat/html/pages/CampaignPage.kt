package charchat.html.pages

import charchat.domain.Campaign
import charchat.html.Layout
import charchat.html.templates.Chat
import io.ktor.server.html.*
import kotlinx.html.a
import kotlinx.html.h2
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.ul

class CampaignPage(private val campaign: Campaign, private val inviteURL: String) : Page {

    override fun Layout.apply() {
        content {
            val characters = campaign.characters()
            h2 {
                +"Characters"
            }
            if (characters.isNotEmpty()) {
                ul {
                    characters.forEach { char ->
                        li {
                            +char.name
                        }
                    }

                }
            } else {
                p {
                    +"There are no characters yet. Try adding some!"
                }
            }

            p {
                +"Invite: "
                a(href = inviteURL) { +inviteURL }
            }
            insert(Chat("/chat")) {
                name = "Example chat"
            }
        }

    }
}

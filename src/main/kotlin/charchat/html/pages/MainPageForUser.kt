package charchat.html.pages

import charchat.domain.Campaign
import charchat.domain.User
import charchat.html.Layout
import charchat.routes.CampaignResource
import charchat.routes.CharacterResource
import io.ktor.server.resources.*
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.form
import kotlinx.html.h2
import kotlinx.html.label
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.submitInput
import kotlinx.html.textInput
import kotlinx.html.ul

class MainPageForUser(
    private val createCampaignURL: String,
    private val createCharacterURL: String,
    private val user: User,
    private val campaigns: List<Campaign>
) : Page {

    override fun Layout.apply() {
        val characters = user.characters()
        content {
            form(action = createCampaignURL, method = FormMethod.post) {
                submitInput {
                    value = "Create campaign"
                }
            }
            form(action = createCharacterURL, method = FormMethod.post) {
                label {
                    +"Character name"
                    textInput(name = "name")
                }

                submitInput {
                    value = "Create character"
                }
            }
            h2 {
                +"Campaigns"
            }
            if (campaigns.isNotEmpty()) {
                ul {
                    campaigns.forEach {campaign ->
                        li {
                            a(href = application.href(CampaignResource.ByID(id = campaign.id.value))) {
                                +campaign.name
                            }
                        }
                    }
                }
            } else {
                p {
                    +"No campaigns yet"
                }
            }
            h2 {
                +"Characters"
            }
            if (characters.isNotEmpty()) {
                ul {
                    characters.forEach { char ->
                        li {
                            a(href = application.href(CharacterResource.ByID(id = char.id.value))) {
                                +char.name
                            }
                        }
                    }
                }
            } else {
                p {
                    +"No characters yet"
                }
            }
        }
    }
}

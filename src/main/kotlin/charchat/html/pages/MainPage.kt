package charchat.html.pages

import charchat.html.Layout
import charchat.html.templates.Chat
import io.ktor.server.html.*
import kotlinx.html.FormMethod
import kotlinx.html.form
import kotlinx.html.label
import kotlinx.html.submitInput
import kotlinx.html.textInput

class MainPage(
    private val createCampaignURL: String,
    private val createCharacterURL: String
) : Page {

    override fun Layout.apply() {
        content {
            if (appSession != null) {
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
            }
            insert(Chat("/chat")) {
                name = "Example chat"
            }
        }
    }
}

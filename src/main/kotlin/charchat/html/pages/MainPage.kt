package charchat.html.pages

import charchat.html.Layout
import charchat.html.templates.Chat
import io.ktor.server.html.*
import kotlinx.html.FormMethod
import kotlinx.html.form
import kotlinx.html.submitInput

class MainPage(private val createCampaignURL: String) : Page {

    override fun Layout.apply() {
        content {
            if (appSession != null) {
                form(action = createCampaignURL, method = FormMethod.post) {
                    submitInput {
                        value = "Create campaign"
                    }
                }
            }
            insert(Chat("/chat")) {
                name ="Example chat"
            }
        }
    }
}

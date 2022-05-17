package charchat.html.pages

import charchat.html.Layout
import kotlinx.html.FormMethod
import kotlinx.html.form
import kotlinx.html.label
import kotlinx.html.submitInput
import kotlinx.html.textInput

class MainPageForUser(
    private val createCampaignURL: String,
    private val createCharacterURL: String
) : Page {

    override fun Layout.apply() {
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
        }
    }
}

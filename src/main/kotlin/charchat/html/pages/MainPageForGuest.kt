package charchat.html.pages

import charchat.html.Layout
import charchat.html.templates.Chat
import io.ktor.server.html.*

class MainPageForGuest : Page {

    override fun Layout.apply() {
        content {
            insert(Chat("/chat")) {
                name = "Example chat"
            }
        }
    }
}

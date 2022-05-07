package charchat.html.pages

import charchat.html.Layout
import charchat.html.templates.Chat
import io.ktor.server.html.*

class MainPage : Page {

    override fun Layout.apply() {
        content {
            insert(Chat()) {
                name ="Example chat"
                message {
                    sender = "Кука Кук"
                    text = "Кука желай знать где сидит пестрый птица"
                }
                message {
                    sender = "Торменто"
                    text = "Я...буду...говорить...очень...медленно..."
                }
                message {
                    sender = "Зун Пупок"
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                }
            }
        }
    }
}

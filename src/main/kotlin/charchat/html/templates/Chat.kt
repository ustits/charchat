package charchat.html.templates

import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.strong
import kotlinx.html.textArea

class Chat(private val websocketURL: String) : Template<FlowContent> {

    var name: String = ""

    override fun FlowContent.apply() {
        h1 {
            +name
        }
        div {
            attributes["hx-ext"] = "ws"
            attributes["ws-connect"] = websocketURL
            div("chat") {
                div {
                    id = "messages"
                }
            }
            form {
                attributes["ws-send"] = ""
                textArea {
                    name = "message"
                    required = true
                }
                input(type = InputType.submit)
            }
        }
    }

    class Message : Template<FlowContent> {

        var sender: String = ""
        var text: String = ""

        override fun FlowContent.apply() {
            div("message") {
                header {
                    strong {
                        +sender
                    }
                }
                +text
            }
        }
    }
}

package charchat.html.templates

import dev.ustits.hyperscript.hyperscript
import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.stream.createHTML
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
                hyperscript = """
                        on submit 
                        call me.reset() 
                    """.trimIndent()
                attributes["ws-send"] = ""
                textArea {
                    name = "message"
                    required = true
                }
                input(type = InputType.submit)
            }
        }
    }

    class Message(private val sender: String, private val text: String) : Template<FlowContent> {

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

        fun toHtmxHtml(): String {
            val html = createHTML()
            return html.div {
                attributes["hx-swap-oob"] = "beforeend:#messages"
                insert(this@Message) {
                }
            }
        }

    }
}

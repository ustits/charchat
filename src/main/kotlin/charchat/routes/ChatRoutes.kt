package charchat.routes

import charchat.html.templates.Chat
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.div
import kotlinx.html.stream.createHTML

private val testMessages = listOf(
    Message("Кука Кук", "Кука желай знать где сидит пестрый птица"),
    Message("Торменто", "Я...буду...говорить...очень...медленно..."),
    Message(
        "Зун Пупок",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    ),
)

fun Route.wsChat() {
    webSocket("/chat") {
        launch {
            testMessages.forEach {
                delay(2000)
                send(it.toHtml())
            }
        }

        try {
            handleMessages()
        } catch (e: Throwable) {
            application.log.error("Channel was closed with reason: ${closeReason.await()}")
        }
    }
}

private suspend fun DefaultWebSocketServerSession.handleMessages() {
    for (frame in incoming) {
        when (frame) {
            is Frame.Text -> {
                val receivedText = frame.readText()
                application.log.debug("Received text frame: $receivedText")
            }
            else -> {
                error("Frame $frame not implemented")
            }
        }
    }
}

private class Message(val sender: String, val text: String) {

    fun toHtml(): String {
        val html = createHTML()
        return html.div {
            attributes["hx-swap-oob"] = "beforeend:#messages"
            insert(Chat.Message()) {
                this.sender = this@Message.sender
                this.text = this@Message.text
            }
        }
    }
}

package charchat.routes

import charchat.html.templates.Chat
import charchat.plugins.AppSession
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val testMessages = listOf(
    Chat.Message("Кука Кук", "Кука желай знать где сидит пестрый птица"),
    Chat.Message("Торменто", "Я...буду...говорить...очень...медленно..."),
    Chat.Message(
        "Зун Пупок",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    ),
)

fun Route.wsChat() {
    webSocket("/chat") {
        launch {
            testMessages.forEach {
                delay(750)
                send(it.toHtmxHtml())
            }
        }

        while (true) {
            try {
                val session = call.sessions.get<AppSession>()
                val wsMessage = receiveDeserialized<HTMXWsMessage>()
                application.log.debug("Received text frame: ${wsMessage.message}, for userID: ${session?.userID ?: "anonymous"}")
                val toSend = Chat.Message(sender = "Кука Кук", text = wsMessage.message)
                send(toSend.toHtmxHtml())
            } catch (e: ClosedReceiveChannelException) {
                application.log.error("Channel was closed with reason: ${closeReason.await()}")
                break
            }
        }
    }
}

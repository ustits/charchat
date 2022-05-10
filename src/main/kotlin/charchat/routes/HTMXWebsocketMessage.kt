package charchat.routes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HTMXWebsocketMessage(
    val message: String,
    @SerialName("HEADERS")
    val headers: Headers
)

@Serializable
data class Headers(
    @SerialName("HX-Request")
    val hxRequest: String,
    @SerialName("HX-Trigger")
    val hxTrigger: String?,
    @SerialName("HX-Trigger-Name")
    val hxTriggerName: String?,
    @SerialName("HX-Target")
    val hxTarget: String?,
    @SerialName("HX-Current-URL")
    val hxCurrentURL: String
)

typealias HTMXWsMessage = HTMXWebsocketMessage

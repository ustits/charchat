package charchat.domain

sealed class Event

data class ActionEvent(val actor: Character, val action: Action) : Event()

class WorldEvent(val text: String) : Event()

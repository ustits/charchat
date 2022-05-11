package charchat.domain

data class Action(val sender: String, val text: String, val type: ActionType)

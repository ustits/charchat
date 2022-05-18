package charchat.domain

class Invite(
    val campaign: Campaign,
    private val id: String
) {

    fun print(): String {
        return id
    }

}

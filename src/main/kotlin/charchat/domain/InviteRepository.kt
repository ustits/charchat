package charchat.domain

interface InviteRepository {

    fun findByText(str: String): Invite?

}

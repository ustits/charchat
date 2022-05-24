package charchat.domain

interface PartyMemberRepository {

    fun add(partyMember: PartyMember)

    fun delete(partyMember: PartyMember)

}

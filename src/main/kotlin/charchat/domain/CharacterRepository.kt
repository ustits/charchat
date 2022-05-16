package charchat.domain

interface CharacterRepository {

    fun findByID(id: ID): Character?

    fun findByUserID(userID: ID): List<Character>

}

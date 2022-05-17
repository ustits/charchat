package charchat.domain

interface CharacterRepository {

    fun findByID(id: ID): Character?

    fun findByUser(user: User): List<Character>

    fun findByCampaign(campaign: Campaign): List<Character>

    fun findByScene(scene: Scene): List<Character>

}

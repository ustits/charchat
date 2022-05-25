package charchat.domain

interface CharacterRepository {

    fun findByID(id: ID): Character?

    fun findAllByPlayer(player: Player): List<Character>

    fun findAllByCampaign(campaign: Campaign): List<Character>

    fun findAllByScene(scene: Scene): List<Character>

}

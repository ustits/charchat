package charchat.domain

interface CharacterRepository {

    fun findByID(id: ID): Character?

    fun findByUserID(userID: ID): List<Character>

    fun findByCampaignID(campaignID: ID): List<Character>

    fun findBySceneID(sceneID: ID): List<Character>

}

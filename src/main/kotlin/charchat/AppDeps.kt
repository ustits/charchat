package charchat

import charchat.adapters.DBCampaignFactory
import charchat.adapters.DBCharacterFactory
import charchat.adapters.DBSceneFactory
import charchat.adapters.DBUserRepository
import charchat.auth.SqliteUserPrincipalRepository
import charchat.auth.UserPrincipalRepository
import charchat.domain.CampaignFactory
import charchat.domain.CharacterFactory
import charchat.domain.SceneFactory
import charchat.domain.UserRepository

class AppDeps {

    private val sceneFactory: SceneFactory = DBSceneFactory()
    private val campaignFactory: CampaignFactory = DBCampaignFactory(sceneFactory)
    private val characterFactory: CharacterFactory = DBCharacterFactory()
    private val userRepository: UserRepository = DBUserRepository(campaignFactory, characterFactory)
    private val userPrincipalRepository: UserPrincipalRepository = SqliteUserPrincipalRepository()

    fun userPrincipalRepository(): UserPrincipalRepository = userPrincipalRepository

    fun userRepository(): UserRepository = userRepository

}

package charchat

import charchat.adapters.DBCampaignFactory
import charchat.adapters.DBCampaignRepository
import charchat.adapters.DBCharacterFactory
import charchat.adapters.DBCharacterRepository
import charchat.adapters.DBSceneFactory
import charchat.adapters.DBUserRepository
import charchat.auth.SqliteUserPrincipalRepository
import charchat.auth.UserPrincipalRepository
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.SceneFactory
import charchat.domain.UserRepository

class AppDeps {

    private val characterFactory: CharacterFactory = DBCharacterFactory()
    private val characterRepository: CharacterRepository = DBCharacterRepository()
    private val sceneFactory: SceneFactory = DBSceneFactory()
    private val campaignFactory: CampaignFactory = DBCampaignFactory(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository
    )
    private val campaignRepository: CampaignRepository = DBCampaignRepository(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository
    )
    private val userRepository: UserRepository = DBUserRepository(
        campaignFactory = campaignFactory,
        campaignRepository = campaignRepository,
        characterFactory = characterFactory,
        characterRepository = characterRepository
    )
    private val userPrincipalRepository: UserPrincipalRepository = SqliteUserPrincipalRepository()

    fun userPrincipalRepository(): UserPrincipalRepository = userPrincipalRepository

    fun userRepository(): UserRepository = userRepository

}

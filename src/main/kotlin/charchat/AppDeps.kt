package charchat

import charchat.adapters.DBCampaignFactory
import charchat.adapters.DBCampaignRepository
import charchat.adapters.DBCharacterFactory
import charchat.adapters.DBCharacterRepository
import charchat.adapters.DBSceneFactory
import charchat.adapters.DBUserRepository
import charchat.adapters.HashidsInviteFactory
import charchat.adapters.HashidsInviteRepository
import charchat.auth.SqliteUserPrincipalRepository
import charchat.auth.UserPrincipalRepository
import charchat.config.AppConfig
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.InviteFactory
import charchat.domain.InviteRepository
import charchat.domain.SceneFactory
import charchat.domain.UserRepository
import org.hashids.Hashids

class AppDeps(appConfig: AppConfig) {

    private val hashids = Hashids(appConfig.salt, appConfig.length)
    private val characterFactory: CharacterFactory = DBCharacterFactory()
    private val inviteFactory: InviteFactory = HashidsInviteFactory(
        hashids = hashids
    )
    private val characterRepository: CharacterRepository = DBCharacterRepository()
    private val sceneFactory: SceneFactory = DBSceneFactory(
        characterRepository = characterRepository
    )
    private val campaignFactory: CampaignFactory = DBCampaignFactory(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository,
        inviteFactory = inviteFactory
    )
    private val campaignRepository: CampaignRepository = DBCampaignRepository(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository,
        inviteFactory = inviteFactory
    )
    private val inviteRepository: InviteRepository = HashidsInviteRepository(
        hashids = hashids,
        campaignRepository = campaignRepository
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

    fun inviteRepository(): InviteRepository = inviteRepository

}

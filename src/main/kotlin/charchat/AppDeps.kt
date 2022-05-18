package charchat

import charchat.adapters.DBCampaigns
import charchat.adapters.DBCharacters
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
    private val dbCharacters: DBCharacters = DBCharacters()
    private val characterFactory: CharacterFactory = dbCharacters
    private val inviteFactory: InviteFactory = HashidsInviteFactory(
        hashids = hashids
    )
    private val characterRepository: CharacterRepository = dbCharacters
    private val sceneFactory: SceneFactory = DBSceneFactory(
        characterRepository = characterRepository
    )
    private val dbCampaigns: DBCampaigns = DBCampaigns(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository,
        inviteFactory = inviteFactory
    )
    private val campaignFactory: CampaignFactory = dbCampaigns
    private val campaignRepository: CampaignRepository = dbCampaigns
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

package charchat

import charchat.adapters.DBCampaigns
import charchat.adapters.DBCharacters
import charchat.adapters.DBPartyMemberRepository
import charchat.adapters.DBSceneFactory
import charchat.adapters.HashidsInviteFactory
import charchat.adapters.HashidsInviteRepository
import charchat.auth.SqliteUserPrincipalRepository
import charchat.auth.UserPrincipalRepository
import charchat.config.Configuration
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterFactory
import charchat.domain.CharacterRepository
import charchat.domain.DungeonMasterRepository
import charchat.domain.InviteFactory
import charchat.domain.InviteRepository
import charchat.domain.PartyMemberRepository
import charchat.domain.PlayerRepository
import charchat.domain.SceneFactory
import org.hashids.Hashids

class AppDeps(val config: Configuration) {

    private val hashids = Hashids(config.app.salt, config.app.length)
    private val dbCharacters: DBCharacters = DBCharacters()
    private val characterFactory: CharacterFactory = dbCharacters
    private val inviteFactory: InviteFactory = HashidsInviteFactory(
        hashids = hashids
    )
    private val partyMemberRepository: PartyMemberRepository = DBPartyMemberRepository()
    private val characterRepository: CharacterRepository = dbCharacters
    private val sceneFactory: SceneFactory = DBSceneFactory()
    private val dbCampaigns: DBCampaigns = DBCampaigns(
        sceneFactory = sceneFactory,
        characterRepository = characterRepository,
        inviteFactory = inviteFactory,
        partyMemberRepository = partyMemberRepository
    )
    private val campaignFactory: CampaignFactory = dbCampaigns
    private val campaignRepository: CampaignRepository = dbCampaigns
    private val inviteRepository: InviteRepository = HashidsInviteRepository(
        hashids = hashids,
        campaignRepository = campaignRepository
    )
    private val dungeonMasterRepository: DungeonMasterRepository = DungeonMasterRepository.ByUser(
        campaignFactory = campaignFactory,
        campaignRepository = campaignRepository
    )
    private val playerRepository: PlayerRepository = PlayerRepository.ByUser(
        characterFactory = characterFactory,
        characterRepository = characterRepository
    )
    private val userPrincipalRepository: UserPrincipalRepository = SqliteUserPrincipalRepository()

    fun userPrincipalRepository(): UserPrincipalRepository = userPrincipalRepository

    fun inviteRepository(): InviteRepository = inviteRepository

    fun characterRepository(): CharacterRepository = characterRepository

    fun campaignRepository(): CampaignRepository = campaignRepository

    fun dungeonMasterRepository(): DungeonMasterRepository = dungeonMasterRepository

    fun playerRepository(): PlayerRepository = playerRepository

}

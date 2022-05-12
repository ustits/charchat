package charchat

import charchat.adapters.DBCampaignFactory
import charchat.adapters.DBUserRepository
import charchat.auth.SqliteUserPrincipalRepository
import charchat.auth.UserPrincipalRepository
import charchat.domain.CampaignFactory
import charchat.domain.CharacterFactory
import charchat.domain.UserRepository

class AppDeps {

    fun userPrincipalRepository(): UserPrincipalRepository {
        return SqliteUserPrincipalRepository()
    }

    fun userRepository(): UserRepository {
        return DBUserRepository(campaignFactory(), CharacterFactory.Stub())
    }

    private fun campaignFactory(): CampaignFactory {
        return DBCampaignFactory()
    }

}

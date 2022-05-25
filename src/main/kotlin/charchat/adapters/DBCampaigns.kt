package charchat.adapters

import charchat.db.first
import charchat.db.firstOrNull
import charchat.db.toSequence
import charchat.db.sql
import charchat.domain.Campaign
import charchat.domain.CampaignFactory
import charchat.domain.CampaignRepository
import charchat.domain.CharacterRepository
import charchat.domain.DungeonMaster
import charchat.domain.ID
import charchat.domain.InviteFactory
import charchat.domain.PartyMemberRepository
import charchat.domain.SceneFactory
import java.sql.ResultSet

class DBCampaigns(
    private val sceneFactory: SceneFactory,
    private val characterRepository: CharacterRepository,
    private val inviteFactory: InviteFactory,
    private val partyMemberRepository: PartyMemberRepository
) : CampaignFactory, CampaignRepository {

    override fun create(dm: DungeonMaster, name: String): Campaign {
        return sql(
            """
                INSERT INTO campaigns (name, dm, created_at) 
                VALUES (?, ?, date('now'))
                RETURNING id, name
            """.trimIndent()
        ) {
            setString(1, name)
            setInt(2, dm.user.id.value)
            executeQuery().first {
                toCampaign()
            }
        }
    }

    override fun findByID(id: ID): Campaign? {
        return sql(
            """
                SELECT id, name FROM campaigns
                WHERE id = ?
            """.trimIndent()
        ) {
            setInt(1, id.value)
            executeQuery().firstOrNull {
                toCampaign()
            }
        }
    }

    override fun findAllByDungeonMaster(dm: DungeonMaster): List<Campaign> {
        return sql(
            """
                SELECT id, name FROM campaigns
                WHERE dm = ?
            """.trimIndent()
        ) {
            setInt(1, dm.user.id.value)
            executeQuery().toSequence {
                toCampaign()
            }.toList()
        }
    }

    private fun ResultSet.toCampaign(): Campaign {
        return Campaign(
            id = ID(getInt("id")),
            name = getString("name"),
            sceneFactory = sceneFactory,
            characterRepository = characterRepository,
            inviteFactory = inviteFactory,
            partyMemberRepository = partyMemberRepository
        )
    }
}

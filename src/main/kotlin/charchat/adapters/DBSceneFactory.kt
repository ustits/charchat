package charchat.adapters

import charchat.db.first
import charchat.db.sql
import charchat.domain.Campaign
import charchat.domain.ID
import charchat.domain.Scene
import charchat.domain.SceneFactory

class DBSceneFactory : SceneFactory {

    override fun create(campaign: Campaign, name: String): Scene {
        return sql(
            """
                INSERT INTO scenes(name, campaign, created_at)
                VALUES (?, ?, date('now'))
                RETURNING id
            """.trimIndent()
        ) {
            setString(1, name)
            setInt(2, campaign.id.value)
            executeQuery().first {
                Scene(
                    id = ID(getInt("id")),
                    partyMembers = emptyList()
                )
            }
        }
    }
}

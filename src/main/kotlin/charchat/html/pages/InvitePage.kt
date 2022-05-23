package charchat.html.pages

import charchat.domain.User
import charchat.html.Layout
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.role

class InvitePage(private val user: User) : Page {

    override fun Layout.apply() {
        val characters = user.characters()
        content {
            h2 {
                +"Join with"
            }
            characters.forEach { char ->
                article {
                    p {
                        +char.name
                    }
                    a {
                        role = "button"
                        +"Join"
                    }
                }
            }
        }
    }
}

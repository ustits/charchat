package charchat.html.pages

import charchat.html.Layout
import kotlinx.html.h2

class CharacterPage(private val character: charchat.domain.Character) : Page {

    override fun Layout.apply() {
        content {
            h2 {
                +character.name
            }
        }
    }
}

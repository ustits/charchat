package charchat.html.pages

import charchat.html.Layout
import kotlinx.html.p

class NotFoundPage : Page {

    override fun Layout.apply() {
        content {
            p {
                +"There is nothing here"
            }
        }
    }
}

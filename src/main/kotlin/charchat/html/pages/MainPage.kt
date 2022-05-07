package charchat.html.pages

import charchat.html.Layout
import kotlinx.html.h1

class MainPage : Page {

    override fun Layout.apply() {
        content {
            h1 {
                +"CharChat"
            }
        }
    }
}

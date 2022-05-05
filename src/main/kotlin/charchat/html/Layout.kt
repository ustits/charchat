package charchat.html

import charchat.plugins.AppSession
import io.ktor.server.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.head
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.nav
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.script
import kotlinx.html.small
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul

class Layout(private val loginURL: String, private val appSession: AppSession?) : Template<HTML> {

    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            title { +"CharChat" }
            meta { charset = "UTF-8" }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1"
            }

            link(rel = "stylesheet", href = "/assets/pico/css/pico.min.css")
            script { src = "/assets/htmx.org/htmx.min.js" }
            script { src = "/assets/hyperscript.org/_hyperscript_web.min.js" }
        }
        body {
            div("container-fluid") {
                nav {
                    ul {
                        li {
                            a(href = "/", classes = "contrast logo") {
                                strong {
                                    +"CharChat"
                                }
                            }
                        }
                    }
                    if (appSession == null) {
                        ul {
                            li {
                                a(href = loginURL) {
                                    role = "button"
                                    +"Login"
                                }
                            }
                        }
                    } else if (appSession.name != null && appSession.name.isNotBlank()) {
                        p {
                            +appSession.name
                        }
                    }
                }
            }
            main(classes = "container") {
                insert(content)
            }
            footer("container-fluid") {
                small {
                    p { +"by Ruslan Ustits" }
                    a(href = "https://github.com/ustits/charchat") { +"Github" }
                }
            }
        }
    }
}

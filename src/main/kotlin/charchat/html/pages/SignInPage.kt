package charchat.html.pages

import charchat.html.Layout
import charchat.plugins.FORM_LOGIN_EMAIL_FIELD
import charchat.plugins.FORM_LOGIN_PASSWORD_FIELD
import charchat.routes.SignIn
import kotlinx.html.FormMethod
import kotlinx.html.emailInput
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.label
import kotlinx.html.passwordInput
import kotlinx.html.submitInput

class SignInPage(private val resource: SignIn) : Page {

    override fun Layout.apply() {
        content {
            if (isFailedAuth()) {
                h3 {
                    +"Email or password were incorrect"
                }
            }
            form(action = signInURL, method = FormMethod.post) {
                label {
                    +"Email"
                    emailInput(name = FORM_LOGIN_EMAIL_FIELD) {
                        placeholder = "gendalf@tatooine.rick"
                        required = true
                        if (isFailedAuth()) {
                            attributes["aria-invalid"] = "true"
                        }
                    }
                }

                label {
                    +"Password"
                    passwordInput(name = FORM_LOGIN_PASSWORD_FIELD) {
                        required = true
                        if (isFailedAuth()) {
                            attributes["aria-invalid"] = "true"
                        }
                    }
                }

                submitInput {
                    value = "Sign In"
                }
            }
        }
    }

    private fun isFailedAuth(): Boolean {
        return resource.failed ?: false
    }

}

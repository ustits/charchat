package charchat.html.pages

import charchat.html.Layout
import charchat.plugins.FORM_LOGIN_EMAIL_FIELD
import charchat.plugins.FORM_LOGIN_PASSWORD_FIELD
import charchat.routes.SignUp
import kotlinx.html.FormMethod
import kotlinx.html.emailInput
import kotlinx.html.form
import kotlinx.html.label
import kotlinx.html.passwordInput
import kotlinx.html.small
import kotlinx.html.submitInput

class SignUpPage(private val resource: SignUp) : Page {

    override fun Layout.apply() {
        content {
            form(action = signUpURL, method = FormMethod.post) {
                label {
                    +"Email"
                    emailInput(name = FORM_LOGIN_EMAIL_FIELD) {
                        placeholder = "gendalf@tatooine.rick"
                        required = true
                        if (userExists()) {
                            attributes["aria-invalid"] = "true"
                            small {
                                +"Such email already exists. Try another email or sign in"
                            }
                        }
                    }
                }

                label {
                    +"Password"
                    passwordInput(name = FORM_LOGIN_PASSWORD_FIELD) {
                        required = true
                    }
                }

                submitInput {
                    value = "Sign Up"
                }
            }
        }
    }

    private fun userExists(): Boolean {
        return resource.userExists ?: false
    }

}

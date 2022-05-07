package charchat.html.pages

import charchat.html.Layout
import charchat.plugins.FORM_LOGIN_EMAIL_FIELD
import charchat.plugins.FORM_LOGIN_PASSWORD_FIELD
import kotlinx.html.FormMethod
import kotlinx.html.emailInput
import kotlinx.html.form
import kotlinx.html.label
import kotlinx.html.passwordInput
import kotlinx.html.submitInput

class SignUpPage : Page {

    override fun Layout.apply() {
        content {
            form(action = signUpURL, method = FormMethod.post) {
                label {
                    +"Email"
                    emailInput(name = FORM_LOGIN_EMAIL_FIELD) {
                        placeholder = "gendalf@tatooine.rick"
                        required = true
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
}

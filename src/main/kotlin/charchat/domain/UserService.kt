package charchat.domain

class UserService(private val characterFactory: CharacterFactory) {

    fun createCharacter(user: User, characterSpec: CharacterSpec): charchat.domain.Character {
        return characterFactory.create(user, characterSpec)
    }

}

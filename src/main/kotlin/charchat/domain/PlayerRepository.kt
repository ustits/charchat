package charchat.domain

interface PlayerRepository {

    fun findByUser(user: User): Player?

    class ByUser(
        private val characterFactory: CharacterFactory,
        private val characterRepository: CharacterRepository
    ) : PlayerRepository {

        override fun findByUser(user: User): Player {
            return Player(
                user = user,
                characterFactory = characterFactory,
                characterRepository = characterRepository
            )
        }
    }
}

package charchat.domain

interface PlayerRepository {

    fun findByID(id: ID): Player?

    class ByUser(
        private val userRepository: UserRepository,
        private val characterFactory: CharacterFactory,
        private val characterRepository: CharacterRepository
    ) : PlayerRepository {

        override fun findByID(id: ID): Player? {
            return userRepository.findByID(id)?.let {
                Player(
                    user = it,
                    characterFactory = characterFactory,
                    characterRepository = characterRepository
                )
            }
        }
    }
}

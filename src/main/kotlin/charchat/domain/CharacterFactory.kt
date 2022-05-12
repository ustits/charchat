package charchat.domain

interface CharacterFactory {

    fun create(name: String): Character

    class Stub : CharacterFactory {
        override fun create(name: String): Character {
            return Character(name)
        }
    }

}

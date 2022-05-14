package charchat.domain

interface SceneFactory {

    fun create(campaign: Campaign, name: String): Scene

}

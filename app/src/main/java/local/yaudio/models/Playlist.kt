package local.yaudio.models

class Playlist {
    private var currentIndex = -1

    fun setCurrentIndex(index: Int) : Boolean { return false }
    fun getNextIndex() : Int = currentIndex + 1
    fun getPreviousIndex(): Int = currentIndex - 1

    fun getCurrentAudio() : Audio? { return null }
}
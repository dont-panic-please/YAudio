package local.yaudio.models

class Audio {
    var audioId: String = ""
    var url: String = ""
    var title: String = "unknown"
    var channel: String = "unknown"
    var currentMillis: Int = 0
    var durationMillis: Int = 0
    var thumbnailImage: ByteArray? = null

    fun getPercentageProgress() : Int {
        return if(durationMillis != 0)
            currentMillis / durationMillis * 100
        else
            0
    }
}
package local.yaudio

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import local.yaudio.models.Audio
import local.yaudio.models.Playlist
import local.yaudio.utils.RunnablePeriodic

class PlayerService : Service() {
    private val binder = PlayerBinder()
    private val player = MediaPlayer()
    private var playlist: Playlist? = null
    private var currentAudio: Audio? = null

    private val _isStarted = MutableLiveData(false)
    private val _isPrepared = MutableLiveData(false)
    private val _isPlaying = MutableLiveData(false)
    private val _progressPercentage = MutableLiveData(0)
    val isStarted: LiveData<Boolean> = _isStarted
    val isPrepared: LiveData<Boolean> = _isPrepared
    val isPlaying: LiveData<Boolean> = _isPlaying
    val progressPercentage: LiveData<Int> = _progressPercentage

    private val progressUpdater = RunnablePeriodic({
        currentAudio!!.currentMillis = player.currentPosition
        _progressPercentage.value = currentAudio!!.getPercentageProgress()
    })
    private val logTag = "PLAYER_SERVICE"
    enum class Action(val action: String){
        PLAY("player.PLAY"),
        PAUSE("player.PAUSE"),
        SKIP_NEXT("player.NEXT"),
        SKIP_PREV("player.PREV")
    }


    fun prepare() {
        if(currentAudio != null) {
            _isPrepared.value = false
            player.setDataSource(currentAudio!!.url)
            player.prepareAsync()
            player.setOnPreparedListener {
                _isPrepared.value = true
                if(isPlaying.value == true)
                    execute(Action.PLAY)
            }
        }
    }

    fun execute(action: Action) {

    }

    fun getCurrentAudio() : Audio? = currentAudio

    inner class PlayerBinder : Binder() {
        fun getService() : PlayerService = this@PlayerService
    }

    override fun onBind(p0: Intent?): IBinder = binder
}
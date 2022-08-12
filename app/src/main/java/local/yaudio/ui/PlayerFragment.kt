package local.yaudio.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import local.yaudio.MainActivity
import local.yaudio.PlayerService
import local.yaudio.R
import local.yaudio.databinding.FragmentPlayerBinding
import local.yaudio.models.Audio

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var playerService: PlayerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.d(logTag, "Service connected")
            playerService = (service as PlayerService.PlayerBinder).getService()
            playerService.isStarted.observe(this@PlayerFragment) {
                if(it) (context as MainActivity).enablePlayerFragment()
                else (context as MainActivity).disablePlayerFragment()
            }
            playerService.isPrepared.observe(this@PlayerFragment) {
                setView(playerService.getCurrentAudio())
            }
            playerService.isPlaying.observe(this@PlayerFragment) {
                binding.buttonPlayPause.setImageResource(
                    if(it) R.drawable.ic_baseline_pause_48
                    else R.drawable.ic_baseline_play_arrow_48
                )
            }
            playerService.progressPercentage.observe(this@PlayerFragment) {
                binding.progressCircular.progress = it
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(logTag, "Service disconnected")
            onStop()
        }

    }

    private val logTag = "PLAYER_FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setView(audio: Audio?) {
        binding.imageThumbnail.setImageBitmap(
            getThumbnailBitmap(audio?.thumbnailImage)
        )
        binding.textTitle.text = audio?.title?: ""
        binding.textChannel.text = audio?.channel?: ""
    }

    private fun getThumbnailBitmap(byteArray: ByteArray?): Bitmap {
        return if(byteArray != null)
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        else
            BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_audiotrack_36)
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), PlayerService::class.java).also { intent ->
            requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        requireContext().unbindService(connection)
        (context as MainActivity).disablePlayerFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
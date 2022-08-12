package local.yaudio

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import local.yaudio.databinding.ActivityMainBinding
import local.yaudio.models.Playlist
import local.yaudio.ui.PlayerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var playerFragment: PlayerFragment? = null
    private var playerFragmentEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_search, R.id.navigation_queue, R.id.navigation_library
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun enablePlayerFragment() {
        Log.d(packageName, "enablePlayerFragment")
        if(playerFragment == null) {
            playerFragment = PlayerFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_player, playerFragment!!)
                .commit()
        }
        else if(!playerFragmentEnabled) {
            supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(playerFragment!!)
                .commit()
            playerFragmentEnabled = true
        }
    }

    fun disablePlayerFragment() {
        Log.d(packageName, "disablePlayerFragment")
        if(playerFragmentEnabled) {
            supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .hide(playerFragment!!)
                .commit()
        }
    }
}
package doji.music.player

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import doji.music.R
import doji.music.domain.AlbumRepo
import kotlinx.coroutines.flow.collect
import doji.music.presentation.player.PlayerViewModel
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.coroutines.launch

class PlayerActivity : AppCompatActivity() {
    companion object {
        const val INTENT_EXTRA_ALBUM = "album-name"
        fun launchIntent(context: Context, albumName: String): Intent {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(INTENT_EXTRA_ALBUM, albumName)
            return intent
        }
    }

    val playerViewModel: PlayerViewModel = PlayerViewModel(AlbumRepo())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupPlayer()
    }

    private fun setupPlayer()  = lifecycleScope.launch {
        val albumName = intent.getStringExtra(INTENT_EXTRA_ALBUM)
        playerViewModel.send(PlayerViewModel.Action.FetchAlbum(albumName))
        playerViewModel.state().collect {
            render(it)
        }
    }

    private fun render(state: PlayerViewModel.State) {
        when(state) {
            is PlayerViewModel.State.AlbumLoaded -> renderAlbum(state)
        }
    }

    private fun renderAlbum(state: PlayerViewModel.State.AlbumLoaded) {
        playerAlbum.text = state.album.name
        playerArtist.text = "ft. " + state.album.artist
        Picasso.get().load(state.album.image).into(cardImage)
    }
}
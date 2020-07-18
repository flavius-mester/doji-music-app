package doji.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import doji.music.domain.Album
import doji.music.domain.AlbumRepo
import doji.music.presentation.AlbumsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.album_item.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumsActivity : AppCompatActivity() {
    val albumsViewModel: AlbumsViewModel = AlbumsViewModel(AlbumRepo())
    val albumsAdapter = AlbumsAdapter(listener =  albumClickListener())

    private fun albumClickListener(): (Album) -> Unit = {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAlbums()
        setupButtons()
    }

    private fun setupButtons() {
        toggleButton.setOnClickListener {
            albumsViewModel.send(AlbumsViewModel.Action.ToggleLayout())
        }
    }

    private val gridLayoutManager = GridLayoutManager(this@AlbumsActivity, 2)

    private fun setupAlbums() = lifecycleScope.launch {
        albumsRV.setHasFixedSize(true)
        albumsRV.adapter = albumsAdapter
        albumsRV.layoutManager = gridLayoutManager
        albumsRV.itemAnimator?.changeDuration = 1000
        albumsViewModel.send(AlbumsViewModel.Action.Init)
        albumsViewModel.state().collect {
            render(it)
        }
    }

    fun columnCountFromLayoutType(layout: AlbumsViewModel.Layout): Int = when(layout) {
        AlbumsViewModel.Layout.Grid -> 2
        AlbumsViewModel.Layout.List -> 1
    }

    private fun render(state: AlbumsViewModel.State) {
        when(state) {
            is AlbumsViewModel.State.Empty -> Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT)
                .show()
            is AlbumsViewModel.State.Albums -> showAlbums(state)
        }
    }

    private fun showAlbums(state: AlbumsViewModel.State.Albums) {
        updateButtonIcon(state.layout)
        albumsAdapter.updateData(state.albums)
        albumsRV.post {
            TransitionManager.beginDelayedTransition(albumsRV)
            gridLayoutManager.spanCount = columnCountFromLayoutType(state.layout)
            albumsAdapter.notifyItemRangeChanged(0, albumsAdapter.itemCount)

        }
    }

    private fun updateButtonIcon(layout: AlbumsViewModel.Layout) {
        val iconResource = when (layout) {
            AlbumsViewModel.Layout.Grid -> R.drawable.view_agenda_outline
            AlbumsViewModel.Layout.List -> R.drawable.view_grid_outline
        }
        toggleButton.setImageResource(iconResource)
    }
}
package doji.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import doji.music.domain.Album
import doji.music.domain.AlbumRepo
import doji.music.presentation.AlbumsViewModel
import kotlinx.android.synthetic.main.activity_main.*
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
        toggleLayout.setOnClickListener {
            albumsViewModel.send(AlbumsViewModel.Action.ToggleLayout())
        }
    }

    private val gridLayoutManager = GridLayoutManager(this@AlbumsActivity, 2)
    private val linearLayoutManager = LinearLayoutManager(this@AlbumsActivity)

    private fun setupAlbums() = lifecycleScope.launch {
        albumsRV.setHasFixedSize(true)
        albumsRV.adapter = albumsAdapter
        albumsRV.layoutManager = gridLayoutManager
        albumsViewModel.send(AlbumsViewModel.Action.Init)
        albumsViewModel.state().collect {
            render(it)
        }
    }

    private fun render(state: AlbumsViewModel.State) {
        when(state) {
            is AlbumsViewModel.State.Empty -> Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT)
                .show()
            is AlbumsViewModel.State.Albums -> showAlbums(state)
        }
    }

    private fun showAlbums(state: AlbumsViewModel.State.Albums) {
        albumsRV.layoutManager = if(state.layout == AlbumsViewModel.Layout.Grid) gridLayoutManager else linearLayoutManager
        albumsAdapter.albums.clear()
        albumsAdapter.albums.addAll(state.albums)
        albumsAdapter.notifyDataSetChanged()
    }
}
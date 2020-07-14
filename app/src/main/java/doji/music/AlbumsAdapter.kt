package doji.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import doji.music.domain.Album

class AlbumsAdapter(val albums: MutableList<Album> = mutableListOf(), val listener: (Album) -> Unit = {}): RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.album_item, parent, false), listener)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindView(albums.get(position))
    }
}
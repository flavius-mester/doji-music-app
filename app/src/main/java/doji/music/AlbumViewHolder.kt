package doji.music

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import doji.music.domain.Album
import kotlinx.android.synthetic.main.album_item.view.*

class AlbumViewHolder(itemView: View, val listener: (Album) -> Unit) : RecyclerView.ViewHolder(itemView) {
    lateinit var album: Album
    init {
        itemView.setOnClickListener { listener(album) }
    }
    fun bindView(album: Album) {
        this.album = album

        itemView.label.text = album.name
        val picasso = Picasso.get()
        picasso.setLoggingEnabled(true)
        picasso.load(album.image)
            .resize(256, 256)
            .into(itemView.imageView)
    }
}

package doji.music.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import doji.music.CropSquareTransformation
import doji.music.domain.Album
import kotlinx.android.synthetic.main.album_item.view.*

class AlbumViewHolder(itemView: View, val listener: (Album) -> Unit) : RecyclerView.ViewHolder(itemView) {
    lateinit var album: Album
    init {
        itemView.setOnClickListener { listener(album) }
    }
    fun bindView(album: Album) {
        this.album = album
        itemView.name.text = album.name
        itemView.artist.text = "by ${album.artist}"
        val picasso = Picasso.get()
        picasso.setLoggingEnabled(true)
        picasso.load(album.image)
//            .transform(CropSquareTransformation())
//            .resize(200, 200)
            .into(itemView.imageView)
    }
}

package doji.music.domain

interface AlbumRepo {
    fun getAlbums(): List<Album>
    fun getAlbum(albumName: String): Album = this.getAlbums().first { it.name == albumName }
}
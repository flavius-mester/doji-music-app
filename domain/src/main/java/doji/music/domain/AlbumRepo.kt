package doji.music.domain

interface AlbumRepo {
    suspend fun getAlbums(): List<Album>
    suspend fun getAlbum(albumName: String): Album = getAlbums().first { it.name == albumName }
}
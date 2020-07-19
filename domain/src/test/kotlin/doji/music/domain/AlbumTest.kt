package doji.music.domain

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

class AlbumTest {

    val sut:AlbumRepo = object : AlbumRepo {
        override suspend fun getAlbums(): List<Album> {
            return listOf(
                Album("some", "artist", "link"),
                Album("some2", "artist2", "link"),
                Album("some3", "artist3", "link")
            )
        }
    }

    @Test
    fun `getAlbum returns result when store contains the album with name`() = runBlocking {
        val album = sut.getAlbum("some2")
        assertNotNull(album)
    }

    @Test
    fun `getAlbum returns null when store does not contain the album with name`() = runBlocking {
        val album = sut.getAlbum("somethingelse")
        assertNull(album)
    }

}


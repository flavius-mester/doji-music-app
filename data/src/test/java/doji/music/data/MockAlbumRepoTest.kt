package doji.music.data

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class MockAlbumRepoTest {

    val sut = MockAlbumRepo()

    @Test
    fun `Test that Starboy is still part of Mocked albums list`() = runBlocking {
        val albumsResult = sut.getAlbums()

        val starboyAlbum = albumsResult.find { it.name.contains("Starboy") }
        assertNotNull(starboyAlbum)
    }
}
package doji.music.data

import doji.music.domain.Album
import doji.music.domain.AlbumRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MockAlbumRepo: AlbumRepo {
    override suspend fun getAlbums(): List<Album>  = withContext(Dispatchers.IO) {
        listOf(
            Album("Starboy", "Daft Punk", "https://lastfm.freetls.fastly.net/i/u/174s/dfd01019404313399f77999285f78aa9.png"),
            Album("Girls Like You", "Maroon 5", "https://lastfm.freetls.fastly.net/i/u/300x300/257768e32141f6ecbc6d863e06d0ee93.png"),
            Album("Senorit", "Camila & Shawn", "https://lastfm.freetls.fastly.net/i/u/174s/2f251cfb493fac20a0bb3c7ac49639d1.png"),
            Album("Love Me Like You Do", "Ellie Goulding", "https://lastfm.freetls.fastly.net/i/u/174s/8f3ccd356af2e71db995ed3023209589.png"),
            Album("Happier", "Marshmello", "https://lastfm.freetls.fastly.net/i/u/174s/c77659d27c0099a8e9ca07a169f806e9.png"),
            Album("Shape of You", "Ed Sheeran", "https://lastfm.freetls.fastly.net/i/u/174s/80daaf62c7fbbf6ddaa143030b684e12.png"),
            Album("Stadium Arcadium", "Red Hot Chili Peppers", "https://lastfm.freetls.fastly.net/i/u/174s/fb7d1a6c6e5240c48159d08b17ea022b.png"),
            Album("Thriller", "Michael Jackson", "https://lastfm.freetls.fastly.net/i/u/174s/e5f40ae3767cf5b6184776f97e52b8ca.png"),
            Album("Queen A Night at the Opera", "Queen", "https://lastfm.freetls.fastly.net/i/u/174s/d36a1f8075da4df6bb835b2f7eac1547.png"),
            Album("High Voltage", "AC/DC", "https://lastfm.freetls.fastly.net/i/u/174s/591095f2549b4d9bbde16f471fa76e83.png")
        )
    }
}
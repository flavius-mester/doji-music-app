package doji.music.presentation.player

import doji.music.domain.Album
import doji.music.domain.AlbumRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(val albumRepo: AlbumRepo) {

    fun state(): Flow<State> = state

    fun send(action: Action) = coroutineScope.launch {
        when (action) {
            is Action.FetchAlbum -> fetchAlbum(action)
        }
    }

    private suspend fun fetchAlbum(action: Action.FetchAlbum) {
        val album = albumRepo.getAlbum(action.albumName)
        state.value = State.AlbumLoaded(album)
    }

    sealed class State {
        object Empty : State()
        data class AlbumLoaded(val album: Album) : State()
    }

    sealed class Action {
        data class FetchAlbum(val albumName: String) : Action()
    }

    private val state: MutableStateFlow<State> = MutableStateFlow(
        State.Empty
    )
    private val coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
package doji.music.presentation.home

import doji.music.domain.Album
import doji.music.domain.AlbumRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel(val albumRepo: AlbumRepo) {

    fun state(): Flow<State> = state

    fun send(action: Action) = coroutineScope.launch {
        when (action) {
            is Action.Init -> fetchAlbums()
            is Action.ToggleLayout -> toggleAlbumsLayout(action)
        }
    }

    private fun fetchAlbums() {
        val albums = albumRepo.getAlbums()
        state.value =
            State.Albums(albums)
    }

    private fun toggleAlbumsLayout(action: Action.ToggleLayout) {
        (state.value as State.Albums).let {
            val newLayout = if(it.layout == Layout.Grid) Layout.List else Layout.Grid
            state.value = it.copy(layout = newLayout)
        }
    }

    sealed class State {
        object Empty : State()
        data class Albums(val albums: List<Album>, val layout: Layout = Layout.Grid) : State()
    }

    sealed class Action {
        object Init : Action()
        class ToggleLayout : Action()
    }

    sealed class Layout {
        object Grid : Layout()
        object List : Layout()
    }

    private val state: MutableStateFlow<State> = MutableStateFlow(
        State.Empty
    )
    private val coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import timber.log.Timber

class PokemonDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailUiState.DUMMY)
    val uiState = _uiState.asStateFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        Timber.d("updatePokemonDetail: $pokemonId")
        if (pokemonId == null) {
            _uiState.value = PokemonDetailUiState.DUMMY
            return
        }
        // TODO : pokemonID 에 해당하는 포켓몬 정보를 가져와서 _uiState 에 업데이트
        _uiState.value = PokemonDetailUiState.DUMMY
    }

    companion object {
        fun factory(): ViewModelProvider.Factory = BaseViewModelFactory { PokemonDetailViewModel() }
    }
}

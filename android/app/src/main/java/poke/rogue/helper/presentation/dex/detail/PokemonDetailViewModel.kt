package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.data.datasource.FakePokemonDetailDataSource
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.toUi

class PokemonDetailViewModel(private val pokemonDetailDataSource: FakePokemonDetailDataSource) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            pokemonDetailDataSource.pokemonDetail(1).toUi(),
        )
    val uiState = _uiState.asStateFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        _uiState.value = pokemonDetailDataSource.pokemonDetail(pokemonId).toUi()
    }

    companion object {
        fun factory(pokemonDetailDataSource: FakePokemonDetailDataSource): ViewModelProvider.Factory =
            BaseViewModelFactory { PokemonDetailViewModel(pokemonDetailDataSource) }
    }
}

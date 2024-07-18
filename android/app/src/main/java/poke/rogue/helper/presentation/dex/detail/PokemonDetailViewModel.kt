package poke.rogue.helper.presentation.dex.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.data.datasource.PokemonDetailDataSource
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.dex.model.toUi
import timber.log.Timber

class PokemonDetailViewModel(private val pokemonDetailDataSource: PokemonDetailDataSource) : ViewModel() {
    // TODO: 스켈레톤에 쓰일 pokemon Id 를 지정해주어야 하나?
    private val _uiState =
        MutableStateFlow(
            pokemonDetailDataSource.pokemonDetail(1).toUi(),
        )
    val uiState = _uiState.asStateFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        requireNotNull(pokemonId) { "Pokemon ID must not be null" }
        Timber.d("updatePokemonDetail: $pokemonId")
        _uiState.value = pokemonDetailDataSource.pokemonDetail(pokemonId).toUi()
    }

    companion object {
        fun factory(pokemonDetailDataSource: PokemonDetailDataSource): ViewModelProvider.Factory =
            BaseViewModelFactory { PokemonDetailViewModel(pokemonDetailDataSource) }
    }
}

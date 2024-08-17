package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.data.model.NewPokemonDetail
import poke.rogue.helper.data.model.NewPokemonSkills
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.presentation.dex.model.NewAbilityUiModel
import poke.rogue.helper.presentation.dex.model.NewPokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.dex.model.toPokemonDetailUi
import poke.rogue.helper.presentation.dex.model.toUi

sealed interface PokemonDetailUiState {
    data class Success(
        val pokemon: NewPokemonUiModel,
        val stats: List<StatUiModel>,
        val abilities: List<NewAbilityUiModel>,
        val skills: NewPokemonSkills,
        val height: Float,
        val weight: Float,
    ) : PokemonDetailUiState

    data object IsLoading : PokemonDetailUiState
}

fun NewPokemonDetail.toUi(): PokemonDetailUiState.Success =
    PokemonDetailUiState.Success(
        pokemon = pokemon.toUi(),
        stats = stats.map(Stat::toUi),
        abilities = abilities.toPokemonDetailUi(),
        skills = skills,
        height = height.toFloat(),
        weight = weight.toFloat(),
    )

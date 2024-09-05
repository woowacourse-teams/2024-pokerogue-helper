package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.data.model.PokemonBiome
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.dex.model.PokemonBiomeUiModel
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.dex.model.toPokemonDetailUi
import poke.rogue.helper.presentation.dex.model.toUi

sealed interface PokemonDetailUiState {
    data class Success(
        val pokemon: PokemonUiModel,
        val stats: List<StatUiModel>,
        val abilities: List<PokemonDetailAbilityUiModel>,
        val evolutions: EvolutionsUiModel,
        val skills: PokemonDetailSkills,
        val height: Float,
        val weight: Float,
        val biomes: List<PokemonBiome>,
    ) : PokemonDetailUiState

    data object IsLoading : PokemonDetailUiState
}

sealed interface PokemonDetailUiState2 {
    data class Success(
        val pokemon: PokemonUiModel,
        val stats: List<StatUiModel>,
        val abilities: List<PokemonDetailAbilityUiModel>,
        val evolutions: EvolutionsUiModel,
        val skills: PokemonDetailSkills,
        val height: Float,
        val weight: Float,
        val biomes: List<PokemonBiomeUiModel>,
    ) : PokemonDetailUiState2

    data object IsLoading : PokemonDetailUiState2
}


fun PokemonDetail.toUi(): PokemonDetailUiState.Success =
    PokemonDetailUiState.Success(
        pokemon = pokemon.toUi(),
        stats = stats.map(Stat::toUi),
        abilities = abilities.toPokemonDetailUi(),
        evolutions = evolutions.toUi(),
        skills = skills,
        height = height.toFloat(),
        weight = weight.toFloat(),
        biomes = biomes,
    )

fun PokemonDetail.toUi(allBiomes: List<Biome>): PokemonDetailUiState2.Success = PokemonDetailUiState2.Success(
    pokemon = pokemon.toUi(),
    stats = stats.map(Stat::toUi),
    abilities = abilities.toPokemonDetailUi(),
    evolutions = evolutions.toUi(),
    skills = skills,
    height = height.toFloat(),
    weight = weight.toFloat(),
    biomes = biomes.toUi(allBiomes),
)

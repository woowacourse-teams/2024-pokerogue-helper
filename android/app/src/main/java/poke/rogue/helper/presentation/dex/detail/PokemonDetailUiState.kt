package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.presentation.type.model.TypeUiModel

data class PokemonDetailUiState(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val height: Float,
    val weight: Float,
    val types: List<TypeUiModel>,
    val statUiModels: List<Stat>,
)

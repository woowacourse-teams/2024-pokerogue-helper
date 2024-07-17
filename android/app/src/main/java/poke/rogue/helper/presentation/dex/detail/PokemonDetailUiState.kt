package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.presentation.type.model.TypeUiModel

data class PokemonDetailUiState(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val height: Float,
    val weight: Float,
    val types: List<TypeUiModel>,
    val stats: List<Stat>,
) {
    companion object {
        val DUMMY =
            PokemonDetailUiState(
                id = 6,
                name = "리자몽",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
                height = 1.7f,
                weight = 90.5f,
                types = listOf(TypeUiModel.FIRE, TypeUiModel.FLYING),
                stats =
                    listOf(
                        Stat("HP", 168, 300),
                        Stat("ATK", 205, 300),
                        Stat("DEF", 64, 300),
                        Stat("SPD", 204, 300),
                    ),
            )
    }
}

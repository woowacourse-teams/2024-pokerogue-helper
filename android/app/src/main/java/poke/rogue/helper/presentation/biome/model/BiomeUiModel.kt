package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.presentation.type.model.TypeUiModel

data class BiomeUiModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
) {
    companion object {
        val DUMMYS: List<BiomeUiModel> =
            listOf(
                BiomeUiModel(
                    1,
                    "풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                ),
                BiomeUiModel(
                    2,
                    "높은 풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_tall_grass_bg.png?w=200&tok=b3497c",
                    types = listOf(TypeUiModel.BUG, TypeUiModel.NORMAL),
                ),
                BiomeUiModel(
                    3,
                    "동굴",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_cave_bg.png?w=200&tok=905d8b",
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.WATER),
                ),
                BiomeUiModel(
                    4,
                    "악지",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_badlands_bg.png?w=200&tok=37d070",
                    types = listOf(TypeUiModel.DARK, TypeUiModel.FIGHTING),
                ),
            )
    }
}

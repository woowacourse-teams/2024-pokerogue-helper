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
                    types = listOf(TypeUiModel.BUG),
                ),
                BiomeUiModel(
                    3,
                    "동굴",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_cave_bg.png?w=200&tok=905d8b",
                    types = listOf(TypeUiModel.GRASS),
                ),
                BiomeUiModel(
                    4,
                    "악지",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_badlands_bg.png?w=200&tok=37d070",
                    types = listOf(TypeUiModel.DARK, TypeUiModel.FIGHTING),
                ),
                BiomeUiModel(
                    5,
                    "공장",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_factory_bg.png?w=200&tok=5c1cb5",
                    types = listOf(TypeUiModel.DARK, TypeUiModel.FIGHTING),
                ),
                BiomeUiModel(
                    6,
                    "공사장",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_construction_site_bg.png?w=200&tok=8cf671",
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.GROUND),
                ),
                BiomeUiModel(
                    7,
                    "눈덮힌 숲",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_snowy_forest_bg.png?w=200&tok=2fe712",
                    types = listOf(TypeUiModel.ICE, TypeUiModel.STEEL),
                ),
                BiomeUiModel(
                    8,
                    "얼음동굴",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_ice_cave_bg.png?w=200&tok=aa8cf1",
                    types = listOf(TypeUiModel.ICE, TypeUiModel.WATER),
                ),
            )
    }
}

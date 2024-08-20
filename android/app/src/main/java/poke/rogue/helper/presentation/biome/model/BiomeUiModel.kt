package poke.rogue.helper.presentation.biome.model

import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.presentation.type.model.TypeUiModel
import java.util.Locale

data class BiomeUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val types: List<TypeUiModel>,
) {
    companion object {
        val DUMMYS: List<BiomeUiModel> =
            listOf(
                BiomeUiModel(
                    "grass",
                    "풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_grassy_fields_bg.png?w=200&tok=745c5b",
                    types = listOf(TypeUiModel.GRASS, TypeUiModel.POISON),
                ),
                BiomeUiModel(
                    "tall_grass",
                    "높은 풀숲",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_tall_grass_bg.png?w=200&tok=b3497c",
                    types = listOf(TypeUiModel.BUG),
                ),
                BiomeUiModel(
                    "cave",
                    "동굴",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_cave_bg.png?w=200&tok=905d8b",
                    types = listOf(TypeUiModel.GRASS),
                ),
                BiomeUiModel(
                    "badlands",
                    "악지",
                    "https://wiki.pokerogue.net/_media/ko:biomes:ko_badlands_bg.png?w=200&tok=37d070",
                    types = listOf(TypeUiModel.DARK, TypeUiModel.FIGHTING),
                ),
                BiomeUiModel(
                    "factory",
                    "공장",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_factory_bg.png?w=200&tok=5c1cb5",
                    types = listOf(TypeUiModel.DARK, TypeUiModel.FIGHTING),
                ),
                BiomeUiModel(
                    "construction_site",
                    "공사장",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_construction_site_bg.png?w=200&tok=8cf671",
                    types = listOf(TypeUiModel.NORMAL, TypeUiModel.GROUND),
                ),
                BiomeUiModel(
                    "snowy_forest",
                    "눈덮힌 숲",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_snowy_forest_bg.png?w=200&tok=2fe712",
                    types = listOf(TypeUiModel.ICE, TypeUiModel.STEEL),
                ),
                BiomeUiModel(
                    "ice_cave",
                    "얼음동굴",
                    "https://wiki.pokerogue.net/_media/en:biomes:en_ice_cave_bg.png?w=200&tok=aa8cf1",
                    types = listOf(TypeUiModel.ICE, TypeUiModel.WATER),
                ),
            )
    }
}

fun List<String>.toTypeUi(): List<TypeUiModel> {
    return this.map { url ->
        val typeName = url.substringAfter("type/").substringBefore("-")
        TypeUiModel.valueOf(typeName.uppercase(Locale.ROOT))
    }
}

fun Biome.toUi(): BiomeUiModel =
    BiomeUiModel(
        id = id,
        name = name,
        imageUrl = image,
        types = (pokemonType.toTypeUi() + gymLeaderType.toTypeUi()).distinct(),
    )

fun List<Biome>.toUi(): List<BiomeUiModel> = map(Biome::toUi)

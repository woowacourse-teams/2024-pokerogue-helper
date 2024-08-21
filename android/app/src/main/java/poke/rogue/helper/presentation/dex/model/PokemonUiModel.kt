package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

data class PokemonUiModel(
    val id: String = "",
    val hashId: Long = 0,
    val dexNumber: Long = 0,
    val name: String,
    val formName: String = "",
    val imageUrl: String,
    val types: List<TypeUiModel>,
    val baseStats: Int = 0,
    val speed: Int = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    private val sortUiModel: PokemonSortUiModel = PokemonSortUiModel.ByDexNumber,
) {
    val displayStat: Int
        get() =
            when (sortUiModel) {
                PokemonSortUiModel.ByBaseStat -> baseStats
                PokemonSortUiModel.BySpeed -> speed
                PokemonSortUiModel.ByHp -> hp
                PokemonSortUiModel.ByAttack -> attack
                PokemonSortUiModel.ByDefense -> defense
                PokemonSortUiModel.BySpecialAttack -> specialAttack
                PokemonSortUiModel.BySpecialDefense -> specialDefense
                else -> 0
            }
}

fun Pokemon.toUi(): PokemonUiModel =
    PokemonUiModel(
        id = id,
        dexNumber = dexNumber,
        name = name,
        formName = formName,
        imageUrl = imageUrl,
        types = types.toUi(),
        baseStats = baseStat,
        speed = speed,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
    )

fun List<Pokemon>.toUi(): List<PokemonUiModel> = mapIndexed { index, pokemon ->
    pokemon.toUi().copy(hashId = index.toLong())
}

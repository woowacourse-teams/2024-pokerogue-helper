package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel1
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
    private val sortUiModel: PokemonSortUiModel1 = PokemonSortUiModel1.ByDexNumber,
) {
    val displayStat: Int
        get() =
            when (sortUiModel) {
                PokemonSortUiModel1.ByBaseStat -> baseStats
                PokemonSortUiModel1.BySpeed -> speed
                PokemonSortUiModel1.ByHp -> hp
                PokemonSortUiModel1.ByAttack -> attack
                PokemonSortUiModel1.ByDefense -> defense
                PokemonSortUiModel1.BySpecialAttack -> specialAttack
                PokemonSortUiModel1.BySpecialDefense -> specialDefense
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

fun List<Pokemon>.toUi(): List<PokemonUiModel> =
    mapIndexed { index, pokemon ->
        pokemon.toUi().copy(hashId = index.toLong())
    }

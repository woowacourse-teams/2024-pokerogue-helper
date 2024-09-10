package poke.rogue.helper.presentation.battle.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.model.toUi

@Parcelize
data class PokemonSelectionUiModel(
    val id: String,
    val dexNumber: Long,
    val name: String,
    val frontImageUrl: String,
    val backImageUrl: String,
    val types: List<TypeUiModel>,
) : Parcelable {
    companion object {
        val DUMMY =
            listOf(
                PokemonSelectionUiModel(
                    id = "bulbasaur",
                    dexNumber = 1,
                    name = "이상해씨",
                    frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    backImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
                    types =
                        listOf(
                            TypeUiModel.GRASS,
                            TypeUiModel.POISON,
                        ),
                ),
                PokemonSelectionUiModel(
                    id = "charmander",
                    dexNumber = 4,
                    name = "파이리",
                    frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    backImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/4.png",
                    types = listOf(TypeUiModel.FIRE),
                ),
                PokemonSelectionUiModel(
                    id = "squirtle",
                    dexNumber = 7,
                    name = "꼬북이",
                    frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                    backImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/7.png",
                    types = listOf(TypeUiModel.WATER),
                ),
                PokemonSelectionUiModel(
                    id = "pikachu",
                    dexNumber = 25,
                    name = "피카츄",
                    frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                    backImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/25.png",
                    types = listOf(TypeUiModel.ELECTRIC),
                ),
                PokemonSelectionUiModel(
                    id = "Charizard",
                    dexNumber = 6,
                    name = "리자몽",
                    frontImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                    backImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/6.png",
                    types = listOf(TypeUiModel.FIRE, TypeUiModel.FLYING),
                ),
            )
    }
}

fun Pokemon.toSelectionUi(): PokemonSelectionUiModel =
    PokemonSelectionUiModel(
        id = id,
        dexNumber = dexNumber,
        name = name,
        frontImageUrl = imageUrl,
        backImageUrl = backImageUrl,
        types = types.map { it.toUi() },
    )

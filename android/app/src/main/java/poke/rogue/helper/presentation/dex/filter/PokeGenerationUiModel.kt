package poke.rogue.helper.presentation.dex.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import poke.rogue.helper.data.model.PokemonGeneration

@Parcelize
enum class PokeGenerationUiModel(val number: Int) : Parcelable {
    ALL(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);
}

fun PokeGenerationUiModel.toDataOrNull(): PokemonGeneration? {
    if (this == PokeGenerationUiModel.ALL) {
        return null
    }
    return PokemonGeneration.of(number)
}
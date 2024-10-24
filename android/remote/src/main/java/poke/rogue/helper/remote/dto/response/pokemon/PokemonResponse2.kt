package poke.rogue.helper.remote.dto.response.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

@Serializable
data class PokemonResponse2(
    val id: String,
    val pokedexNumber: Long,
    val formName: String,
    val name: String,
    val image: String,
    val backImage: String,
    @SerialName("pokemonTypeResponse")
    val types: List<PokemonTypeResponse>,
    val generation: Int,
    @SerialName("totalStats")
    val baseStats: Int,
    val speed: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
)

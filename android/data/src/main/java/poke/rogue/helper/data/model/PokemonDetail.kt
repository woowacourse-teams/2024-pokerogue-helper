package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class PokemonDetail(
    val pokemon: Pokemon,
    val stats: List<Stat>,
    val abilities: List<Ability>,
    val skills: List<PokemonSkill> = PokemonSkill.FAKE_SKILLS,
    val height: Float,
    val weight: Float,
)

fun PokemonDetailResponse.toData(id: Long): PokemonDetail =
    PokemonDetail(
        Pokemon(
            id = id,
            dexNumber = dexNumber,
            name = name,
            imageUrl = imageUrl,
            types = types.map(PokemonTypeResponse::toData),
        ),
        stats =
            listOf(
                Stat("hp", hp),
                Stat("attack", attack),
                Stat("defense", defense),
                Stat("specialAttack", specialAttack),
                Stat("specialDefense", specialDefense),
                Stat("speed", speed),
                Stat("total", totalStats),
            ),
        abilities = abilities.map(AbilityResponse::toData),
        height = height,
        weight = weight,
    )

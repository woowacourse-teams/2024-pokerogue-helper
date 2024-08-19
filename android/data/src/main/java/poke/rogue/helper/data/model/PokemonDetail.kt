package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class PokemonDetail(
    val pokemon: Pokemon,
    val abilities: List<PokemonDetailAbility>,
    val stats: List<Stat>,
    val pokemonCategory: PokemonCategory,
    val evolutions: List<Evolution>,
    val skills: PokemonDetailSkills,
    val biomes: List<PokemonBiome>,
    val height: Double,
    val weight: Double,
)

// TODO: 서버에서 String 으로 주면 id: Long -> id.String() 으로 변경
fun PokemonDetailResponse.toData(id: Long): PokemonDetail =
    PokemonDetail(
        pokemon =
            Pokemon(
                id = id.toString(),
                dexNumber = dexNumber,
                name = name,
                imageUrl = imageUrl,
                types = types.map(PokemonTypeResponse::toData),
            ),
        abilities = abilities.map(AbilityResponse::toNewData),
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
        pokemonCategory = PokemonCategory.EMPTY,
        evolutions = emptyList(),
        skills =
            PokemonDetailSkills(
                selfLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                tmLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                eggLearn = PokemonSkill.FAKE_EGG_LEARN_SKILLS,
            ),
        biomes = PokemonBiome.DUMMYS,
        height = height.toDouble(),
        weight = weight.toDouble(),
    )

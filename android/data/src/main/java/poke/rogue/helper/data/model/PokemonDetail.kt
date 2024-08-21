package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.biom.PokemonBiomeResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse2
import poke.rogue.helper.remote.dto.response.pokemon.PokemonSkillResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class PokemonDetail(
    val pokemon: Pokemon,
    val abilities: List<PokemonDetailAbility>,
    val stats: List<Stat>,
    val pokemonCategory: PokemonCategory,
    val evolutions: List<Evolution>,
    val skills: PokemonDetailSkills2,
    val biomes: List<PokemonBiome>,
    val height: Double,
    val weight: Double,
)

fun PokemonDetailResponse2.toData(id: String): PokemonDetail =
    PokemonDetail(
        pokemon =
            Pokemon(
                id = id,
                dexNumber = dexNumber,
                name = name,
                imageUrl = imageUrl,
                types = types.map(PokemonTypeResponse::toData),
            ),
        abilities = abilities.toData(),
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
        evolutions = evolutions.toData(),
        skills =
            PokemonDetailSkills2(
                selfLearn = selfLearnSkills.map(PokemonSkillResponse::toData),
                tmLearn = selfLearnSkills.map(PokemonSkillResponse::toData),
                eggLearn = eggSkills.map(PokemonSkillResponse::toData),
            ),
        biomes = biomes.map(PokemonBiomeResponse::toData),
        height = height.toDouble(),
        weight = weight.toDouble(),
    )

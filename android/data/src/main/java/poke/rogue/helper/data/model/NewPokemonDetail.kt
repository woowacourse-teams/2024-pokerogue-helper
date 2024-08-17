package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.pokemon.PokemonDetailResponse
import poke.rogue.helper.remote.dto.response.type.PokemonTypeResponse

data class NewPokemonDetail(
    val pokemon: Pokemon,
    val abilities: List<NewAbility>,
    val stats: List<Stat>,
    val pokemonCategory: PokemonCategory,
    val evolutions: List<Evolution>,
    val skills: NewPokemonSkills,
    val biomes: List<Biome>,
    val height: Double,
    val weight: Double,
)

fun PokemonDetailResponse.toNewData(id: Long): NewPokemonDetail =
    NewPokemonDetail(
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
            NewPokemonSkills(
                selfLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                tmLearn = PokemonSkill.FAKE_SELF_LEARN_SKILLS,
                eggLearn = PokemonSkill.FAKE_EGG_LEARN_SKILLS,
            ),
        biomes = Biome.DUMMYS,
        height = height.toDouble(),
        weight = weight.toDouble(),
    )

package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.ability.AbilityResponse
import poke.rogue.helper.remote.dto.response.ability.AbilityResponse2

data class PokemonDetailAbility(
    val id: String,
    val name: String,
    val description: String,
    val passive: Boolean,
    val hidden: Boolean,
) {
    companion object {
        val DUMMY_POKEMON_DETAIL_ABILTIES =
            listOf(
                PokemonDetailAbility(
                    id = "10",
                    name = "그래스메이커",
                    description = "등장했을 때 그래스필드를 사용한다.",
                    passive = true,
                    hidden = false,
                ),
                PokemonDetailAbility(
                    id = "450",
                    name = "심록",
                    description = "HP가 줄었을 때 풀타입 기술의 위력이 올라간다.",
                    passive = false,
                    hidden = false,
                ),
                PokemonDetailAbility(
                    id = "419",
                    name = "엽록소",
                    description = "날씨가 맑을 때 스피드가 올라간다.",
                    passive = false,
                    hidden = true,
                ),
            )
    }
}

fun AbilityResponse.toNewData(): PokemonDetailAbility =
    PokemonDetailAbility(
        id = id.toString(),
        name = title,
        description = description,
        passive = false,
        hidden = false,
    )

fun AbilityResponse2.toData(): PokemonDetailAbility =
    PokemonDetailAbility(
        id = id,
        name = name,
        description = description,
        passive = passive,
        hidden = hidden,
    )

fun List<AbilityResponse2>.toData(): List<PokemonDetailAbility> = map(AbilityResponse2::toData)

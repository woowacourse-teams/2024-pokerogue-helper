package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.AbilityDetail
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.stringmatcher.has

class FakeAbilityRepository : AbilityRepository {
    override suspend fun abilities(): List<Ability> = ABILITES

    override suspend fun abilities(query: String): List<Ability> =
        ABILITES.filter { ability ->
            ability.title.has(query)
        }

    override suspend fun abilityDetail(id: String): AbilityDetail =
        ABILITY_DETAILS[id] ?: throw IllegalArgumentException("Invalid Ability Id")

    companion object {
        private const val FORMAT_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
                "/official-artwork/"

        private const val POSTFIX_PNG = ".png"

        private fun pokemonImageUrl(pokemonId: Long) = FORMAT_POKEMON_IMAGE_URL + pokemonId + POSTFIX_PNG

        val ABILITES: List<Ability> =
            listOf(
                Ability("1", "악취", "악취를 풍겨서 공격했을 때 상대가 풀죽을 때가 있다."),
                Ability("2", "잔비", "등장했을 때 날씨를 비로 만든다."),
                Ability("3", "가속", "매 턴 스피드가 올라간다."),
                Ability("4", "전투무장", "단단한 껍질에 보호받아 상대의 공격이 급소에 맞지 않는다."),
                Ability("5", "옹골참", "상대 기술을 받아도 일격으로 쓰러지지 않는다. 일격필살 기술도 효과 없다."),
                Ability("6", "유연", "주변을 습하게 함으로써 자폭 등 폭발하는 기술을 아무도 못 쓰게 한다."),
                Ability("7", "모래숨기", "모래바람일 때 회피율이 올라간다."),
                Ability("8", "정전기", "정전기를 몸에 둘러 접촉한 상대를 마비시킬 때가 있다."),
                Ability("9", "축전 (P)", "전기타입의 기술을 받으면 데미지를 받지 않고 회복한다."),
                Ability("10", "저수 (P)", "물타입의 기술을 받으면 데미지를 받지 않고 회복한다."),
                Ability("11", "둔감", "둔감해서 헤롱헤롱이나 도발 상태가 되지 않는다."),
                Ability("12", "날씨부정", "모든 날씨의 영향이 없어진다."),
                Ability("13", "복안", "복안을 가지고 있어 기술의 명중률이 올라간다."),
                Ability("14", "불면", "잠들지 못하는 체질이라 잠듦 상태가 되지 않는다."),
                Ability("15", "변색", "상대에게 받은 기술의 타입으로 자신의 타입이 변화한다."),
                Ability("16", "면역", "체내에 면역을 가지고 있어 독 상태가 되지 않는다."),
                Ability("17", "타오르는불꽃", "불꽃타입의 기술을 받으면 불꽃을 받아서 자신이 사용하는 불꽃타입의 기술이 강해진다."),
                Ability("18", "인분 (P)", "인분에 보호받아 기술의 추가 효과를 받지 않게 된다."),
                Ability("19", "마이페이스", "마이페이스라서 혼란 상태가 되지 않는다."),
                Ability("20", "홉반", "흡반으로 지면에 달라붙어 포켓몬을 교체시키는 기술이나 도구의 효과를 발휘하지 못하게 한다."),
                Ability("21", "위협", "등장했을 때 위협해서 상대를 위축시켜 상대의 공격을 떨어뜨린다."),
                Ability("22", "그림자밝기", "상대의 그림자를 밟아 도망치거나 교체할 수 없게 한다."),
                Ability("23", "까칠한피부", "공격을 받았을 때 자신에게 접촉한 상대를 까칠까칠한 피부로 상처를 입힌다."),
                Ability("24", "불가사의부적", "효과가 굉장한 기술만 맞는 불가사의한 힘."),
            )

        private val ABILITY_DETAILS: Map<String, AbilityDetail> =
            mapOf(
                "1" to
                    AbilityDetail(
                        title = "악취",
                        description = "악취를 풍겨서 공격했을 때 상대가 풀죽을 때가 있다.",
                        pokemons =
                            listOf(
                                Pokemon(
                                    id = "1",
                                    dexNumber = 1,
                                    name = "이상해씨",
                                    imageUrl = pokemonImageUrl(pokemonId = 1),
                                    types = listOf(Type.GRASS, Type.POISON),
                                ),
                            ),
                    ),
                "2" to
                    AbilityDetail(
                        title = "잔비",
                        description = "등장했을 때 날씨를 비로 만든다.",
                        pokemons =
                            listOf(
                                Pokemon(
                                    id = "2",
                                    dexNumber = 2,
                                    name = "이상해풀",
                                    imageUrl = pokemonImageUrl(pokemonId = 2),
                                    types = listOf(Type.GRASS, Type.POISON),
                                ),
                            ),
                    ),
            )
    }
}

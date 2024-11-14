package poke.rogue.helper.data.model

import poke.rogue.helper.remote.dto.response.pokemon.PokemonSkillResponse

data class PokemonSkill(
    val id: String,
    val name: String,
    val level: Int,
    val power: Int,
    val type: Type,
    val accuracy: Int,
    val category: SkillCategory,
) {
    companion object {
        const val NO_POWER_VALUE = -1
        const val NO_ACCURACY_VALUE = -1

        val FAKE_EGG_LEARN_SKILLS =
            listOf(
                PokemonSkill(
                    id = "101",
                    name = "기가드레인",
                    level = 1,
                    power = 75,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.specialAttackSkill,
                ),
                PokemonSkill(
                    id = "102",
                    name = "오물폭탄",
                    level = 1,
                    power = 90,
                    type = Type.POISON,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "103",
                    name = "대지의힘",
                    level = 1,
                    power = 90,
                    type = Type.GROUND,
                    accuracy = 100,
                    category = SkillCategory.specialAttackSkill,
                ),
                PokemonSkill(
                    id = "104",
                    name = "쑥쑥봄버",
                    level = 1,
                    power = 100,
                    type = Type.GRASS,
                    accuracy = 90,
                    category = SkillCategory.physicalAttackSkill,
                ),
            )

        val FAKE_SELF_LEARN_SKILLS =
            listOf(
                PokemonSkill(
                    id = "1",
                    name = "몸통박치기",
                    level = 1,
                    power = 40,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "2",
                    name = "울음소리",
                    level = 1,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "3",
                    name = "덩굴채찍",
                    level = 1,
                    power = 45,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "4",
                    name = "성장",
                    level = 6,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "5",
                    name = "씨뿌리기",
                    level = 9,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 90,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "6",
                    name = "앞날가르기",
                    level = 12,
                    power = 55,
                    type = Type.GRASS,
                    accuracy = 95,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "7",
                    name = "독가루",
                    level = 15,
                    power = NO_POWER_VALUE,
                    type = Type.POISON,
                    accuracy = 75,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "8",
                    name = "수면가루",
                    level = 15,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 75,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "9",
                    name = "씨폭탄",
                    level = 18,
                    power = 80,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "10",
                    name = "돌진",
                    level = 21,
                    power = 90,
                    type = Type.NORMAL,
                    accuracy = 85,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "11",
                    name = "달콤한향기",
                    level = 24,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "12",
                    name = "광합성",
                    level = 27,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "13",
                    name = "고민씨",
                    level = 30,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "14",
                    name = "파워휩",
                    level = 33,
                    power = 120,
                    type = Type.GRASS,
                    accuracy = 85,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "15",
                    name = "솔라빔",
                    level = 36,
                    power = 120,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.specialAttackSkill,
                ),
            )

        val FAKE_TM_LEARN_SKILLS =
            listOf(
                PokemonSkill(
                    id = "1",
                    name = "몸통박치기",
                    level = 1,
                    power = 40,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "2",
                    name = "울음소리",
                    level = 1,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "3",
                    name = "덩굴채찍",
                    level = 1,
                    power = 45,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "4",
                    name = "성장",
                    level = 6,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "5",
                    name = "씨뿌리기",
                    level = 9,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 90,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "6",
                    name = "앞날가르기",
                    level = 12,
                    power = 55,
                    type = Type.GRASS,
                    accuracy = 95,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "7",
                    name = "독가루",
                    level = 15,
                    power = NO_POWER_VALUE,
                    type = Type.POISON,
                    accuracy = 75,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "8",
                    name = "수면가루",
                    level = 15,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 75,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "9",
                    name = "씨폭탄",
                    level = 18,
                    power = 80,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "10",
                    name = "돌진",
                    level = 21,
                    power = 90,
                    type = Type.NORMAL,
                    accuracy = 85,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "11",
                    name = "달콤한향기",
                    level = 24,
                    power = NO_POWER_VALUE,
                    type = Type.NORMAL,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "12",
                    name = "광합성",
                    level = 27,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "13",
                    name = "고민씨",
                    level = 30,
                    power = NO_POWER_VALUE,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.changeStatusSkill,
                ),
                PokemonSkill(
                    id = "14",
                    name = "파워휩",
                    level = 33,
                    power = 120,
                    type = Type.GRASS,
                    accuracy = 85,
                    category = SkillCategory.physicalAttackSkill,
                ),
                PokemonSkill(
                    id = "15",
                    name = "솔라빔",
                    level = 36,
                    power = 120,
                    type = Type.GRASS,
                    accuracy = 100,
                    category = SkillCategory.specialAttackSkill,
                ),
            )
    }
}

fun PokemonSkillResponse.toData(): PokemonSkill =
    PokemonSkill(
        id = id,
        name = name,
        level = level,
        power = power,
        type = Type.of(type),
        accuracy = accuracy,
        category =
            SkillCategory(
                category,
                categoryLogo,
            ),
    )

fun List<PokemonSkillResponse>.toData(): List<PokemonSkill> = map(PokemonSkillResponse::toData)

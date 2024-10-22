package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.TypeRepository

class FakeTypeRepository : TypeRepository {
    override fun matchedTypesAgainstMyType(myTypeId: Int): List<MatchedTypes> =
        DUMMY_RESULTS_AGAINST_MINE[Type.fromId(myTypeId)]
            ?: throw IllegalArgumentException("Invalid ID : $myTypeId")

    override fun matchedTypesAgainstOpponent(opponentTypeId: Int): List<MatchedTypes> =
        DUMMY_RESULTS_AGAINST_OPPONENT[Type.fromId(opponentTypeId)]
            ?: throw IllegalArgumentException("Invalid ID : $opponentTypeId")

    override fun matchedTypes(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<MatchedTypes> {
        val myType = Type.fromId(myTypeId)
        val result =
            opponentTypeIds.groupBy { opponentTypeId ->
                val opponentType = Type.fromId(opponentTypeId)
                DUMMY_RESULTS[myType]?.get(
                    opponentType,
                ) ?: throw IllegalArgumentException("No result for myType: $myType and opponentType: $opponentType")
            }
        return result.entries.map { (matchedResult, types) ->
            MatchedTypes(matchedResult, types.map { Type.fromId(it) })
        }
    }

    override fun allTypes(): List<Type> = listOf()

    companion object {
        val DUMMY_RESULTS_AGAINST_MINE =
            mapOf(
                Type.FAIRY to
                    listOf(
                        MatchedTypes(MatchedResult.STRONG, listOf(Type.ICE, Type.DRAGON)),
                        MatchedTypes(MatchedResult.WEAK, listOf(Type.FIRE, Type.POISON)),
                    ),
            )

        val DUMMY_RESULTS_AGAINST_OPPONENT =
            mapOf(
                Type.FAIRY to
                    listOf(
                        MatchedTypes(MatchedResult.NORMAL, listOf(Type.WATER, Type.GRASS)),
                        MatchedTypes(MatchedResult.STRONG, listOf(Type.POISON, Type.STEEL)),
                    ),
            )

        val DUMMY_RESULTS =
            mapOf(
                Type.FAIRY to
                    mapOf(
                        Type.NORMAL to MatchedResult.NORMAL,
                        Type.FIRE to MatchedResult.WEAK,
                        Type.WATER to MatchedResult.NORMAL,
                        Type.ELECTRIC to MatchedResult.NORMAL,
                        Type.GRASS to MatchedResult.NORMAL,
                        Type.ICE to MatchedResult.NORMAL,
                        Type.FIGHTING to MatchedResult.STRONG,
                        Type.POISON to MatchedResult.WEAK,
                        Type.GROUND to MatchedResult.NORMAL,
                        Type.FLYING to MatchedResult.NORMAL,
                        Type.PSYCHIC to MatchedResult.NORMAL,
                        Type.BUG to MatchedResult.NORMAL,
                        Type.ROCK to MatchedResult.NORMAL,
                        Type.GHOST to MatchedResult.NORMAL,
                        Type.DRAGON to MatchedResult.STRONG,
                        Type.DARK to MatchedResult.STRONG,
                        Type.STEEL to MatchedResult.WEAK,
                        Type.FAIRY to MatchedResult.NORMAL,
                        Type.STELLAR to MatchedResult.NORMAL,
                    ),
            )
    }
}

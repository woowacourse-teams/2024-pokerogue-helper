package poke.rogue.helper.testing.data.repository

import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.TypeRepository

class FakeTypeRepository : TypeRepository {
    override fun matchedTypesAgainstMyType(myTypeId: Int): List<MatchedTypes> = DUMMY_RESULTS_AGAINST_MINE

    override fun matchedTypesAgainstOpponent(opponentTypeId: Int): List<MatchedTypes> = DUMMY_RESULTS_AGAINST_OPPONENT

    override fun matchedTypes(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<MatchedTypes> = DUMMY_RESULTS

    override fun allTypes(): List<Type> = listOf()

    companion object {
        val DUMMY_RESULTS_AGAINST_MINE =
            listOf(
                MatchedTypes(MatchedResult.STRONG, listOf(Type.FIRE, Type.DRAGON, Type.FLYING)),
                MatchedTypes(MatchedResult.WEAK, listOf(Type.GHOST, Type.GRASS, Type.BUG)),
            )

        val DUMMY_RESULTS_AGAINST_OPPONENT =
            listOf(
                MatchedTypes(MatchedResult.NORMAL, listOf(Type.FIRE, Type.DRAGON, Type.FLYING)),
                MatchedTypes(MatchedResult.STRONG, listOf(Type.GHOST, Type.FIRE, Type.WATER)),
            )

        val DUMMY_RESULTS =
            listOf(MatchedTypes(MatchedResult.NORMAL, listOf(Type.FIRE, Type.DRAGON)))
    }
}

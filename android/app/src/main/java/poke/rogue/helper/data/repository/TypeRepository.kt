package poke.rogue.helper.data.repository

import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.TypeInfo

interface TypeRepository {
    fun matchedTypesAgainstMyType(myTypeId: Int): List<MatchedTypes>

    fun matchedTypesAgainstOpponent(opponentTypeId: Int): List<MatchedTypes>

    fun matchedTypes(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<MatchedTypes>

    fun allTypes(): List<TypeInfo>
}

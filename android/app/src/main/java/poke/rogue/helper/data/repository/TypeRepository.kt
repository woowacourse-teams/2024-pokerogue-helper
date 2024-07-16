package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.TypeMatchedResult

class TypeRepository(private val localTypeDataSource: LocalTypeDataSource) {
    fun findResultAgainstMyType(myTypeId: Int): List<TypeMatchedResult> {
        return localTypeDataSource.findAllTypesAgainstAttackingType(myTypeId)
            .filter { it.matchedResult != MatchedResult.NORMAL }
    }

    fun findResultAgainstOpponent(opponentTypeId: Int): List<TypeMatchedResult> {
        return localTypeDataSource.findAllTypesAgainstDefendingType(opponentTypeId)
            .filter { it.matchedResult != MatchedResult.NORMAL }
    }

    fun findMatchedResult(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<TypeMatchedResult> {
        return opponentTypeIds.map {
            localTypeDataSource.findMatchedTypeResult(myTypeId, it)
        }.filter { it.matchedResult != MatchedResult.NORMAL }
    }
}

package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.TypeInfo

class DefaultTypeRepository(private val localTypeDataSource: LocalTypeDataSource) : TypeRepository {
    override fun matchedTypesAgainstMyType(myTypeId: Int): List<MatchedTypes> {
        return localTypeDataSource.matchedTypesAgainstAttackingType(myTypeId)
            .filter { it.matchedResult != MatchedResult.NORMAL }
    }

    override fun matchedTypesAgainstOpponent(opponentTypeId: Int): List<MatchedTypes> {
        return localTypeDataSource.matchedTypesAgainstDefendingType(opponentTypeId)
            .filter { it.matchedResult != MatchedResult.NORMAL }
    }

    override fun matchedTypes(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<MatchedTypes> {
        return opponentTypeIds.map {
            localTypeDataSource.matchedTypes(myTypeId, it)
        }.filter { it.matchedResult != MatchedResult.NORMAL }
    }

    override fun allTypes(): List<TypeInfo> {
        return localTypeDataSource.allTypes()
    }
}

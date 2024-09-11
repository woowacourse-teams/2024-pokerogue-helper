package poke.rogue.helper.data.repository

import poke.rogue.helper.data.datasource.LocalTypeDataSource
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.Type

class DefaultTypeRepository(private val localTypeDataSource: LocalTypeDataSource) : TypeRepository {
    override fun matchedTypesAgainstMyType(myTypeId: Int): List<MatchedTypes> {
        return localTypeDataSource.matchedTypesAgainstAttackingType(myTypeId)
    }

    override fun matchedTypesAgainstOpponent(opponentTypeId: Int): List<MatchedTypes> {
        return localTypeDataSource.matchedTypesAgainstDefendingType(opponentTypeId)
    }

    override fun matchedTypes(
        myTypeId: Int,
        opponentTypeIds: List<Int>,
    ): List<MatchedTypes> {
        return localTypeDataSource.matchedTypes(
            myTypeId,
            opponentTypeIds,
        )
    }

    override fun allTypes(): List<Type> {
        return localTypeDataSource.allTypes()
    }
}

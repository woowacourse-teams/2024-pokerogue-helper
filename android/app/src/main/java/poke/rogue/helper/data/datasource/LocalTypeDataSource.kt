package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.TypeInfo
import poke.rogue.helper.local.DummyTypeData

class LocalTypeDataSource(private val typeData: DummyTypeData = DummyTypeData) {
    fun matchedTypesAgainstAttackingType(attackingTypeId: Int): List<MatchedTypes> {
        val allResult = typeData.typeMatchedTable[attackingTypeId]
        val resultMap = allResult.withIndex().groupBy({ it.value }, { typeData.allTypes[it.index] })
        val result =
            resultMap.entries.map {
                    (matchedResult, types) ->
                MatchedTypes(matchedResult, types)
            }
        return result
    }

    fun matchedTypesAgainstDefendingType(defendingTypeId: Int): List<MatchedTypes> {
        val allResult = typeData.typeMatchedTable.map { it[defendingTypeId] }
        val resultMap = allResult.withIndex().groupBy({ it.value }, { typeData.allTypes[it.index] })
        val result =
            resultMap.entries.map {
                    (matchedResult, types) ->
                MatchedTypes(matchedResult, types)
            }
        return result
    }

    fun matchedTypes(
        attackingTypeId: Int,
        defendingTypeId: Int,
    ): MatchedTypes {
        return MatchedTypes(
            typeData.typeMatchedTable[attackingTypeId][defendingTypeId],
            listOf(typeData.allTypes.get(defendingTypeId)),
        )
    }

    fun allTypes(): List<TypeInfo> = typeData.allTypes
}

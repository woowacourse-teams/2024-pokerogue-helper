package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.TypeMatchedResult
import poke.rogue.helper.local.DummyTypeData

class LocalTypeDataSource(private val typeData: DummyTypeData = DummyTypeData) {
    fun findAllTypesAgainstAttackingType(attackingTypeId: Int): List<TypeMatchedResult> {
        val allResult = typeData.typeMatchedTable[attackingTypeId]
        val resultMap = allResult.withIndex().groupBy({ it.value }, { typeData.allTypes[it.index] })
        val result =
            resultMap.entries.map {
                    (matchedResult, types) ->
                TypeMatchedResult(matchedResult, types)
            }
        return result
    }

    fun findAllTypesAgainstDefendingType(defendingTypeId: Int): List<TypeMatchedResult> {
        val allResult = typeData.typeMatchedTable.map { it[defendingTypeId] }
        val resultMap = allResult.withIndex().groupBy({ it.value }, { typeData.allTypes[it.index] })
        val result =
            resultMap.entries.map {
                    (matchedResult, types) ->
                TypeMatchedResult(matchedResult, types)
            }
        return result
    }

    fun findMatchedTypeResult(
        attackingTypeId: Int,
        defendingTypeId: Int,
    ): TypeMatchedResult {
        return TypeMatchedResult(
            typeData.typeMatchedTable[attackingTypeId][defendingTypeId],
            listOf(typeData.allTypes.get(defendingTypeId)),
        )
    }
}

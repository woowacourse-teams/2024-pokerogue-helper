package poke.rogue.helper.data.datasource

import poke.rogue.helper.data.model.MatchedResult
import poke.rogue.helper.data.model.MatchedTypes
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.model.TypeMatchedTable

class LocalTypeDataSource {
    fun matchedTypesAgainstAttackingType(attackingTypeId: Int): List<MatchedTypes> {
        val attackingType = Type.fromId(attackingTypeId)
        val result =
            TypeMatchedTable.typeMatchedTable.getValue(attackingType).entries.groupBy(
                { it.value },
                { it.key },
            )

        return result.toMatchedTypesList()
    }

    fun matchedTypesAgainstDefendingType(defendingTypeId: Int): List<MatchedTypes> {
        val defendingType = Type.fromId(defendingTypeId)
        val result =
            TypeMatchedTable.typeMatchedTable.entries.groupBy(
                keySelector = { (_, defenderMap) -> defenderMap.getValue(defendingType) },
                valueTransform = { (attackerType, _) -> attackerType },
            )

        return result.toMatchedTypesList()
    }

    fun matchedTypes(
        attackingTypeId: Int,
        defendingTypeIds: List<Int>,
    ): List<MatchedTypes> {
        val attackingType = Type.fromId(attackingTypeId)
        val defendingTypes = defendingTypeIds.map { Type.fromId(it) }
        val result =
            defendingTypes.groupBy { defendingType ->
                TypeMatchedTable.typeMatchedTable.getValue(attackingType).getValue(defendingType)
            }
        return result.toMatchedTypesList()
    }

    fun allTypes(): List<Type> = Type.entries.toList()

    private fun Map<MatchedResult, List<Type>>.toMatchedTypesList(): List<MatchedTypes> =
        this.entries.map { (matchedResult, types) ->
            MatchedTypes(matchedResult, types)
        }
}

package poke.rogue.helper.presentation.dex.model

import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_ALOLA_RAICHU
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_EEVEE
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_ESPEON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_FLAREON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_GIGA_PIKACHU
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_GLACEON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_GOLDUCK
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_JOLTEON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_LEAFEON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_PICHU
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_PIKACHU
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_PSYDUCK
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_RAICHU
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_SYLYEON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_UMBREON
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.DUMMY_VAPOREON

data class EvolutionsUiModel(
    val evolutions: List<SingleEvolutionUiModel>,
) {
    constructor(vararg evolutions: SingleEvolutionUiModel) : this(evolutions.toList())

    fun evolutions(depth: Int) = evolutions.filter { it.depth == depth }

    fun hasEvolutionChain(): Boolean = evolutions.size > 1

    companion object {
        val DUMMY_PICAKCHU_EVOLUTION =
            EvolutionsUiModel(
                evolutions =
                    listOf(
                        DUMMY_PICHU,
                        DUMMY_PIKACHU,
                        DUMMY_RAICHU,
                        DUMMY_ALOLA_RAICHU,
                        DUMMY_GIGA_PIKACHU,
                    ),
            )

        val DUMMY_PSYDUCK_EVOLUTION =
            EvolutionsUiModel(
                evolutions =
                    listOf(
                        DUMMY_PSYDUCK,
                        DUMMY_GOLDUCK,
                    ),
            )

        val DUMMY_EVE_EVOLUTION =
            EvolutionsUiModel(
                evolutions =
                    listOf(
                        DUMMY_EEVEE,
                        DUMMY_SYLYEON,
                        DUMMY_ESPEON,
                        DUMMY_UMBREON,
                        DUMMY_VAPOREON,
                        DUMMY_JOLTEON,
                        DUMMY_FLAREON,
                        DUMMY_LEAFEON,
                        DUMMY_GLACEON,
                    ),
            )
    }
}

package poke.rogue.helper.presentation.battle.selection

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.model.PokemonSelectionUiModel
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

class BattleSelectionViewModel(logger: AnalyticsLogger = analyticsLogger()) :
    ErrorHandleViewModel(logger), BattleSelectionHandler {
    private val _pokemons = MutableStateFlow(PokemonSelectionUiModel.DUMMY)
    val pokemons = _pokemons.asStateFlow()

    private val _selectedPokemon = MutableStateFlow<PokemonSelectionUiModel?>(null)
    val selectedPokemon: StateFlow<PokemonSelectionUiModel?> = _selectedPokemon.asStateFlow()

    private val _skills = MutableStateFlow(SkillSelectionUiModel.DUMMY)
    val skills = _skills.asStateFlow()

    private val _selectedSkill = MutableStateFlow<SkillSelectionUiModel?>(null)
    val selectedSkill: StateFlow<SkillSelectionUiModel?> = _selectedSkill.asStateFlow()

    override fun selectPokemon(pokemon: PokemonSelectionUiModel) {
        _selectedPokemon.value = pokemon
    }

    override fun selectSkill(skill: SkillSelectionUiModel) {
        _selectedSkill.value = skill
    }
}

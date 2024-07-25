package poke.rogue.helper.presentation.ability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.ability.model.AbilityUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.util.view.QueryHandler

class AbilityViewModel(private val abilityRepository: AbilityRepository) :
    ViewModel(),
    QueryHandler,
    AbilityUiEventHandler {
    private val _navigationToDetailEvent = MutableSharedFlow<Long>()
    val navigationToDetailEvent: SharedFlow<Long> = _navigationToDetailEvent.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<List<AbilityUiModel>> =
        searchQuery
            .debounce(300)
            .mapLatest { query ->
                queriedAbilities(query)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                emptyList(),
            )

    override fun onQueryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }

    private suspend fun queriedAbilities(query: String): List<AbilityUiModel> = abilityRepository.abilities(query).map { it.toUi() }

    override fun navigateToDetail(abilityId: Long) {
        viewModelScope.launch {
            _navigationToDetailEvent.emit(abilityId)
        }
    }

    companion object {
        fun factory(abilityRepository: AbilityRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                AbilityViewModel(abilityRepository)
            }
    }
}

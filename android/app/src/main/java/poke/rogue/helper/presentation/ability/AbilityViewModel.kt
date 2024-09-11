package poke.rogue.helper.presentation.ability

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.AbilityRepository
import poke.rogue.helper.presentation.ability.model.AbilityUiModel
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BaseViewModelFactory
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel

class AbilityViewModel(
    private val abilityRepository: AbilityRepository,
    logger: AnalyticsLogger = analyticsLogger(),
) : ErrorHandleViewModel(logger),
    AbilityQueryHandler,
    AbilityUiEventHandler {
    private val _navigationToDetailEvent = MutableSharedFlow<String>()
    val navigationToDetailEvent: SharedFlow<String> = _navigationToDetailEvent.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<AbilityUiState<List<AbilityUiModel>>> =
        refreshEvent.onStart {
            if (searchQuery.value.isEmpty()) {
                emit(Unit)
            }
        }.flatMapLatest {
            searchQuery
                .debounce(300)
                .mapLatest { query ->
                    val abilities = queriedAbilities(query)
                    AbilityUiState.Success(abilities)
                }.catch { e ->
                    handlePokemonError(e)
                }
        }.stateIn(
            viewModelScope + errorHandler,
            SharingStarted.WhileSubscribed(5000L),
            AbilityUiState.Loading,
        )

    override fun queryName(name: String) {
        viewModelScope.launch {
            searchQuery.emit(name)
        }
    }

    private suspend fun queriedAbilities(query: String): List<AbilityUiModel> = abilityRepository.abilities(query).map { it.toUi() }

    override fun navigateToDetail(abilityId: String) {
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

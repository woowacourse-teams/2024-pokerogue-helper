package poke.rogue.helper.presentation.home

sealed interface HomeNavigateUiState {
    data object ToType : HomeNavigateUiState

    data object ToDex : HomeNavigateUiState

    data object ToAbility : HomeNavigateUiState

    data object ToTip : HomeNavigateUiState

    data object ToLogo : HomeNavigateUiState
}

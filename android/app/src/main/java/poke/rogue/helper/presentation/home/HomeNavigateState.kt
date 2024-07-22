package poke.rogue.helper.presentation.home

sealed interface HomeNavigateState {
    data object ToType : HomeNavigateState

    data object ToDex : HomeNavigateState

    data object ToAbility : HomeNavigateState

    data object ToTip : HomeNavigateState

    data object ToLogo : HomeNavigateState
}

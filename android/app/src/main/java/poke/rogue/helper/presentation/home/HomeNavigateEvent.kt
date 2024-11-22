package poke.rogue.helper.presentation.home

sealed interface HomeNavigateEvent {
    data object ToType : HomeNavigateEvent

    data object ToDex : HomeNavigateEvent

    data object ToAbility : HomeNavigateEvent

    data object ToTip : HomeNavigateEvent

    data object ToLogo : HomeNavigateEvent

    data object ToBiome : HomeNavigateEvent

    data object ToItem : HomeNavigateEvent

    data object ToBattle : HomeNavigateEvent
}

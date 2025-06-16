package cd.zgeniuscoders.floapp.ui.screens.dashboard

sealed interface DashboardEvent {

    data object OnRefreshPage : DashboardEvent
    data object OnLoadData : DashboardEvent

}
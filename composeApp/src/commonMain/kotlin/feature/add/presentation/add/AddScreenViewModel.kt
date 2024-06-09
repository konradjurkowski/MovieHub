package feature.add.presentation.add

import core.architecture.BaseViewModel

class AddScreenViewModel : BaseViewModel<AddScreenIntent, AddScreenSideEffect, AddScreenState>() {
    override fun getDefaultState(): AddScreenState = AddScreenState

    override fun processIntent(intent: AddScreenIntent) {
       when (intent) {
           AddScreenIntent.AddMoviePressed -> sendSideEffect(AddScreenSideEffect.GoToAddMovie)
           AddScreenIntent.AddSeriesPressed -> sendSideEffect(AddScreenSideEffect.GoToAddSeries)
       }
    }
}

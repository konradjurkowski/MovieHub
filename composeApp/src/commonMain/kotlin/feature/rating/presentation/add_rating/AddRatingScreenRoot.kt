package feature.rating.presentation.add_rating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.koin.getScreenModel
import core.architecture.BaseScreen
import core.architecture.CollectSideEffects
import core.navigation.GlobalNavigators
import core.utils.LocalSnackbarState
import core.utils.getFailureMessage
import feature.movies.domain.model.FirebaseRating
import feature.rating.presentation.add_rating.componenets.AddRatingScreen
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.add_rating_screen_rating_added
import org.koin.core.parameter.parametersOf

class AddRatingScreenRoot(
    val mediaId: Long,
    val isMovie: Boolean = true,
    val firebaseRating: FirebaseRating? = null,
) : BaseScreen() {

    @Composable
    override fun Content() {
        val snackbarState = LocalSnackbarState.current

        val viewModel = getScreenModel<AddRatingViewModel> { parametersOf(mediaId) }
        val state by viewModel.viewState.collectAsState()

        CollectSideEffects(viewModel.viewSideEffects) { effect ->
            when (effect) {
                is AddRatingSideEffect.ShowError -> {
                    snackbarState.showError(getFailureMessage(effect.error))
                }
                AddRatingSideEffect.Success -> {
                    GlobalNavigators.navigator?.pop()
                    snackbarState.showSuccess(Res.string.add_rating_screen_rating_added)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.sendIntent(AddRatingIntent.LoadInitialData(firebaseRating))
        }

        AddRatingScreen(
            state = state,
            isMovie = isMovie,
            onIntent = viewModel::sendIntent,
        )
    }
}

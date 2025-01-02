package feature.rating.presentation.add_rating.componenets

import StarRatingBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.text_field.InputTextField
import core.components.text_field.TextFieldLabel
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import core.utils.clearFocus
import feature.rating.presentation.add_rating.AddRatingIntent
import feature.rating.presentation.add_rating.AddRatingState
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.add_rating_screen_comment_label
import moviehub.composeapp.generated.resources.add_rating_screen_rating_label
import moviehub.composeapp.generated.resources.save_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddRatingScreen(
    state: AddRatingState,
    isMovie: Boolean,
    onIntent: (AddRatingIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.clearFocus(),
        topBar = { LogoTopBar(isLeadingVisible = true) },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = Dimens.padding16),
        ) {
            TextFieldLabel(text = stringResource(Res.string.add_rating_screen_rating_label))
            SmallSpacer()
            StarRatingBar(
                maxStars = 10,
                rating = state.rating,
                onRatingChanged = { onIntent(AddRatingIntent.RatingUpdated(it)) },
            )
            RegularSpacer()
            TextFieldLabel(text = stringResource(Res.string.add_rating_screen_comment_label))
            SmallSpacer()
            InputTextField(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                value = state.comment,
                onValueChange = { onIntent(AddRatingIntent.CommentUpdated(it)) },
                textStyle = MaterialTheme.typography.bodySmall,
                singleLine = false,
                isError = state.ratingState.isFailure()
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.save_label),
                loading = state.ratingState.isLoading(),
            ) {
                focusManager.clearFocus()
                onIntent(AddRatingIntent.Submit(isMovie, state.rating, state.comment))
            }
        }
    }
}

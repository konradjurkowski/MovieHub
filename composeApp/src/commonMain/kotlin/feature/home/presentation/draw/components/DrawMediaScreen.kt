package feature.home.presentation.draw.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.components.button.PrimaryButton
import core.components.image.AnyImage
import core.components.loading.LoadingIndicator
import core.components.other.LargeSpacer
import core.components.other.RegularSpacer
import core.components.result.FailureWidget
import core.components.top_bar.MainTopBar
import core.utils.Dimens
import core.utils.getScreenSizeInfo
import feature.home.presentation.draw.DrawMediaIntent
import feature.home.presentation.draw.DrawMediaState
import feature.home.presentation.draw.isDataLoaded
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.draw_media_screen_almost_there_label
import moviehub.composeapp.generated.resources.draw_media_screen_got_label
import moviehub.composeapp.generated.resources.draw_media_screen_ok_label
import moviehub.composeapp.generated.resources.draw_media_screen_shake_label
import moviehub.composeapp.generated.resources.draw_media_screen_shake_the_phone_label
import moviehub.composeapp.generated.resources.draw_media_screen_well_done_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun DrawMediaScreen(
    state: DrawMediaState,
    onIntent: (DrawMediaIntent) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(state.selectedMovie, state.selectedSeries) {
        if (state.selectedMovie != null || state.selectedSeries != null) {
            pagerState.animateScrollToPage(page = 1, animationSpec = tween(durationMillis = 500))
        }
    }

    Scaffold(
        topBar = {
            DrawMediaTopBar(
                isDataLoaded = state.isDataLoaded(),
                shakeCount = state.shakeCount,
                onShakePressed = { onIntent(DrawMediaIntent.OnShakePressed) },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                state.isLoading -> LoadingIndicator(modifier = Modifier.fillMaxSize())

                state.isDataLoaded() -> DrawMediaSuccess(
                    pagerState = pagerState,
                    state = state,
                    onIntent = onIntent,
                )

                else -> FailureWidget { onIntent(DrawMediaIntent.TryAgainPressed) }
            }
        }
    }
}

@Composable
private fun DrawMediaTopBar(
    isDataLoaded: Boolean,
    shakeCount: Int,
    onShakePressed: () -> Unit,
) {
    MainTopBar {
        AnimatedVisibility(visible = shakeCount < 3 && isDataLoaded) {
            ElevatedButton(
                onClick = onShakePressed,
                shape = RoundedCornerShape(Dimens.radius12),
            ) {
                Text(
                    text = stringResource(Res.string.draw_media_screen_shake_label),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
        RegularSpacer()
    }
}

@Composable
fun DrawMediaSuccess(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    state: DrawMediaState,
    onIntent: (DrawMediaIntent) -> Unit,
) {
    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
        userScrollEnabled = false,
    ) { index ->
        when (index) {
            0 -> ShakingContent(shakeCount = state.shakeCount)
            1 -> {
                if (state.selectedMovie != null) {
                    ShakeResultContent(
                        title = state.selectedMovie.title,
                        imageUrl = state.selectedMovie.posterPath,
                        onItemPressed = { onIntent(DrawMediaIntent.MoviePressed(state.selectedMovie)) },
                    )
                    return@HorizontalPager
                }

                if (state.selectedSeries != null) {
                    ShakeResultContent(
                        title = state.selectedSeries.name,
                        imageUrl = state.selectedSeries.posterPath,
                        onItemPressed = { onIntent(DrawMediaIntent.SeriesPressed(state.selectedSeries)) },
                    )
                    return@HorizontalPager
                }
            }
        }
    }
}

@Composable
private fun ShakingContent(
    modifier: Modifier = Modifier,
    shakeCount: Int,
) {
    val screenSizeInfo = getScreenSizeInfo()
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(Res.readBytes("files/shake_anim.json").decodeToString())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.padding16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(
            modifier = Modifier.height(8.dp),
            progress = { shakeCount / 3f },
            drawStopIndicator = {},
        )
        RegularSpacer()
        Text(
            text = when (shakeCount) {
                0 -> stringResource(Res.string.draw_media_screen_shake_the_phone_label)
                1, 2 -> stringResource(Res.string.draw_media_screen_almost_there_label)
                else -> stringResource(Res.string.draw_media_screen_well_done_label)
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        LargeSpacer()
        Image(
            modifier = Modifier.size(screenSizeInfo.height * 0.3f),
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever,
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun ShakeResultContent(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String?,
    onItemPressed: () -> Unit,
) {
    val screenSizeInfo = getScreenSizeInfo()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(screenSizeInfo.width * 0.1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.draw_media_screen_got_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        RegularSpacer()
        Card(
            modifier = Modifier
                .height(screenSizeInfo.height * 0.4f)
                .width(screenSizeInfo.height * 0.3f),
            shape = RoundedCornerShape(Dimens.radius12),
        ) {
            AnyImage(modifier = Modifier.fillMaxSize(), image = imageUrl)
        }
        RegularSpacer()
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.draw_media_screen_ok_label),
            onClick = onItemPressed,
        )
    }
}

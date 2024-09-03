package feature.movies.presentation.movie_details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.components.button.BoxButton
import core.components.image.NetworkImage
import core.components.media.MediaInfo
import core.components.media.MediaRating
import core.components.media.cast.CastList
import core.components.media.comments.CommentList
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.components.tab_row.AppTabRow
import core.model.MediaTab
import core.theme.withA70
import core.utils.Dimens
import core.utils.LocalDateTimeFormatter
import core.utils.getScreenSizeInfo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import feature.auth.domain.AppUser
import feature.movies.domain.model.CastData
import feature.movies.domain.model.FirebaseMovie
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.MovieDetails
import feature.movies.presentation.info_tab.MovieInfoTab
import kotlinx.coroutines.launch
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_calendar
import moviehub.composeapp.generated.resources.ic_clock
import moviehub.composeapp.generated.resources.ic_ticket
import moviehub.composeapp.generated.resources.minutes_label
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsSuccess(
    movie: MovieDetails,
    firebaseMovie: FirebaseMovie,
    castData: CastData,
    users: List<AppUser>,
    selectedTab: Int,
    onBackPressed: () -> Unit,
    onAddCommentPressed: (FirebaseRating?) -> Unit,
    onDeleteCommentPressed: (FirebaseRating) -> Unit,
    onTabPressed: (Int) -> Unit,
) {
    val dateTimeFormatter = LocalDateTimeFormatter.current
    val screenSize = getScreenSizeInfo()
    val pagerState = rememberPagerState(pageCount = { MediaTab.entries.size })
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(modifier = Modifier.height(screenSize.height * 0.6f)) {
            Box(modifier = Modifier.fillMaxSize()) {
                NetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = movie.backdropPath,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                ),
                            )
                        )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.padding16),
            ) {
                BoxButton(modifier = Modifier.safeDrawingPadding()) { onBackPressed() }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .height(140.dp),
                        shape = RoundedCornerShape(Dimens.radius12),
                        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
                    ) {
                        NetworkImage(
                            modifier = Modifier.fillMaxSize(),
                            imageUrl = movie.posterPath,
                        )
                    }
                    RegularSpacer()
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    RegularSpacer()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                MediaInfo(
                                    modifier = Modifier.padding(end = Dimens.padding8),
                                    iconRes = Res.drawable.ic_calendar,
                                    text = dateTimeFormatter.formatToYear(movie.releaseDate),
                                )
                                VerticalDivider(
                                    modifier = Modifier.height(Dimens.padding16),
                                    color = MaterialTheme.colorScheme.onBackground.withA70(),
                                )
                                MediaInfo(
                                    modifier = Modifier.padding(horizontal = Dimens.padding8),
                                    iconRes = Res.drawable.ic_clock,
                                    text = "${movie.runtime} ${stringResource(Res.string.minutes_label)}",
                                )
                                VerticalDivider(
                                    modifier = Modifier.height(Dimens.padding16),
                                    color = MaterialTheme.colorScheme.onBackground.withA70(),
                                )
                                MediaInfo(
                                    modifier = Modifier.padding(start = Dimens.padding8),
                                    iconRes = Res.drawable.ic_ticket,
                                    text = movie.genres.firstOrNull()?.name ?: "",
                                )
                            }
                        }
                        SmallSpacer()
                        MediaRating(rating = firebaseMovie.averageRating) {
                            val firebaseRating = firebaseMovie.ratings.firstOrNull { Firebase.auth.currentUser?.uid == it.userId }
                            onAddCommentPressed(firebaseRating)
                        }
                    }
                }
            }
        }
        AppTabRow(
            modifier = Modifier
                .padding(top = Dimens.padding8)
                .fillMaxWidth(),
            selectedTabIndex = selectedTab,
            tabs = MediaTab.entries.map { stringResource(it.stringRes) },
            onTabPressed = {  tab ->
                scope.launch { scrollState.animateScrollTo(scrollState.maxValue) }
                onTabPressed(tab)
            },
        )
        SmallSpacer()
        HorizontalPager(
            modifier = Modifier
                .height(screenSize.height * 0.7f)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            state = pagerState,
            userScrollEnabled = false,
        ) { index ->
            when (index) {
                MediaTab.INFO.ordinal -> MovieInfoTab(movie = movie, castData = castData)
                MediaTab.COMMENTS.ordinal -> {
                    CommentList(
                        ratings = firebaseMovie.ratings,
                        users = users,
                        onEditPressed = onAddCommentPressed,
                        onDeletePressed =  onDeleteCommentPressed,
                    )
                }
                MediaTab.CAST.ordinal -> CastList(casts = castData.cast)
            }
        }
    }
}

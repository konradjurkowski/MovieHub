package feature.movies.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.components.media.comments.CommentList
import core.components.media.details.MediaDetailsBackground
import core.components.media.details.MediaDetailsInfo
import core.components.other.SmallSpacer
import core.components.tab_row.AppTabRow
import core.model.MediaTab
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
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.minutes_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieDetailsSuccess(
    modifier: Modifier = Modifier,
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

    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(modifier = Modifier.height(screenSize.height * 0.6f)) {
            MediaDetailsBackground(backgroundPath = movie.backdropPath)
            MediaDetailsInfo(
                title = movie.title,
                releaseDate = dateTimeFormatter.formatToYear(movie.releaseDate),
                duration = "${movie.runtime} ${stringResource(Res.string.minutes_label)}",
                genre = movie.genres.firstOrNull()?.name ?: "",
                posterPath = movie.posterPath,
                rating = firebaseMovie.averageRating,
                onBackPressed = onBackPressed,
                onRatingPressed = {
                    val user = Firebase.auth.currentUser
                    val firebaseRating = firebaseMovie.ratings.firstOrNull { user?.uid == it.userId }
                    onAddCommentPressed(firebaseRating)
                },
            )
        }
        AppTabRow(
            modifier = Modifier
                .padding(top = Dimens.padding8)
                .fillMaxWidth(),
            selectedTabIndex = selectedTab,
            tabs = MediaTab.entries.map { stringResource(it.stringRes) },
            onTabPressed = { onTabPressed(it) },
        )
        SmallSpacer()
        HorizontalPager(
            modifier = Modifier
                .defaultMinSize(minHeight = screenSize.height * 0.8f)
                .fillMaxWidth(),
            state = pagerState,
            userScrollEnabled = false,
            verticalAlignment = Alignment.Top,
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
            }
        }
    }
}

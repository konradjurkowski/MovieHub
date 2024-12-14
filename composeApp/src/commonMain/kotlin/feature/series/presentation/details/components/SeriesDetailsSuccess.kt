package feature.series.presentation.details.components

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
import feature.movies.domain.model.FirebaseRating
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.SeriesDetails
import feature.series.presentation.info_tab.SeriesInfoTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.seasons_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeriesDetailsSuccess(
    modifier: Modifier = Modifier,
    series: SeriesDetails,
    firebaseSeries: FirebaseSeries,
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
            MediaDetailsBackground(backgroundPath = series.backdropPath)
            MediaDetailsInfo(
                title = series.name,
                releaseDate = dateTimeFormatter.formatToYear(series.firstAirDate),
                duration = "${series.seasons.size} ${stringResource(Res.string.seasons_label)}",
                genre = series.genres.firstOrNull()?.name ?: "",
                posterPath = series.posterPath,
                rating = firebaseSeries.averageRating,
                onBackPressed = onBackPressed,
                onRatingPressed = {
                    val user = Firebase.auth.currentUser
                    val firebaseRating = firebaseSeries.ratings.firstOrNull { user?.uid == it.userId }
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
            onTabPressed = {  onTabPressed(it) },
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
                MediaTab.INFO.ordinal -> SeriesInfoTab(series = series, castData = castData)
                MediaTab.COMMENTS.ordinal -> {
                    CommentList(
                        ratings = firebaseSeries.ratings,
                        users = users,
                        onEditPressed = onAddCommentPressed,
                        onDeletePressed = onDeleteCommentPressed,
                    )
                }
            }
        }
    }
}

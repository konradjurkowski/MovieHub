package feature.series.presentation.series_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.components.button.BoxButton
import core.components.image.AnyImage
import core.components.media.MediaInfo
import core.components.media.MediaRating
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
import feature.movies.domain.model.FirebaseRating
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.SeriesDetails
import feature.series.presentation.info_tab.SeriesInfoTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_calendar
import moviehub.composeapp.generated.resources.ic_clock
import moviehub.composeapp.generated.resources.ic_ticket
import moviehub.composeapp.generated.resources.seasons_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeriesDetailsSuccess(
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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(modifier = Modifier.height(screenSize.height * 0.6f)) {
            Box(modifier = Modifier.fillMaxSize()) {
                AnyImage(
                    modifier = Modifier.fillMaxSize(),
                    image = series.backdropPath,
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
                        AnyImage(
                            modifier = Modifier.fillMaxSize(),
                            image = series.posterPath,
                        )
                    }
                    RegularSpacer()
                    Text(
                        text = series.name,
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
                                    text = dateTimeFormatter.formatToYear(series.firstAirDate),
                                )
                                VerticalDivider(
                                    modifier = Modifier.height(Dimens.padding16),
                                    color = MaterialTheme.colorScheme.onBackground.withA70(),
                                )
                                MediaInfo(
                                    modifier = Modifier.padding(horizontal = Dimens.padding8),
                                    iconRes = Res.drawable.ic_clock,
                                    text = "${series.seasons.size} ${stringResource(Res.string.seasons_label)}",
                                )
                                VerticalDivider(
                                    modifier = Modifier.height(Dimens.padding16),
                                    color = MaterialTheme.colorScheme.onBackground.withA70(),
                                )
                                MediaInfo(
                                    modifier = Modifier.padding(start = Dimens.padding8),
                                    iconRes = Res.drawable.ic_ticket,
                                    text = series.genres.firstOrNull()?.name ?: "",
                                )
                            }
                        }
                        SmallSpacer()
                        MediaRating(rating = firebaseSeries.averageRating) {
                            val firebaseRating = firebaseSeries.ratings.firstOrNull { Firebase.auth.currentUser?.uid == it.userId }
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

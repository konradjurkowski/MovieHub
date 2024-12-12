package core.components.media.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.result.EmptyView
import core.utils.Dimens
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import feature.auth.domain.AppUser
import feature.movies.domain.model.FirebaseRating

@Composable
fun CommentList(
    modifier: Modifier = Modifier,
    ratings: List<FirebaseRating>,
    users: List<AppUser>,
    onEditPressed: (FirebaseRating) -> Unit,
    onDeletePressed: (FirebaseRating) -> Unit,
) {
    if (ratings.isEmpty()) return EmptyView()

    val user = Firebase.auth.currentUser
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
    ) {
        ratings.forEachIndexed { index, rating ->
            val commentUser = users.firstOrNull { rating.userId == it.userId }
            val isUserComment = commentUser?.userId == user?.uid
            commentUser?.let {
                CommentCard(
                    modifier = Modifier
                        .padding(bottom = if (index != ratings.size - 1) Dimens.padding16 else 0.dp),
                    rating = rating,
                    user = commentUser,
                    onEditPressed = if (isUserComment) onEditPressed else null,
                    onDeletePressed = if (isUserComment) onDeletePressed else null,
                )
            }
        }
    }
}

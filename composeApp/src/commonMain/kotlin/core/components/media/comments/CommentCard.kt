package core.components.media.comments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import core.components.image.NetworkImage
import core.components.media.RatingStars
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.theme.withA10
import core.theme.withA30
import core.theme.withA40
import core.theme.withA90
import core.utils.Dimens
import core.utils.LocalDateTimeFormatter
import core.utils.LocalTouchFeedback
import feature.auth.domain.AppUser
import feature.movies.domain.model.FirebaseRating
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.delete_label
import moviehub.composeapp.generated.resources.edit_label
import moviehub.composeapp.generated.resources.user_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    user: AppUser,
    rating: FirebaseRating,
    onEditPressed: ((FirebaseRating) -> Unit)? = null,
    onDeletePressed: ((FirebaseRating) -> Unit)? = null,
) {
    val dateTimeFormatter = LocalDateTimeFormatter.current

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground.withA10(),
        shape = RoundedCornerShape(Dimens.radius16),
        border = BorderStroke(
            width = Dimens.border1,
            color = MaterialTheme.colorScheme.onBackground.withA30(),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.padding16),
        ) {
            Row {
                UserSection(
                    modifier = Modifier.weight(1f),
                    user = user,
                    date = dateTimeFormatter.formatToDayMonthYear(rating.createdAt),
                )
                RegularSpacer()
                RatingStars(rating = rating.rating)
            }
            SmallSpacer()
            Text(
                text = rating.comment,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.withA90(),
                fontWeight = FontWeight.Light,
            )
            if (onEditPressed != null || onDeletePressed != null) {
                EditSection(
                    onEditPressed = { onEditPressed?.invoke(rating) },
                    onDeletePressed = { onDeletePressed?.invoke(rating) },
                )
            }
        }
    }
}

@Composable
private fun UserSection(
    modifier: Modifier = Modifier,
    user: AppUser,
    date: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(Dimens.radius5)),
            imageUrl = user.imageUrl,
            placeholderRes = Res.drawable.user_placeholder,
        )
        RegularSpacer()
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.withA40(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun EditSection(
    modifier: Modifier = Modifier,
    onEditPressed: () -> Unit,
    onDeletePressed: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current

    val editTextStyle = MaterialTheme.typography.bodySmall
        .copy(color = MaterialTheme.colorScheme.onBackground.withA90())
        .toSpanStyle()
    val deleteTextStyle = MaterialTheme.typography.bodySmall
        .copy(color = MaterialTheme.colorScheme.error.withA90())
        .toSpanStyle()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimens.padding8),
        horizontalArrangement = Arrangement.End,
    ) {
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = editTextStyle) { append(stringResource(Res.string.edit_label)) }
            },
            onClick = {
                touchFeedback.performLongPress()
                onEditPressed()
            },
        )
        SmallSpacer()
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = deleteTextStyle) { append(stringResource(Res.string.delete_label)) }
            },
            onClick = {
                touchFeedback.performLongPress()
                onDeletePressed()
            }
        )
    }
}

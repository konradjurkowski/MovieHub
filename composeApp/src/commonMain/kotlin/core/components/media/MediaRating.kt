package core.components.media

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import core.theme.starColor
import core.theme.withA10
import core.theme.withA40
import core.theme.withA50
import core.utils.Dimens
import core.utils.noRippleClickable
import core.utils.round
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun MediaRating(
    modifier: Modifier = Modifier,
    rating: Double,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .noRippleClickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Surface(
            modifier = modifier.size(Dimens.icon50),
            color = MaterialTheme.colorScheme.onBackground.withA10(),
            shape = RoundedCornerShape(Dimens.radius16),
            border = BorderStroke(
                width = Dimens.border1,
                color = MaterialTheme.colorScheme.onBackground.withA50(),
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                MovieRatingText(rating = rating)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -(Dimens.icon24 / 2)),
        ) {
            Icon(
                modifier = Modifier.size(Dimens.icon24),
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = null,
                tint = starColor,
            )
        }
    }
}

@Composable
private fun MovieRatingText(
    rating: Double,
) {
    val ratingString = rating.round(1).toString()
    val integerPart = ratingString.substringBefore(".")
    val decimalPart = ratingString.substringAfter(".")

    val styledText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            ),
        ) {
            append(integerPart)
        }
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.withA40(),
                fontWeight = FontWeight.SemiBold,
            ),
        ) {
            append(",")
            append(decimalPart)
        }
    }

    Text(text = styledText)
}

package com.konradjurkowski.moviehub.feature.profile.presentation.profile.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.konradjurkowski.moviehub.core.presentation.comp.image.CircleImage
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.TinySpacer
import com.konradjurkowski.moviehub.feature.auth.domain.model.User

@Composable
fun UserDataSection(
    modifier: Modifier = Modifier,
    user: User? = null,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircleImage(
            image = user?.imageUrl,
            actionIcon = Icons.Default.Edit,
            onActionClick = if (user != null) onEditClick else null,
        )
        SmallSpacer()
        Text(
            text = user?.name ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
        TinySpacer()
        Text(
            text = user?.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.ExtraLight,
        )
    }
}

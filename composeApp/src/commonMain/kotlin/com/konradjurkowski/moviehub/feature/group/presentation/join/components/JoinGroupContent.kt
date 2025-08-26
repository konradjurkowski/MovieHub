package com.konradjurkowski.moviehub.feature.group.presentation.join.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.konradjurkowski.moviehub.core.domain.model.ValidationResult
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.AppScaffold
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text.ClickableTextFooter
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InvalidFieldMessage
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.TextFieldLabel
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.noRippleClickable
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.CreateGroupPressed
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.InvitationCodeChanged
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.JoinGroupPressed
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.ScanQrCodePressed
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupState

@Composable
fun JoinGroupContent(
    state: JoinGroupState,
    onIntent: (JoinGroupIntent) -> Unit,
) {
    AppScaffold(
        topBar = {
            MainTopBar(title = "Join to Group")
        },
        bottomBar = {
            ClickableTextFooter(
                textPart1 = "Nie masz zaproszenia?",
                textPart2 = "Utwórz grupę",
                onClick = { onIntent(CreateGroupPressed) },
            )
        },
        keepBottomBarBelowKeyboard = true,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = Dimens.padding16)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Aby dołączyć do grupy, wpisz 8 znakowy kod zaproszenia, lub zeskanuj kod QR",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
            )
            RegularSpacer()
            TextFieldLabel(text = "Ivitation code")
            SmallSpacer()
            InputTextField(
                value = state.invitationCode,
                onValueChange = { onIntent(InvitationCodeChanged(it)) },
                keyboardType = KeyboardType.Number,
                isError = false,
            )
            InvalidFieldMessage(result = ValidationResult())
            RegularSpacer()
            Row(modifier = Modifier.noRippleClickable { onIntent(ScanQrCodePressed) }) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                SmallSpacer()
                Text(
                    text = "Scan the invittaion code",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Join",
                loading = state.joinState.isLoading(),
                onClick = { onIntent(JoinGroupPressed(state.invitationCode)) },
            )
        }
    }
}

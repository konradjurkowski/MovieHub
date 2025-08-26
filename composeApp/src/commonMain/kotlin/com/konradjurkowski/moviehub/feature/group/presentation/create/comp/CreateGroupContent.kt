package com.konradjurkowski.moviehub.feature.group.presentation.create.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.image.CircleImage
import com.konradjurkowski.moviehub.core.presentation.comp.other.AppScaffold
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InvalidFieldMessage
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.TextFieldLabel
import com.konradjurkowski.moviehub.core.presentation.comp.top_bar.MainTopBar
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.CreateClick
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.DescriptionChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.EditImageClick
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.NameChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupState
import com.preat.peekaboo.image.picker.toImageBitmap

@Composable
fun CreateGroupContent(
    state: CreateGroupState,
    onIntent: (CreateGroupIntent) -> Unit,
) {
    AppScaffold(
        topBar = {
            MainTopBar(title = "Create group")
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.padding16)
                .verticalScroll(rememberScrollState()),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircleImage(
                    modifier = Modifier.align(Alignment.Center),
                    image = state.image?.array?.toImageBitmap(),
                    onActionClick = { onIntent(EditImageClick) },
                )
            }
            RegularSpacer()
            TextFieldLabel(text = "Name")
            SmallSpacer()
            InputTextField(
                value = state.name,
                onValueChange = { onIntent(NameChanged(it)) },
                imeAction = ImeAction.Next,
                isError = !state.nameValidation.successful,
            )
            InvalidFieldMessage(result = state.nameValidation)
            RegularSpacer()
            TextFieldLabel(text = "Description")
            SmallSpacer()
            InputTextField(
                value = state.description,
                onValueChange = { onIntent(DescriptionChanged(it)) },
            )
            RegularSpacer()
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Create",
                loading = state.createState.isLoading(),
                onClick = {
                    val intent = CreateClick(
                        name = state.name,
                        description = state.description,
                        image = state.image,
                    )
                    onIntent(intent)
                },
            )
            RegularSpacer()
        }
    }
}

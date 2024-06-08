package feature.profile.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.top_bar.LogoTopBar
import core.utils.Dimens
import feature.profile.presentation.profile.components.UserDataSection

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            LogoTopBar()
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Dimens.regularPadding),
        ) {
            UserDataSection()
        }
    }
}





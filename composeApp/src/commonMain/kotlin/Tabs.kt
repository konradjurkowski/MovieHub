//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.rememberVectorPainter
//import cafe.adriel.voyager.koin.getScreenModel
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.Navigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import cafe.adriel.voyager.navigator.tab.Tab
//import cafe.adriel.voyager.navigator.tab.TabOptions
//import cafe.adriel.voyager.transitions.SlideTransition
//import core.components.button.PrimaryButton
//import core.components.media.HorizontalMediaList
//import core.components.media.VerticalMediaList
//import core.components.other.SmallSpacer
//import core.components.text.SectionTitle
//import core.utils.Resource
//import feature.movies.domain.model.Movie
//import feature.movies.presentation.movies.MoviesScreen
//import feature.movies.presentation.movies.MoviesViewModel
//import kotlin.jvm.Transient
//
//class MoviesTab(
//    @Transient
//    val onNavigator : (isRoot : Boolean) -> Unit,
//) : Tab {
//    override val options: TabOptions
//        @Composable
//        get() {
//            val title = "Movies"
//            val icon = rememberVectorPainter(Icons.Default.Home)
//
//            return remember {
//                TabOptions(
//                    index = 0u,
//                    title = title,
//                    icon = icon
//                )
//            }
//        }
//
//    @Composable
//    override fun Content() {
//        Navigator(MoviesScreen()) { navigator ->
//            LaunchedEffect(navigator.lastItem) {
//                onNavigator(navigator.lastItem is MoviesScreen)
//            }
//
//            SlideTransition(navigator)
//        }
//    }
//}
//
//object SeriesTab : Tab {
//    override val options: TabOptions
//        @Composable
//        get() {
//            val title = "Series"
//            val icon = rememberVectorPainter(Icons.Default.Info)
//
//            return remember {
//                TabOptions(
//                    index = 0u,
//                    title = title,
//                    icon = icon
//                )
//            }
//        }
//
//    @Composable
//    override fun Content() {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Text(
//                modifier = Modifier.align(Alignment.Center),
//                text = "Series Tab",
//            )
//        }
//    }
//}
//
//object SearchTab : Tab {
//    override val options: TabOptions
//        @Composable
//        get() {
//            val title = "Search"
//            val icon = rememberVectorPainter(Icons.Default.Search)
//
//            return remember {
//                TabOptions(
//                    index = 0u,
//                    title = title,
//                    icon = icon
//                )
//            }
//        }
//
//    @Composable
//    override fun Content() {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Text(
//                modifier = Modifier.align(Alignment.Center),
//                text = "Search Tab",
//            )
//        }
//    }
//}

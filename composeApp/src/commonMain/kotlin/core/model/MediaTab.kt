package core.model

import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.cast_tab_label
import moviehub.composeapp.generated.resources.comments_tab_label
import moviehub.composeapp.generated.resources.info_tab_label
import org.jetbrains.compose.resources.StringResource

enum class MediaTab(val stringRes: StringResource) {
    INFO(Res.string.info_tab_label),
    COMMENTS(Res.string.comments_tab_label),
    CAST(Res.string.cast_tab_label),
}

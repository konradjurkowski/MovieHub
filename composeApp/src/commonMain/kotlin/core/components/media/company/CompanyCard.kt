package core.components.media.company

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.components.image.AnyImage
import core.model.ProductionCompany
import core.utils.Dimens

@Composable
fun CompanyCard(
    modifier: Modifier = Modifier,
    company: ProductionCompany,
) {
    Surface(
        modifier = modifier
            .height(100.dp)
            .width(150.dp),
        color = MaterialTheme.colorScheme.onBackground,
        shape = RoundedCornerShape(Dimens.radius4),
    ) {
        AnyImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.padding8),
            image = company.logoPath,
            contentScale = ContentScale.Fit,
        )
    }
}

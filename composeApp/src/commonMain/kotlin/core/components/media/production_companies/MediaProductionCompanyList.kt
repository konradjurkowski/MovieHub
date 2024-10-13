package core.components.media.production_companies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.components.image.AnyImage
import core.components.other.SmallSpacer
import core.components.text.SectionTitle
import core.model.ProductionCompany
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.production_companies
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaProductionCompanyList(
    productionCompanyList: List<ProductionCompany>,
) {
    if (productionCompanyList.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth()) {
        SectionTitle(title = stringResource(Res.string.production_companies))
        SmallSpacer()
        productionCompanyList.take(2).forEach { productionCompany ->
            ProductionCompanyCard(
                modifier = Modifier.padding(bottom = Dimens.padding16),
                productionCompany = productionCompany,
            )
        }
    }
}

@Composable
private fun ProductionCompanyCard(
    modifier: Modifier = Modifier,
    productionCompany: ProductionCompany,
) {
    Surface(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        shape = RoundedCornerShape(Dimens.radius16),
    ) {
        AnyImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.padding8),
            image = productionCompany.logoPath,
            contentScale = ContentScale.Fit,
        )
    }
}

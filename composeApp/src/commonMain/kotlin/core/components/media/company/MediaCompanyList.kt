package core.components.media.company

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.text.SectionTitle
import core.model.ProductionCompany
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.production_companies
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaCompanyList(
    modifier: Modifier = Modifier,
    companyList: List<ProductionCompany>,
) {
    if (companyList.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.padding16))
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = stringResource(Res.string.production_companies),
        )
        CompanyHorizontalList(
            modifier = Modifier.padding(top = Dimens.padding8),
            companyList = companyList,
        )
    }
}

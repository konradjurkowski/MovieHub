package core.components.media.company

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.model.ProductionCompany
import core.utils.Dimens

@Composable
fun CompanyHorizontalList(
    modifier: Modifier = Modifier,
    companyList: List<ProductionCompany>,
) {
    val filteredList = companyList.filter { it.logoPath != null }

    LazyRow(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
    ) {
        itemsIndexed(filteredList) { index, company ->
            CompanyCard(
                modifier = Modifier
                    .padding(
                        start = if (index == 0) Dimens.padding16 else Dimens.padding4,
                        end = if (index == filteredList.size - 1) Dimens.padding16 else 0.dp,
                    ),
                company = company,
            )
        }
    }
}

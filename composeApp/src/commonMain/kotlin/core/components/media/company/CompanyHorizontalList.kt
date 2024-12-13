package core.components.media.company

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.model.ProductionCompany
import core.utils.paddingForIndex

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
                modifier = Modifier.paddingForIndex(index = index, size = companyList.size),
                company = company,
            )
        }
    }
}

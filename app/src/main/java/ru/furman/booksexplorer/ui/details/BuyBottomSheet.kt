package ru.furman.booksexplorer.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.furman.booksexplorer.R

@ExperimentalMaterialApi
@Composable
fun BuyBottomSheet(state: ModalBottomSheetState, screenContent: @Composable () -> Unit) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = MaterialTheme.shapes.large.copy(
            topStart = CornerSize(16.dp),
            topEnd = CornerSize(16.dp)
        ),
        sheetContent = {
            Box(
                Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.book_details_buy_error),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.error,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        content = screenContent
    )
}
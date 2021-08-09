package ru.furman.booksexplorer.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.furman.booksexplorer.R

@Composable
fun BuyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = R.string.book_details_buy_button),
            style = MaterialTheme.typography.button.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@Preview
@Composable
fun BuyButtonPreview() {
    Box(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        BuyButton(Modifier.fillMaxWidth()) {}
    }
}
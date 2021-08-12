package ru.furman.booksexplorer.utils

import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.min

fun LazyListState.toolbarOffsetByItem(itemInd: Int = 0): Float {
    val calculatedOffset = when {
        firstVisibleItemIndex < itemInd -> 1f
        firstVisibleItemIndex == itemInd -> {
            1f - (firstVisibleItemScrollOffset.toFloat()
                    / (layoutInfo.visibleItemsInfo.getOrNull(itemInd)?.size?.toFloat() ?: 1f))
        }
        else -> 0f
    }
    return min(1f, calculatedOffset)
}
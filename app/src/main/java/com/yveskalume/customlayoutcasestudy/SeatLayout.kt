package com.yveskalume.customlayoutcasestudy

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yveskalume.customlayoutcasestudy.ui.theme.Purple40
import com.yveskalume.customlayoutcasestudy.ui.theme.Purple80
import com.yveskalume.customlayoutcasestudy.ui.theme.PurpleGrey40


@Composable
fun <T> SeatLayout(
    modifier: Modifier = Modifier,
    quantity: Int,
    numberPerRow: Int,
    data: List<T>,
    isAvailable: (index: Int, item: T) -> Boolean,
    content: @Composable (number: Int, item: T?) -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            (1..quantity).forEach { index ->
                val item = data.firstOrNull { isAvailable(index, it) }
                content(index, item)
            }
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth / numberPerRow,
                    minWidth = constraints.maxWidth / numberPerRow,
                    maxHeight = constraints.maxWidth / numberPerRow,
                    minHeight = constraints.maxWidth / numberPerRow
                )
            )
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0
            var xPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.place(x = xPosition, y = yPosition)

                // Record the y co-ord placed up to
                xPosition += placeable.measuredWidth
                if (xPosition + placeable.measuredWidth > constraints.maxWidth) {
                    xPosition = 0
                    yPosition += placeable.measuredWidth
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeatLayoutPreview() {
    val selectedData: SnapshotStateMap<Int, Seat> = remember {
        mutableStateMapOf()
    }
    MaterialTheme {
        SeatLayout(
            modifier = Modifier.padding(24.dp),
            data = getDataList(),
            quantity = 28,
            numberPerRow = 4,
            isAvailable = { index, item -> item.number == index && item.isAvailable }
        ) { number, item ->
            val itemIsSelected = selectedData.containsKey(item?.id)
            val seatColor by animateColorAsState(
                targetValue = if (itemIsSelected) {
                    Purple40
                } else if (item != null) {
                    Purple80
                } else {
                    PurpleGrey40.copy(alpha = 0.6f)
                },
                label = ""
            )

            val seatTextColor by animateColorAsState(
                targetValue = if (itemIsSelected) {
                    Color.White
                } else if (item != null) {
                    Color.Black
                } else {
                    Color.White
                },
                label = ""
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(seatColor)
                    .clickable(enabled = item != null) {
                        if (itemIsSelected) {
                            selectedData.remove(item?.id)
                        } else {
                            if (item != null) {
                                selectedData[item.id] = item
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$number",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = seatTextColor
                )
            }

        }
    }
}






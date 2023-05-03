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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yveskalume.customlayoutcasestudy.ui.theme.Purple40
import com.yveskalume.customlayoutcasestudy.ui.theme.Purple80
import com.yveskalume.customlayoutcasestudy.ui.theme.PurpleGrey40

@Composable
fun MainScreen() {
    val selectedData: SnapshotStateList<Seat> = remember {
        mutableStateListOf()
    }
    MaterialTheme {
        SeatLayout(
            modifier = Modifier.padding(24.dp),
            data = getDataList(),
            quantity = 28,
            numberPerRow = 4,
            isAvailable = { index, item -> item.number == index && item.isAvailable }
        ) { number, item ->
            val seatColor by animateColorAsState(
                targetValue = if (selectedData.contains(item)) {
                    Purple40
                } else if (item != null) {
                    Purple80
                } else {
                    PurpleGrey40.copy(alpha = 0.6f)
                },
                label = ""
            )

            val seatTextColor by animateColorAsState(
                targetValue = if (selectedData.contains(item)) {
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
                        if (selectedData.contains(item)) {
                            selectedData.remove(item)
                        } else {
                            if (item != null) {
                                selectedData.add(item)
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}
package com.example.adivan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.ApplicationProgressStep
import com.example.adivan.data.StepState
import com.example.adivan.data.TimelineItem
import com.example.adivan.ui.theme.Cream
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextSecondary
import com.example.adivan.ui.theme.WarningOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdivanTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(title, fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
    )
}

@Composable
fun AdivanCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        content = { content() },
    )
}

@Composable
fun HorizontalProgressSteps(steps: List<ApplicationProgressStep>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        steps.forEachIndexed { index, step ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            ) {
                StepDot(state = step.state)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = step.label,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 11.sp,
                    color = when (step.state) {
                        StepState.Completed -> SuccessGreen
                        StepState.Current -> DarkGreen
                        StepState.Pending -> TextSecondary
                    },
                    fontWeight = if (step.state == StepState.Current) FontWeight.SemiBold else FontWeight.Normal,
                )
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(8.dp)
                        .height(2.dp)
                        .background(
                            if (step.state == StepState.Completed) SuccessGreen else Color.LightGray
                        )
                )
            }
        }
    }
}

@Composable
private fun StepDot(state: StepState) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(
                when (state) {
                    StepState.Completed -> SuccessGreen
                    StepState.Current -> DarkGreen
                    StepState.Pending -> Color(0xFFE0E0E0)
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            StepState.Completed -> Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp),
            )
            StepState.Current -> Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            StepState.Pending -> {}
        }
    }
}

@Composable
fun VerticalTimeline(items: List<TimelineItem>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TimelineNode(item.state)
                    if (index < items.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(72.dp)
                                .background(
                                    if (item.state == StepState.Completed) SuccessGreen
                                    else Color(0xFFE0E0E0)
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        color = when (item.state) {
                            StepState.Completed -> MaterialTheme.colorScheme.onSurface
                            StepState.Current -> DarkGreen
                            StepState.Pending -> TextSecondary
                        },
                    )
                    if (item.date != null) {
                        Text(text = item.date, fontSize = 13.sp, color = TextSecondary)
                    } else if (item.state == StepState.Current) {
                        Text(text = "In Progress", fontSize = 13.sp, color = DarkGreen, fontWeight = FontWeight.Medium)
                    }
                    Text(text = item.subtitle, fontSize = 13.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun TimelineNode(state: StepState) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(
                when (state) {
                    StepState.Completed -> SuccessGreen
                    StepState.Current -> DarkGreen
                    StepState.Pending -> Color(0xFFE0E0E0)
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            StepState.Completed -> Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
            StepState.Current -> Box(Modifier.size(10.dp).clip(CircleShape).background(Color.White))
            StepState.Pending -> Text("○", color = TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun TribalPatternBand(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(Cream),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        repeat(20) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(if (it % 2 == 0) DarkGreen else SoftGreen)
            )
        }
    }
}

@Composable
fun StatusChip(text: String, isWarning: Boolean = false) {
    Text(
        text = text,
        modifier = Modifier
            .background(
                if (isWarning) WarningOrange.copy(alpha = 0.15f) else SoftGreen,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        fontSize = 12.sp,
        color = if (isWarning) WarningOrange else DarkGreen,
        fontWeight = FontWeight.Medium,
    )
}

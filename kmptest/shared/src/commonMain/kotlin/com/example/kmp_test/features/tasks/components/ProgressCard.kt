package com.example.kmp_test.features.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_test.core.theme.Accent

@Composable
fun ProgressCard(completed: Int, total: Int) {
    Column(Modifier.fillMaxWidth().background(Accent, RoundedCornerShape(20.dp)).padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("오늘의 진행률", color = Color.White)
        Text("$completed / ${total} 완료", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(progress = { if (total == 0) 0f else completed.toFloat() / total }, modifier = Modifier.fillMaxWidth(), color = Color.White, trackColor = Color(0xFF8997EB))
    }
}

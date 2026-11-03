package com.example.kmp_test.features.tasks.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_test.features.tasks.model.Task

@Composable
fun TaskDetailScreen(task: Task?, onBack: () -> Unit, onToggle: () -> Unit, onDelete: () -> Unit) {
    Column(Modifier.widthIn(max = 600.dp).fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        TextButton(onClick = onBack) { Text("‹ 목록으로") }
        Text("할 일 상세", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
        if (task == null) {
            Text("할 일을 찾을 수 없어요.")
        } else {
            Text(task.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("상태: ${if (task.done) "완료" else "진행 중"}")
            Button(onClick = onToggle, modifier = Modifier.fillMaxWidth()) { Text(if (task.done) "진행 중으로 변경" else "완료로 변경") }
            TextButton(onClick = onDelete, modifier = Modifier.fillMaxWidth()) { Text("할 일 삭제", color = Color(0xFFC03945)) }
        }
    }
}

package com.example.kmp_test.features.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.kmp_test.features.tasks.model.Task

@Composable
fun TaskItem(task: Task, onToggle: (Boolean) -> Unit, onOpen: () -> Unit, onDelete: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(14.dp)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = task.done, onCheckedChange = { checked -> onToggle(checked) })
        Text("${task.title} ›", modifier = Modifier.weight(1f).clickable { onOpen() }.padding(vertical = 16.dp), textDecoration = if (task.done) TextDecoration.LineThrough else null, color = if (task.done) Color(0xFF747D8C) else Color(0xFF17213A))
        TextButton(onClick = { onDelete() }) { Text("삭제") }
    }
}

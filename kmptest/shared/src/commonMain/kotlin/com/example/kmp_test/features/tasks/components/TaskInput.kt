package com.example.kmp_test.features.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskInput(value: String, onChange: (String) -> Unit, onAdd: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(value = value, onValueChange = onChange, label = { Text("새 할 일") }, placeholder = { Text("새 할 일을 입력하세요") }, singleLine = true, modifier = Modifier.weight(1f))
        Button(onClick = { onAdd() }, enabled = value.trim().isNotEmpty()) { Text("추가") }
    }
}

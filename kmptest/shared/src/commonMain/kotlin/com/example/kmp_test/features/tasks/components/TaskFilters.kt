package com.example.kmp_test.features.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.kmp_test.features.tasks.model.TaskFilter

@Composable
fun TaskFilters(selected: TaskFilter, onChange: (TaskFilter) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TaskFilter.entries.forEach { value -> FilterChip(selected = selected == value, onClick = { onChange(value) }, label = { Text(value.label) }) }
    }
}

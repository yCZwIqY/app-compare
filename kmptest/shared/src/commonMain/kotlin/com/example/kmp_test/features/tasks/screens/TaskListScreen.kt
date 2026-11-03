package com.example.kmp_test.features.tasks.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_test.core.theme.Accent
import com.example.kmp_test.features.tasks.components.*
import com.example.kmp_test.features.tasks.state.TasksState

@Composable
fun TaskListScreen(state: TasksState, onOpen: (Int) -> Unit, onOpenYoutube: () -> Unit) {
    val visible = state.visible
    Column(Modifier.widthIn(max = 600.dp).fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(Modifier.height(8.dp))
        Text("SMALL STEPS, EVERY DAY", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("오늘의 할 일", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF17213A))
            TextButton(onClick = onOpenYoutube) { Text("유튜브 열기 ›") }
        }
        Text("작은 실천으로 하루를 채워보세요.", color = Color(0xFF747D8C))
        ProgressCard(completed = state.completed, total = state.tasks.size)
        TaskInput(value = state.draft, onChange = state::updateDraft, onAdd = state::add)
        TaskFilters(selected = state.filter, onChange = state::updateFilter)
        if (visible.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) { Text("표시할 할 일이 없어요.") }
        } else {
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(visible, key = { it.id }) { task ->
                    TaskItem(task = task, onToggle = { state.setDone(task.id, it) }, onOpen = { onOpen(task.id) }, onDelete = { state.remove(task.id) })
                }
            }
        }
        Text("한 번에 하나씩, 나만의 속도로.", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFF747D8C), fontSize = 12.sp)
    }
}

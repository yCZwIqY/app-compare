package com.example.kmp_test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read

private val accent = Color(0xFF5265D9)

@Composable
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val navController = rememberNavController()
    var tasks by remember { mutableStateOf(initialTasks()) }
    var nextId by remember { mutableStateOf(4) }
    var draft by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf(TaskFilter.ALL) }
    fun backToList() {
        if (!navController.popBackStack()) {
            navController.navigate("tasks") { launchSingleTop = true }
        }
    }
    val completed = tasks.count { it.done }
    val visible = tasks.filter { filter.matches(it) }
    fun add() {
        val title = draft.trim()
        if (title.isNotEmpty()) { tasks = tasks + Task(nextId++, title); draft = "" }
    }
    MaterialTheme(colorScheme = lightColorScheme(primary = accent, background = Color(0xFFF5F7FB))) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(Modifier.fillMaxSize().safeDrawingPadding().imePadding(), contentAlignment = Alignment.TopCenter) {
                NavHost(navController = navController, startDestination = "tasks", modifier = Modifier.widthIn(max = 600.dp).fillMaxSize()) {
                    composable("tasks") {
                Column(Modifier.widthIn(max = 600.dp).fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Spacer(Modifier.height(8.dp))
                    Text("SMALL STEPS, EVERY DAY", color = accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("오늘의 할 일", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF17213A))
                    Text("작은 실천으로 하루를 채워보세요.", color = Color(0xFF747D8C))
                    Column(Modifier.fillMaxWidth().background(accent, RoundedCornerShape(20.dp)).padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("오늘의 진행률", color = Color.White)
                        Text("$completed / ${tasks.size} 완료", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        LinearProgressIndicator(progress = { if (tasks.isEmpty()) 0f else completed.toFloat() / tasks.size }, modifier = Modifier.fillMaxWidth(), color = Color.White, trackColor = Color(0xFF8997EB))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(value = draft, onValueChange = { draft = it.take(100) }, label = { Text("새 할 일") }, placeholder = { Text("새 할 일을 입력하세요") }, singleLine = true, modifier = Modifier.weight(1f))
                        Button(onClick = { add() }, enabled = draft.trim().isNotEmpty()) { Text("추가") }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TaskFilter.entries.forEach { value -> FilterChip(selected = filter == value, onClick = { filter = value }, label = { Text(value.label) }) }
                    }
                    if (visible.isEmpty()) {
                        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) { Text("표시할 할 일이 없어요.") }
                    } else {
                        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(visible, key = { it.id }) { task ->
                                Row(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(14.dp)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = task.done, onCheckedChange = { checked -> tasks = tasks.map { if (it.id == task.id) it.copy(done = checked) else it } })
                                    Text("${task.title} ›", modifier = Modifier.weight(1f).clickable { navController.navigate("tasks/${task.id}") }.padding(vertical = 16.dp), textDecoration = if (task.done) TextDecoration.LineThrough else null, color = if (task.done) Color(0xFF747D8C) else Color(0xFF17213A))
                                    TextButton(onClick = { tasks = tasks.filter { it.id != task.id } }) { Text("삭제") }
                                }
                            }
                        }
                    }
                    Text("한 번에 하나씩, 나만의 속도로.", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFF747D8C), fontSize = 12.sp)
                }
                    }
                    composable("tasks/{id}") { entry ->
                        val taskId = entry.arguments?.read { getString("id") }
                        val task = tasks.find { it.id.toString() == taskId }
                        TaskDetailScreen(
                            task = task,
                            onBack = { backToList() },
                            onToggle = { tasks = tasks.map { if (it.id == task?.id) it.copy(done = !it.done) else it } },
                            onDelete = {
                                tasks = tasks.filter { it.id != task?.id }
                                backToList()
                            },
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(navController) { onNavHostReady(navController) }
}

@Composable
private fun TaskDetailScreen(task: Task?, onBack: () -> Unit, onToggle: () -> Unit, onDelete: () -> Unit) {
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

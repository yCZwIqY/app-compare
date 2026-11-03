package com.example.kmp_test.features.tasks.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kmp_test.features.tasks.model.*

// Shared by both destinations; route changes do not recreate this state holder.
class TasksState {
    var tasks by mutableStateOf(initialTasks())
        private set
    var draft by mutableStateOf("")
        private set
    var filter by mutableStateOf(TaskFilter.ALL)
        private set
    private var nextId = 4
    val completed: Int get() = tasks.count { it.done }
    val visible: List<Task> get() = tasks.filter { filter.matches(it) }
    fun updateDraft(value: String) { draft = value.take(100) }
    fun updateFilter(value: TaskFilter) { filter = value }
    fun add() {
        val title = draft.trim()
        if (title.isEmpty()) return
        tasks = tasks + Task(nextId++, title)
        draft = ""
    }
    fun setDone(id: Int, done: Boolean) { tasks = tasks.map { if (it.id == id) it.copy(done = done) else it } }
    fun toggle(id: Int) { tasks = tasks.map { if (it.id == id) it.copy(done = !it.done) else it } }
    fun remove(id: Int) { tasks = tasks.filter { it.id != id } }
}

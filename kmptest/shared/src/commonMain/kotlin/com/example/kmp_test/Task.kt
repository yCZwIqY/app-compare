package com.example.kmp_test

data class Task(val id: Int, val title: String, val done: Boolean = false)
enum class TaskFilter(val label: String) {
    ALL("전체"), ACTIVE("진행 중"), COMPLETED("완료");
    fun matches(task: Task): Boolean = when (this) {
        ALL -> true
        ACTIVE -> !task.done
        COMPLETED -> task.done
    }
}
fun initialTasks(): List<Task> = listOf(
    Task(1, "물 한 잔 마시기", true),
    Task(2, "20분 산책하기"),
    Task(3, "새로운 아이디어 기록하기"),
)

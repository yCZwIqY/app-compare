package com.example.kmp_test
import kotlin.test.Test
import kotlin.test.assertEquals
class SharedCommonTest {
    @Test fun filtersPartitionInitialTasks() {
        val tasks = initialTasks()
        assertEquals(3, tasks.filter { TaskFilter.ALL.matches(it) }.size)
        assertEquals(listOf(2, 3), tasks.filter { TaskFilter.ACTIVE.matches(it) }.map { it.id })
        assertEquals(listOf(1), tasks.filter { TaskFilter.COMPLETED.matches(it) }.map { it.id })
        val completed = tasks.map { it.copy(done = true) }
        assertEquals(emptyList(), completed.filter { TaskFilter.ACTIVE.matches(it) })
    }
}

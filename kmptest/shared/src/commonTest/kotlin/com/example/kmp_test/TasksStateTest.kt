package com.example.kmp_test

import com.example.kmp_test.features.tasks.model.TaskFilter
import com.example.kmp_test.features.tasks.state.TasksState
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksStateTest {
    @Test
    fun stateOwnsFilteringUpdatesAndUniqueIds() {
        val state = TasksState()
        state.updateDraft("   ")
        state.add()
        assertEquals(3, state.tasks.size)
        state.updateDraft("  새 할 일  ")
        state.add()
        assertEquals("새 할 일", state.tasks.last().title)
        assertEquals("", state.draft)
        state.toggle(4)
        state.updateFilter(TaskFilter.COMPLETED)
        assertEquals(listOf(1, 4), state.visible.map { it.id })
        assertEquals(2, state.completed)
        state.remove(4)
        state.updateDraft("다음 할 일")
        state.add()
        assertEquals(5, state.tasks.last().id)
        assertEquals(listOf(1), state.visible.map { it.id })
        state.updateDraft("가".repeat(101))
        state.add()
        assertEquals(100, state.tasks.last().title.length)
    }
}

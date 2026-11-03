import { useState } from 'react';
import { router } from 'expo-router';
import { FlatList, Pressable, StyleSheet, Text, View } from 'react-native';
import { PageShell } from '@/shared/ui/page-shell';
import { colors } from '@/shared/theme/colors';
import { ProgressCard } from '../components/progress-card';
import { TaskInput } from '../components/task-input';
import { TaskFilters } from '../components/task-filters';
import { TaskItem } from '../components/task-item';
import type { TaskFilter } from '../model/task';
import { useTasks } from '../state/task-provider';

export default function TaskListScreen() {
  const { tasks, add, toggle, remove } = useTasks();
  const [draft, setDraft] = useState('');
  const [filter, setFilter] = useState<TaskFilter>('전체');
  const visible = tasks.filter(task => filter === '전체' || task.done === (filter === '완료'));
  function addTask() {
    if (!draft.trim()) return;
    add(draft);
    setDraft('');
  }
  return <PageShell>
    <Text style={styles.eyebrow}>SMALL STEPS, EVERY DAY</Text>
    <View style={styles.header}>
      <Text style={styles.heading}>오늘의 할 일</Text>
      <Pressable accessibilityRole="link" onPress={() => router.push('/youtube')}><Text style={styles.link}>유튜브 열기 ›</Text></Pressable>
    </View>
    <Text style={styles.muted}>작은 실천으로 하루를 채워보세요.</Text>
    <ProgressCard completed={tasks.filter(task => task.done).length} total={tasks.length} />
    <TaskInput value={draft} onChange={setDraft} onAdd={addTask} />
    <TaskFilters value={filter} onChange={setFilter} />
    <FlatList data={visible} keyExtractor={task => String(task.id)} keyboardShouldPersistTaps="handled"
      contentContainerStyle={styles.list} ListEmptyComponent={<Text style={styles.empty}>표시할 할 일이 없어요.</Text>}
      renderItem={({ item }) => <TaskItem task={item} onToggle={() => toggle(item.id)} onDelete={() => remove(item.id)}
        onOpen={() => router.push({ pathname: '/tasks/[id]', params: { id: item.id } })} />} />
    <Text style={styles.footer}>한 번에 하나씩, 나만의 속도로.</Text>
  </PageShell>;
}
const styles = StyleSheet.create({
  header: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap' },
  link: { color: colors.accent, paddingVertical: 12 },
  eyebrow: { color: colors.accent, fontSize: 12, fontWeight: '700', letterSpacing: 2, marginTop: 24 },
  heading: { color: colors.ink, fontSize: 32, fontWeight: '800' }, muted: { color: colors.muted },
  list: { gap: 10, paddingBottom: 16 }, empty: { padding: 32, textAlign: 'center', color: colors.muted },
  footer: { textAlign: 'center', color: colors.muted, fontSize: 12 },
});

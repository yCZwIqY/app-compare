import { Pressable, StyleSheet, Text, View } from 'react-native';
import { colors } from '@/shared/theme/colors';
import type { Task } from '../model/task';

type Props = { task: Task; onToggle: () => void; onOpen: () => void; onDelete: () => void };
export function TaskItem({ task, onToggle, onOpen, onDelete }: Props) {
  return <View style={styles.card}>
    <Pressable accessibilityRole="checkbox" accessibilityLabel={`${task.title} 완료 전환`}
      accessibilityState={{ checked: task.done }} style={styles.toggle} onPress={onToggle}>
      <Text style={styles.check}>{task.done ? '✓' : '○'}</Text>
    </Pressable>
    <Pressable accessibilityRole="link" accessibilityLabel={`${task.title} 상세 보기`} style={styles.open} onPress={onOpen}>
      <Text style={[styles.title, task.done && styles.done]}>{task.title} ›</Text>
    </Pressable>
    <Pressable accessibilityRole="button" accessibilityLabel={`${task.title} 삭제`} onPress={onDelete}>
      <Text style={styles.remove}>삭제</Text>
    </Pressable>
  </View>;
}
const styles = StyleSheet.create({
  card: { flexDirection: 'row', alignItems: 'center', backgroundColor: colors.white, borderRadius: 14, padding: 12 },
  toggle: { justifyContent: 'center', padding: 8, minHeight: 48 },
  check: { fontSize: 26, color: colors.accent }, open: { flex: 1, paddingVertical: 14 },
  title: { fontSize: 16, color: colors.ink }, done: { textDecorationLine: 'line-through', color: colors.muted },
  remove: { padding: 12, color: colors.muted },
});

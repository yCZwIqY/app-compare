import { Pressable, StyleSheet, Text, View } from 'react-native';
import { colors } from '@/shared/theme/colors';
import { taskFilters, type TaskFilter } from '../model/task';

export function TaskFilters({ value, onChange }: { value: TaskFilter; onChange: (value: TaskFilter) => void }) {
  return <View style={styles.row}>{taskFilters.map(filter => (
    <Pressable key={filter} accessibilityRole="button" accessibilityState={{ selected: value === filter }}
      onPress={() => onChange(filter)} style={[styles.chip, value === filter && styles.selected]}>
      <Text style={{ color: value === filter ? colors.white : colors.muted }}>{filter}</Text>
    </Pressable>
  ))}</View>;
}
const styles = StyleSheet.create({
  row: { flexDirection: 'row', gap: 8 },
  chip: { backgroundColor: colors.chip, paddingHorizontal: 18, paddingVertical: 12, borderRadius: 24 },
  selected: { backgroundColor: colors.ink },
});

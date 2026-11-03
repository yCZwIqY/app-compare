import { StyleSheet, Text, View } from 'react-native';
import { colors } from '@/shared/theme/colors';

export function ProgressCard({ completed, total }: { completed: number; total: number }) {
  return <View style={styles.card}>
    <Text style={styles.label}>오늘의 진행률</Text>
    <Text style={styles.total}>{completed} / {total} 완료</Text>
    <View style={styles.track}><View style={[styles.progress, { width: `${total ? completed / total * 100 : 0}%` }]} /></View>
  </View>;
}
const styles = StyleSheet.create({
  card: { backgroundColor: colors.accent, padding: 24, borderRadius: 20, gap: 12 },
  label: { color: colors.white },
  total: { color: colors.white, fontSize: 24, fontWeight: '700' },
  track: { height: 6, backgroundColor: colors.track, borderRadius: 3, overflow: 'hidden' },
  progress: { height: 6, backgroundColor: colors.white },
});

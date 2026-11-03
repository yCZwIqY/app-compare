import { Pressable, StyleSheet, Text, TextInput, View } from 'react-native';
import { colors } from '@/shared/theme/colors';

type Props = { value: string; onChange: (value: string) => void; onAdd: () => void };
export function TaskInput({ value, onChange, onAdd }: Props) {
  return <View style={styles.row}>
    <TextInput accessibilityLabel="새 할 일" style={styles.input} placeholder="새 할 일을 입력하세요"
      value={value} onChangeText={onChange} onSubmitEditing={onAdd} maxLength={100} />
    <Pressable accessibilityRole="button" style={[styles.add, !value.trim() && styles.disabled]}
      disabled={!value.trim()} onPress={onAdd}><Text style={styles.label}>추가</Text></Pressable>
  </View>;
}
const styles = StyleSheet.create({
  row: { flexDirection: 'row', gap: 8 },
  input: { flex: 1, minWidth: 0, backgroundColor: colors.white, borderWidth: 1, borderColor: colors.border,
    borderRadius: 12, padding: 14, color: colors.ink },
  add: { justifyContent: 'center', backgroundColor: colors.accent, paddingHorizontal: 18, borderRadius: 12 },
  disabled: { opacity: 0.4 }, label: { color: colors.white },
});

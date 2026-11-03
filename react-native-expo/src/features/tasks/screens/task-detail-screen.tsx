import { router, useLocalSearchParams } from 'expo-router';
import { Pressable, ScrollView, StyleSheet, Text } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useTasks } from '../state/task-provider';

export default function TaskDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const { tasks, toggle, remove } = useTasks();
  const task = tasks.find(item => String(item.id) === id);
  function back() {
    if (router.canGoBack()) router.back();
    else router.replace('/');
  }
  return (
    <SafeAreaView style={styles.safe}>
      <ScrollView contentContainerStyle={styles.page}>
        <Pressable accessibilityRole="button" onPress={back} style={styles.back}>
          <Text style={styles.link}>‹ 목록으로</Text>
        </Pressable>
        <Text style={styles.eyebrow}>TASK DETAIL</Text>
        <Text style={styles.heading}>할 일 상세</Text>
        {task ? <>
          <Text style={styles.title}>{task.title}</Text>
          <Text style={styles.status}>상태: {task.done ? '완료' : '진행 중'}</Text>
          <Pressable accessibilityRole="button" style={styles.button} onPress={() => toggle(task.id)}>
            <Text style={styles.white}>{task.done ? '진행 중으로 변경' : '완료로 변경'}</Text>
          </Pressable>
          <Pressable accessibilityRole="button" style={styles.back} onPress={() => { remove(task.id); back(); }}>
            <Text style={styles.danger}>할 일 삭제</Text>
          </Pressable>
        </> : <Text style={styles.status}>할 일을 찾을 수 없어요.</Text>}
      </ScrollView>
    </SafeAreaView>
  );
}
const styles = StyleSheet.create({
  safe: { flex: 1, backgroundColor: '#F5F7FB' },
  page: { width: '100%', maxWidth: 600, alignSelf: 'center', padding: 24, gap: 20 },
  back: { paddingVertical: 14 },
  link: { color: '#5265D9', fontSize: 16 },
  eyebrow: { color: '#5265D9', letterSpacing: 2, fontSize: 12 },
  heading: { color: '#17213A', fontSize: 32, fontWeight: '800' },
  title: { color: '#17213A', fontSize: 24, fontWeight: '600' },
  status: { color: '#747D8C', fontSize: 16 },
  button: { padding: 18, borderRadius: 12, backgroundColor: '#5265D9', alignItems: 'center' },
  white: { color: '#FFFFFF', fontWeight: '700' },
  danger: { color: '#C03945', textAlign: 'center' },
});

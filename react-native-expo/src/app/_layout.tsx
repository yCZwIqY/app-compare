import { Stack } from 'expo-router';
import { TaskProvider } from '@/features/tasks/state/task-provider';

export default function RootLayout() {
  return (
    <TaskProvider>
      <Stack screenOptions={{ headerShown: false }}>
        <Stack.Screen name="index" />
        <Stack.Screen name="tasks/[id]" />
      </Stack>
    </TaskProvider>
  );
}

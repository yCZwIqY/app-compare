import type { PropsWithChildren } from 'react';
import { StyleSheet, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { colors } from '../theme/colors';

export function PageShell({ children }: PropsWithChildren) {
  return <SafeAreaView style={styles.safe}><View style={styles.page}>{children}</View></SafeAreaView>;
}
const styles = StyleSheet.create({
  safe: { flex: 1, backgroundColor: colors.background },
  page: { flex: 1, width: '100%', maxWidth: 600, alignSelf: 'center', padding: 24, gap: 16 },
});

import { Linking, Pressable, StyleSheet, Text, View } from 'react-native';
import { colors } from '@/shared/theme/colors';

// YouTube's full website cannot be used as a cross-origin iframe.
export function YoutubeBrowser() {
  return <View style={styles.panel}>
    <Text style={styles.title}>YouTube</Text>
    <Text>브라우저에서 유튜브를 열어보세요.</Text>
    <Pressable accessibilityRole="link" onPress={() => Linking.openURL('https://www.youtube.com')} style={styles.button}>
      <Text style={styles.label}>브라우저에서 열기 ↗</Text>
    </Pressable>
  </View>;
}
const styles = StyleSheet.create({
  panel: { flex: 1, justifyContent: 'center', alignItems: 'center', gap: 24 },
  title: { fontSize: 36, fontWeight: '800', color: colors.danger },
  button: { backgroundColor: colors.accent, padding: 18, borderRadius: 12 }, label: { color: colors.white },
});

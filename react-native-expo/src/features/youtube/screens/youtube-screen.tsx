import { router } from 'expo-router';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { PageShell } from '@/shared/ui/page-shell';
import { colors } from '@/shared/theme/colors';
import { YoutubeBrowser } from '../components/youtube-browser';

export default function YoutubeScreen() {
  return <PageShell>
    <View style={styles.header}>
      <Pressable accessibilityRole="button" onPress={() => router.canGoBack() ? router.back() : router.replace('/')}>
        <Text style={styles.back}>‹ 목록으로</Text>
      </Pressable>
      <Text style={styles.title}>유튜브</Text>
    </View>
    <YoutubeBrowser />
  </PageShell>;
}
const styles = StyleSheet.create({
  header: { flexDirection: 'row', alignItems: 'center', gap: 24, paddingVertical: 8 },
  back: { color: colors.accent, paddingVertical: 8 }, title: { fontSize: 24, fontWeight: '700', color: colors.ink },
});

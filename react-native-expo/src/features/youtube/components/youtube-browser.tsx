import { useRef, useState } from 'react';
import { ActivityIndicator, Pressable, StyleSheet, Text, View } from 'react-native';
import { WebView } from 'react-native-webview';
import { colors } from '@/shared/theme/colors';

export function YoutubeBrowser() {
  const browser = useRef<WebView<object>>(null);
  const [loading, setLoading] = useState(true);
  const [canGoBack, setCanGoBack] = useState(false);
  const [error, setError] = useState(false);
  return <View style={styles.container}>
    <View style={styles.toolbar}>
      <Pressable accessibilityRole="button" disabled={!canGoBack} onPress={() => browser.current?.goBack()}>
        <Text style={{ color: canGoBack ? colors.accent : colors.muted }}>웹 뒤로</Text>
      </Pressable>
      <Text style={styles.address}>m.youtube.com</Text>
      <Pressable accessibilityRole="button" onPress={() => { setError(false); browser.current?.reload(); }}>
        <Text style={styles.action}>새로고침</Text>
      </Pressable>
    </View>
    {loading && <ActivityIndicator accessibilityLabel="유튜브 로딩 중" color={colors.accent} />}
    {error && <Text style={styles.error}>유튜브를 불러오지 못했어요. 연결을 확인하고 새로고침해 주세요.</Text>}
    <WebView<object> ref={browser} source={{ uri: 'https://m.youtube.com' }} style={styles.container}
      javaScriptEnabled domStorageEnabled allowsInlineMediaPlayback
      onNavigationStateChange={state => setCanGoBack(state.canGoBack)}
      onLoadStart={() => { setLoading(true); setError(false); }} onLoadEnd={() => setLoading(false)}
      onError={() => { setError(true); setLoading(false); }}
      onShouldStartLoadWithRequest={request => request.url.startsWith('https://') || request.url === 'about:blank'} />
  </View>;
}
const styles = StyleSheet.create({
  container: { flex: 1 }, toolbar: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', paddingVertical: 14 },
  address: { color: colors.muted }, action: { color: colors.accent }, error: { color: colors.danger, padding: 16 },
});

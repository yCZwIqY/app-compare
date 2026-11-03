import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:webview_flutter/webview_flutter.dart';

class YoutubeBrowser extends StatefulWidget {
  const YoutubeBrowser({super.key});
  @override
  State<YoutubeBrowser> createState() => _YoutubeBrowserState();
}

class _YoutubeBrowserState extends State<YoutubeBrowser> {
  WebViewController? controller;
  bool loading = true;
  bool canGoBack = false;
  String? error;
  bool get supported =>
      !kIsWeb &&
      [
        TargetPlatform.android,
        TargetPlatform.iOS,
        TargetPlatform.macOS,
      ].contains(defaultTargetPlatform);
  @override
  void initState() {
    super.initState();
    if (!supported) return;
    controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (_) {
            if (mounted) {
              setState(() {
                loading = true;
                error = null;
              });
            }
          },
          onPageFinished: (_) async {
            final back = await controller!.canGoBack();
            if (mounted) {
              setState(() {
                loading = false;
                canGoBack = back;
              });
            }
          },
          onWebResourceError: (failure) {
            if (failure.isForMainFrame == true && mounted) {
              setState(() {
                error = '유튜브를 불러오지 못했어요. 새로고침해 주세요.';
                loading = false;
              });
            }
          },
          onNavigationRequest: (request) =>
              request.url.startsWith('https://') || request.url == 'about:blank'
              ? NavigationDecision.navigate
              : NavigationDecision.prevent,
        ),
      )
      ..loadRequest(Uri.parse('https://m.youtube.com'));
  }

  Future<void> openBrowser() async {
    try {
      final opened = await launchUrl(
        Uri.parse('https://www.youtube.com'),
        mode: LaunchMode.externalApplication,
      );
      if (!opened && mounted) setState(() => error = '브라우저를 열지 못했어요.');
    } catch (_) {
      if (mounted) setState(() => error = '브라우저를 열지 못했어요.');
    }
  }

  @override
  Widget build(BuildContext context) {
    if (controller == null) {
      return Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Text(
              'YouTube',
              style: TextStyle(
                fontSize: 36,
                fontWeight: FontWeight.bold,
                color: Colors.red,
              ),
            ),
            const SizedBox(height: 24),
            const Text('브라우저에서 유튜브를 열어보세요.'),
            const SizedBox(height: 24),
            FilledButton(
              onPressed: openBrowser,
              child: const Text('브라우저에서 열기 ↗'),
            ),
            if (error != null) Text(error!),
          ],
        ),
      );
    }
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            TextButton(
              onPressed: canGoBack ? () => controller!.goBack() : null,
              child: const Text('웹 뒤로'),
            ),
            const Text('m.youtube.com'),
            TextButton(
              onPressed: () => controller!.reload(),
              child: const Text('새로고침'),
            ),
          ],
        ),
        if (loading) const LinearProgressIndicator(),
        if (error != null)
          Padding(padding: const EdgeInsets.all(16), child: Text(error!)),
        Expanded(child: WebViewWidget(controller: controller!)),
      ],
    );
  }
}

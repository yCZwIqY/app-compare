import 'package:flutter/material.dart';

import '../widgets/youtube_browser.dart';

class YoutubePage extends StatelessWidget {
  const YoutubePage({super.key});
  @override
  Widget build(BuildContext context) => Scaffold(
    appBar: AppBar(title: const Text('유튜브')),
    body: const SafeArea(child: YoutubeBrowser()),
  );
}

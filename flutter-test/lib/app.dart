import 'package:flutter/material.dart';

import 'core/theme/app_theme.dart';
import 'features/tasks/pages/task_list_page.dart';

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
    title: '오늘의 할 일',
    debugShowCheckedModeBanner: false,
    theme: appTheme(),
    home: const TaskPage(),
  );
}

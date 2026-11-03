import 'package:flutter/material.dart';

import '../features/tasks/controllers/task_controller.dart';
import '../features/tasks/models/task.dart';
import '../features/tasks/pages/task_detail_page.dart';

class TaskRoutes {
  static Future<void> openDetail(
    BuildContext context,
    Task task,
    TaskController controller,
  ) => Navigator.of(context).push<void>(
    MaterialPageRoute(
      settings: RouteSettings(name: '/tasks/${task.id}'),
      builder: (_) => TaskDetailPage(task: task, controller: controller),
    ),
  );
}

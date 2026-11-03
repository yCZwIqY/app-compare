import 'package:flutter/material.dart';

import '../controllers/task_controller.dart';
import '../models/task.dart';

class TaskDetailPage extends StatelessWidget {
  const TaskDetailPage({
    super.key,
    required this.task,
    required this.controller,
  });
  final Task task;
  final TaskController controller;
  @override
  Widget build(BuildContext context) => ListenableBuilder(
    listenable: controller,
    builder: (context, _) => Scaffold(
      appBar: AppBar(title: const Text('할 일 상세')),
      body: Center(
        child: ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 600),
          child: ListView(
            padding: const EdgeInsets.all(24),
            children: [
              Text(
                task.title,
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 20),
              Text('상태: ${task.done ? '완료' : '진행 중'}'),
              const SizedBox(height: 24),
              FilledButton(
                onPressed: () => controller.toggle(task),
                child: Text(task.done ? '진행 중으로 변경' : '완료로 변경'),
              ),
              const SizedBox(height: 12),
              TextButton(
                onPressed: () {
                  controller.remove(task);
                  Navigator.of(context).pop();
                },
                child: const Text(
                  '할 일 삭제',
                  style: TextStyle(color: Colors.red),
                ),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop(),
                child: const Text('목록으로'),
              ),
            ],
          ),
        ),
      ),
    ),
  );
}

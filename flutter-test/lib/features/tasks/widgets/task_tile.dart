import 'package:flutter/material.dart';

import '../models/task.dart';

class TaskTile extends StatelessWidget {
  const TaskTile({
    super.key,
    required this.task,
    required this.onToggle,
    required this.onOpen,
    required this.onDelete,
  });
  final Task task;
  final VoidCallback onToggle;
  final VoidCallback onOpen;
  final VoidCallback onDelete;
  @override
  Widget build(BuildContext context) => Material(
    color: Colors.white,
    borderRadius: BorderRadius.circular(14),
    child: ListTile(
      leading: Checkbox(
        value: task.done,
        semanticLabel: task.title,
        onChanged: (_) => onToggle(),
      ),
      title: Text(
        task.title,
        style: TextStyle(
          decoration: task.done ? TextDecoration.lineThrough : null,
          color: task.done ? Colors.blueGrey : const Color(0xFF17213A),
        ),
      ),
      subtitle: const Text('상세 보기 ›'),
      onTap: onOpen,
      trailing: IconButton(
        tooltip: '${task.title} 삭제',
        icon: const Icon(Icons.close),
        onPressed: onDelete,
      ),
    ),
  );
}

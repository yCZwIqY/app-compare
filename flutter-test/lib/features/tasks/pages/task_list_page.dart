import 'package:flutter/material.dart';

import '../../../core/theme/app_theme.dart';
import '../../../navigation/task_routes.dart';
import '../controllers/task_controller.dart';
import '../widgets/progress_card.dart';
import '../widgets/task_filters.dart';
import '../widgets/task_input.dart';
import '../widgets/task_tile.dart';

class TaskPage extends StatefulWidget {
  const TaskPage({super.key});
  @override
  State<TaskPage> createState() => _TaskPageState();
}

class _TaskPageState extends State<TaskPage> {
  final input = TextEditingController();
  final tasks = TaskController();
  void add() {
    if (input.text.trim().isEmpty) return;
    tasks.add(input.text);
    input.clear();
    setState(() {});
  }

  @override
  void dispose() {
    input.dispose();
    tasks.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => ListenableBuilder(
    listenable: tasks,
    builder: (context, _) {
      final visible = tasks.visible;
      return Scaffold(
        body: SafeArea(
          child: Center(
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 600),
              child: Padding(
                padding: const EdgeInsets.all(24),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    const SizedBox(height: 24),
                    const Text(
                      'SMALL STEPS, EVERY DAY',
                      style: TextStyle(
                        color: accent,
                        fontSize: 12,
                        fontWeight: FontWeight.bold,
                        letterSpacing: 2,
                      ),
                    ),
                    const SizedBox(height: 16),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Flexible(
                          child: Text(
                            '오늘의 할 일',
                            style: TextStyle(
                              color: Color(0xFF17213A),
                              fontSize: 32,
                              fontWeight: FontWeight.w800,
                            ),
                          ),
                        ),
                        TextButton(
                          onPressed: () => TaskRoutes.openYoutube(context),
                          child: const Text('유튜브 열기 ›'),
                        ),
                      ],
                    ),
                    const SizedBox(height: 12),
                    const Text(
                      '작은 실천으로 하루를 채워보세요.',
                      style: TextStyle(color: Colors.blueGrey),
                    ),
                    const SizedBox(height: 20),
                    ProgressCard(
                      completed: tasks.completed,
                      total: tasks.tasks.length,
                    ),
                    const SizedBox(height: 16),
                    TaskInput(
                      controller: input,
                      onChanged: () => setState(() {}),
                      onAdd: add,
                    ),
                    const SizedBox(height: 16),
                    TaskFilters(
                      value: tasks.filter,
                      onChanged: tasks.setFilter,
                    ),
                    const SizedBox(height: 16),
                    Expanded(
                      child: visible.isEmpty
                          ? const Center(child: Text('표시할 할 일이 없어요.'))
                          : ListView.separated(
                              itemCount: visible.length,
                              separatorBuilder: (_, index) =>
                                  const SizedBox(height: 10),
                              itemBuilder: (context, index) {
                                final task = visible[index];
                                return TaskTile(
                                  task: task,
                                  onToggle: () => tasks.toggle(task),
                                  onDelete: () => tasks.remove(task),
                                  onOpen: () => TaskRoutes.openDetail(
                                    context,
                                    task,
                                    tasks,
                                  ),
                                );
                              },
                            ),
                    ),
                    const SizedBox(height: 12),
                    const Text(
                      '한 번에 하나씩, 나만의 속도로.',
                      textAlign: TextAlign.center,
                      style: TextStyle(color: Colors.blueGrey, fontSize: 12),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
      );
    },
  );
}

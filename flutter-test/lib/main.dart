import 'package:flutter/material.dart';

void main() => runApp(const MyApp());
const accent = Color(0xFF5265D9);

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) => MaterialApp(
    title: '오늘의 할 일',
    debugShowCheckedModeBanner: false,
    theme: ThemeData(
      colorScheme: ColorScheme.fromSeed(seedColor: accent),
      scaffoldBackgroundColor: const Color(0xFFF5F7FB),
      useMaterial3: true,
    ),
    home: const TaskPage(),
  );
}

class Task {
  Task(this.id, this.title, [this.done = false]);
  final int id;
  final String title;
  bool done;
}

class TaskPage extends StatefulWidget {
  const TaskPage({super.key});
  @override
  State<TaskPage> createState() => _TaskPageState();
}

class _TaskPageState extends State<TaskPage> {
  final controller = TextEditingController();
  final tasks = [
    Task(1, '물 한 잔 마시기', true),
    Task(2, '20분 산책하기'),
    Task(3, '새로운 아이디어 기록하기'),
  ];
  int nextId = 4;
  String filter = '전체';
  Future<void> openTask(Task task) async {
    final deleted = await Navigator.of(context).push<bool>(
      MaterialPageRoute(
        settings: RouteSettings(name: '/tasks/${task.id}'),
        builder: (_) => TaskDetailPage(task: task),
      ),
    );
    if (!mounted) return;
    setState(() {
      if (deleted == true) tasks.removeWhere((item) => item.id == task.id);
    });
  }

  void add() {
    if (controller.text.trim().isEmpty) return;
    setState(() {
      tasks.add(Task(nextId++, controller.text.trim()));
      controller.clear();
    });
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final completed = tasks.where((t) => t.done).length;
    final visible = tasks
        .where((t) => filter == '전체' || t.done == (filter == '완료'))
        .toList();
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
                  const Text(
                    '오늘의 할 일',
                    style: TextStyle(
                      color: Color(0xFF17213A),
                      fontSize: 32,
                      fontWeight: FontWeight.w800,
                    ),
                  ),
                  const SizedBox(height: 12),
                  const Text(
                    '작은 실천으로 하루를 채워보세요.',
                    style: TextStyle(color: Colors.blueGrey),
                  ),
                  const SizedBox(height: 20),
                  Container(
                    padding: const EdgeInsets.all(24),
                    decoration: BoxDecoration(
                      color: accent,
                      borderRadius: BorderRadius.circular(20),
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        const Text(
                          '오늘의 진행률',
                          style: TextStyle(color: Colors.white),
                        ),
                        const SizedBox(height: 12),
                        Text(
                          '$completed / ${tasks.length} 완료',
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 12),
                        LinearProgressIndicator(
                          value: tasks.isEmpty ? 0 : completed / tasks.length,
                          color: Colors.white,
                          backgroundColor: const Color(0xFF8997EB),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 16),
                  Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: controller,
                          maxLength: 100,
                          onChanged: (_) => setState(() {}),
                          onSubmitted: (_) => add(),
                          decoration: InputDecoration(
                            labelText: '새 할 일',
                            hintText: '새 할 일을 입력하세요',
                            counterText: '',
                            filled: true,
                            fillColor: Colors.white,
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(12),
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(width: 10),
                      FilledButton(
                        onPressed: controller.text.trim().isEmpty ? null : add,
                        child: const Text('추가'),
                      ),
                    ],
                  ),
                  const SizedBox(height: 16),
                  Wrap(
                    spacing: 8,
                    children: ['전체', '진행 중', '완료']
                        .map(
                          (f) => ChoiceChip(
                            label: Text(f),
                            selected: filter == f,
                            onSelected: (_) => setState(() => filter = f),
                          ),
                        )
                        .toList(),
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
                              return Material(
                                color: Colors.white,
                                borderRadius: BorderRadius.circular(14),
                                child: ListTile(
                                  leading: Checkbox(
                                    value: task.done,
                                    semanticLabel: task.title,
                                    onChanged: (_) =>
                                        setState(() => task.done = !task.done),
                                  ),
                                  title: Text(
                                    task.title,
                                    style: TextStyle(
                                      decoration: task.done
                                          ? TextDecoration.lineThrough
                                          : null,
                                      color: task.done
                                          ? Colors.blueGrey
                                          : const Color(0xFF17213A),
                                    ),
                                  ),
                                  subtitle: const Text('상세 보기 ›'),
                                  onTap: () => openTask(task),
                                  trailing: IconButton(
                                    tooltip: '${task.title} 삭제',
                                    icon: const Icon(Icons.close),
                                    onPressed: () => setState(
                                      () => tasks.removeWhere(
                                        (t) => t.id == task.id,
                                      ),
                                    ),
                                  ),
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
  }
}

class TaskDetailPage extends StatefulWidget {
  const TaskDetailPage({super.key, required this.task});
  final Task task;
  @override
  State<TaskDetailPage> createState() => _TaskDetailPageState();
}

class _TaskDetailPageState extends State<TaskDetailPage> {
  @override
  Widget build(BuildContext context) => Scaffold(
    appBar: AppBar(title: const Text('할 일 상세')),
    body: Center(
      child: ConstrainedBox(
        constraints: const BoxConstraints(maxWidth: 600),
        child: ListView(
          padding: const EdgeInsets.all(24),
          children: [
            Text(
              widget.task.title,
              style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 20),
            Text('상태: ${widget.task.done ? '완료' : '진행 중'}'),
            const SizedBox(height: 24),
            FilledButton(
              onPressed: () =>
                  setState(() => widget.task.done = !widget.task.done),
              child: Text(widget.task.done ? '진행 중으로 변경' : '완료로 변경'),
            ),
            const SizedBox(height: 12),
            TextButton(
              onPressed: () => Navigator.of(context).pop(true),
              child: const Text('할 일 삭제', style: TextStyle(color: Colors.red)),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('목록으로'),
            ),
          ],
        ),
      ),
    ),
  );
}

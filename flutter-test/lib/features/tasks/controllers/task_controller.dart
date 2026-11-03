import 'package:flutter/foundation.dart';

import '../models/task.dart';

class TaskController extends ChangeNotifier {
  final _tasks = [
    Task(1, '물 한 잔 마시기', true),
    Task(2, '20분 산책하기'),
    Task(3, '새로운 아이디어 기록하기'),
  ];
  int _nextId = 4;
  String _filter = '전체';
  List<Task> get tasks => List.unmodifiable(_tasks);
  String get filter => _filter;
  int get completed => _tasks.where((task) => task.done).length;
  List<Task> get visible => _tasks
      .where((task) => _filter == '전체' || task.done == (_filter == '완료'))
      .toList();
  void setFilter(String value) {
    _filter = value;
    notifyListeners();
  }

  void add(String draft) {
    final title = draft.trim();
    if (title.isEmpty) return;
    _tasks.add(
      Task(_nextId++, title.length > 100 ? title.substring(0, 100) : title),
    );
    notifyListeners();
  }

  void toggle(Task task) {
    task.done = !task.done;
    notifyListeners();
  }

  void remove(Task task) {
    _tasks.removeWhere((item) => item.id == task.id);
    notifyListeners();
  }
}

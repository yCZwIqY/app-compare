import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_test_1102/features/tasks/controllers/task_controller.dart';

void main() {
  test('controller owns filtering, updates and unique IDs after deletion', () {
    final controller = TaskController();
    addTearDown(controller.dispose);
    controller.add('   ');
    expect(controller.tasks.length, 3);
    controller.add('  새 할 일  ');
    final added = controller.tasks.last;
    expect(added.title, '새 할 일');
    controller.toggle(added);
    controller.setFilter('완료');
    expect(controller.visible.map((task) => task.id), [1, 4]);
    expect(controller.completed, 2);
    controller.remove(added);
    controller.add('다음 할 일');
    expect(controller.tasks.last.id, 5);
    expect(controller.visible.map((task) => task.id), [1]);
    controller.add('가' * 101);
    expect(controller.tasks.last.title.length, 100);
  });
}

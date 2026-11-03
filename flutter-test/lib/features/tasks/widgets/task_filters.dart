import 'package:flutter/material.dart';

class TaskFilters extends StatelessWidget {
  const TaskFilters({super.key, required this.value, required this.onChanged});
  final String value;
  final ValueChanged<String> onChanged;
  @override
  Widget build(BuildContext context) => Wrap(
    spacing: 8,
    children: ['전체', '진행 중', '완료']
        .map(
          (filter) => ChoiceChip(
            label: Text(filter),
            selected: value == filter,
            onSelected: (_) => onChanged(filter),
          ),
        )
        .toList(),
  );
}

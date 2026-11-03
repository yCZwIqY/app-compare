import 'package:flutter/material.dart';

class TaskInput extends StatelessWidget {
  const TaskInput({
    super.key,
    required this.controller,
    required this.onChanged,
    required this.onAdd,
  });
  final TextEditingController controller;
  final VoidCallback onChanged;
  final VoidCallback onAdd;
  @override
  Widget build(BuildContext context) => Row(
    children: [
      Expanded(
        child: TextField(
          controller: controller,
          maxLength: 100,
          onChanged: (_) => onChanged(),
          onSubmitted: (_) => onAdd(),
          decoration: InputDecoration(
            labelText: '새 할 일',
            hintText: '새 할 일을 입력하세요',
            counterText: '',
            filled: true,
            fillColor: Colors.white,
            border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
          ),
        ),
      ),
      const SizedBox(width: 10),
      FilledButton(
        onPressed: controller.text.trim().isEmpty ? null : onAdd,
        child: const Text('추가'),
      ),
    ],
  );
}

import 'package:flutter/material.dart';

import '../../../core/theme/app_theme.dart';

class ProgressCard extends StatelessWidget {
  const ProgressCard({super.key, required this.completed, required this.total});
  final int completed;
  final int total;
  @override
  Widget build(BuildContext context) => Container(
    padding: const EdgeInsets.all(24),
    decoration: BoxDecoration(
      color: accent,
      borderRadius: BorderRadius.circular(20),
    ),
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        const Text('오늘의 진행률', style: TextStyle(color: Colors.white)),
        const SizedBox(height: 12),
        Text(
          '$completed / $total 완료',
          style: const TextStyle(
            color: Colors.white,
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 12),
        LinearProgressIndicator(
          value: total == 0 ? 0 : completed / total,
          color: Colors.white,
          backgroundColor: const Color(0xFF8997EB),
        ),
      ],
    ),
  );
}

import 'package:flutter/material.dart';

const accent = Color(0xFF5265D9);
ThemeData appTheme() => ThemeData(
  colorScheme: ColorScheme.fromSeed(seedColor: accent),
  scaffoldBackgroundColor: const Color(0xFFF5F7FB),
  useMaterial3: true,
);

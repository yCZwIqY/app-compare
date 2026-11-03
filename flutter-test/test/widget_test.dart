import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_test_1102/app.dart';

void main() {
  testWidgets('youtube route opens and returns without losing the draft', (
    tester,
  ) async {
    debugDefaultTargetPlatformOverride = TargetPlatform.windows;
    addTearDown(() => debugDefaultTargetPlatformOverride = null);
    await tester.pumpWidget(const MyApp());
    await tester.enterText(find.byType(TextField), '유지할 내용');
    await tester.tap(find.text('유튜브 열기 ›'));
    await tester.pumpAndSettle();
    expect(find.text('유튜브'), findsOneWidget);
    expect(find.text('브라우저에서 열기 ↗'), findsOneWidget);
    await tester.pageBack();
    await tester.pumpAndSettle();
    expect(find.text('유지할 내용'), findsOneWidget);
    debugDefaultTargetPlatformOverride = null;
  });
  testWidgets('back preserves list filter and draft', (tester) async {
    await tester.pumpWidget(const MyApp());
    await tester.enterText(find.byType(TextField), '작성 중인 내용');
    await tester.tap(find.widgetWithText(ChoiceChip, '진행 중'));
    await tester.pumpAndSettle();
    await tester.tap(find.text('20분 산책하기'));
    await tester.pumpAndSettle();
    expect(find.text('할 일 상세'), findsOneWidget);
    await tester.pageBack();
    await tester.pumpAndSettle();
    expect(
      tester
          .widget<ChoiceChip>(find.widgetWithText(ChoiceChip, '진행 중'))
          .selected,
      isTrue,
    );
    expect(find.text('작성 중인 내용'), findsOneWidget);
    expect(find.text('물 한 잔 마시기'), findsNothing);
  });
  testWidgets('add toggle filter delete and empty state', (tester) async {
    await tester.pumpWidget(const MyApp());
    expect(find.text('1 / 3 완료'), findsOneWidget);
    await tester.enterText(find.byType(TextField), '   ');
    expect(
      tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
      isNull,
    );
    await tester.enterText(find.byType(TextField), '테스트 할 일');
    await tester.pump();
    await tester.tap(find.text('추가'));
    await tester.pumpAndSettle();
    expect(find.text('1 / 4 완료'), findsOneWidget);
    await tester.scrollUntilVisible(
      find.text('테스트 할 일'),
      100,
      scrollable: find.byType(Scrollable).last,
    );
    await tester.tap(find.text('테스트 할 일'));
    await tester.pumpAndSettle();
    expect(find.text('할 일 상세'), findsOneWidget);
    expect(find.text('상태: 진행 중'), findsOneWidget);
    await tester.tap(find.text('완료로 변경'));
    await tester.pumpAndSettle();
    expect(find.text('상태: 완료'), findsOneWidget);
    await tester.tap(find.text('목록으로'));
    await tester.pumpAndSettle();
    expect(find.text('2 / 4 완료'), findsOneWidget);
    await tester.tap(find.widgetWithText(ChoiceChip, '완료'));
    await tester.pumpAndSettle();
    expect(find.text('20분 산책하기'), findsNothing);
    await tester.tap(find.text('테스트 할 일'));
    await tester.pumpAndSettle();
    await tester.tap(find.text('할 일 삭제'));
    await tester.pumpAndSettle();
    expect(find.text('1 / 3 완료'), findsOneWidget);
    await tester.tap(find.byTooltip('물 한 잔 마시기 삭제'));
    await tester.pumpAndSettle();
    expect(find.text('표시할 할 일이 없어요.'), findsOneWidget);
    expect(find.text('0 / 2 완료'), findsOneWidget);
  });
}

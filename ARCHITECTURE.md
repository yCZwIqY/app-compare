# 예제 앱 파일 구조 읽는 법

세 앱 모두 기능별로 폴더를 묶고, 그 안에서 화면·컴포넌트·상태·모델을 구분합니다.
이 문서의 구조는 이 작은 앱에 맞춘 예시이며, 모든 프로젝트에 동일하게 적용해야 하는 규칙은 아닙니다.
서버가 없으므로 repository, API service, DI 프레임워크는 추가하지 않았습니다.

## 같은 역할을 프레임워크별로 비교하기

| 역할 | React Native | Flutter | KMP / Compose |
| --- | --- | --- | --- |
| 시작 / 앱 조립 | `app/_layout.tsx` | `main.dart`, `app.dart` | `App.kt` |
| 라우팅 | `app/` 파일 경로 | `navigation/task_routes.dart` | `navigation/AppNavHost.kt` |
| 페이지 전체 UI | `screens/` | `pages/` | `screens/` |
| 작은 UI 조각 | `components/` | `widgets/` | `components/` |
| 데이터와 변경 로직 | Context Provider | ChangeNotifier Controller | Compose State Holder |
| 데이터 형태 | TypeScript `Task` | Dart `Task` | Kotlin `Task` |
| 공통 스타일 | `shared/theme/` | `core/theme/` | `core/theme/` |

상태 소유자가 데이터를 보관하고, 화면이 그 데이터를 컴포넌트에 내려줍니다.
컴포넌트는 사용자 입력을 콜백으로 전달합니다. 작은 컴포넌트가 전역 상태나 라우터를 직접 참조하지 않도록 했습니다.

## React Native / Expo

기준 폴더: `react-native-expo/src/`

```text
app/                            # Expo Router가 읽는 URL과 앱 조립
  _layout.tsx                   # TaskProvider + Stack
  index.tsx                     # 목록 화면을 내보내는 얇은 라우트
  tasks/[id].tsx                # 상세 화면을 내보내는 동적 라우트
features/tasks/
  model/task.ts                 # Task 타입, 필터, 초기 데이터
  state/task-provider.tsx       # 목록 데이터, 추가/완료/삭제, useTasks
  screens/
    task-list-screen.tsx        # 페이지 조합, 입력/필터 상태, 상세로 이동
    task-detail-screen.tsx      # 상세 UI, ID 조회, 뒤로/삭제 처리
  components/
    progress-card.tsx
    task-input.tsx
    task-filters.tsx
    task-item.tsx
shared/
  theme/colors.ts               # 앱 공통 색상
  ui/page-shell.tsx             # SafeArea와 최대 너비, 페이지 여백
```

읽는 순서: `_layout.tsx` → `app/index.tsx` → `task-list-screen.tsx` → `task-item.tsx` → `task-provider.tsx`.

예를 들어 완료 버튼을 누르면 `TaskItem.onToggle` → 화면의 `toggle(id)` → Provider의 데이터 변경 → 화면 재렌더링 순서로 동작합니다.
목록과 상세는 같은 Provider를 사용하고, 입력 중인 문자열과 필터는 목록 화면이 소유합니다.
`app/` 안에는 라우트만 놓습니다. 기존 Expo 스타터의 보조 파일은 별도로 남아 있으며, 할 일 기능 구현은 `features/tasks/`에 모았습니다.

Expo Router에서 레이아웃과 Provider를 조립하는 방식은 [공식 레이아웃 문서](https://docs.expo.dev/router/basics/navigation-layouts/)를 참고하세요.

## Flutter

기준 폴더: `flutter-test/lib/`

```text
main.dart                       # runApp만 호출하는 시작점
app.dart                        # MaterialApp, 테마, 첫 화면 조립
core/theme/app_theme.dart       # 공통 테마와 색상
navigation/task_routes.dart     # 상세 MaterialPageRoute 생성
features/tasks/
  models/task.dart              # Task 데이터 형태
  controllers/task_controller.dart # ChangeNotifier로 목록과 필터 관리
  pages/
    task_list_page.dart         # Controller 생성/해제, 목록 UI 조합
    task_detail_page.dart       # 전달받은 Controller로 상세 표시/변경
  widgets/
    progress_card.dart
    task_input.dart
    task_filters.dart
    task_tile.dart
```

읽는 순서: `main.dart` → `app.dart` → `task_list_page.dart` → `task_controller.dart` → `task_tile.dart`.

목록 페이지가 Controller를 생성하고 dispose합니다. 상세 페이지에는 같은 인스턴스를 전달하므로 새 데이터 복사본이 생기지 않습니다.
`ListenableBuilder`가 Controller를 구독하고, `notifyListeners()`가 호출되면 화면이 다시 그려집니다.
`TextEditingController`는 목록 페이지가 생성/해제합니다. 위젯은 데이터와 콜백만 받습니다.
상세에서 삭제하면 Controller를 갱신하고 `Navigator.pop()`으로 목록에 돌아갑니다.

작은 예제를 위해 ChangeNotifier 기반 Controller를 사용했습니다. 규모가 커지면 저장소 접근을 Repository로 분리하고 화면 상태를 ViewModel로 관리할 수 있습니다.
UI와 데이터의 책임 분리는 [Flutter 공식 아키텍처 가이드](https://docs.flutter.dev/app-architecture/guide)를 참고하세요.

## Kotlin Multiplatform / Compose

기준 폴더: `kmptest/shared/src/commonMain/kotlin/com/example/kmp_test/`

```text
App.kt                          # 상태 생성, 테마와 NavHost 조립
core/theme/DailyTheme.kt         # 공통 색상과 MaterialTheme
navigation/AppNavHost.kt         # 경로, 인자 파싱, 화면 이동/뒤로 가기
features/tasks/
  model/Task.kt                 # Task, TaskFilter, 초기 데이터
  state/TasksState.kt            # observable 상태와 추가/완료/삭제 처리
  screens/
    TaskListScreen.kt           # 목록 페이지, 컴포넌트 조합
    TaskDetailScreen.kt         # 상세 UI, 이벤트는 콜백으로 전달
  components/
    ProgressCard.kt
    TaskInput.kt
    TaskFilters.kt
    TaskItem.kt
```

읽는 순서: `App.kt` → `AppNavHost.kt` → `TaskListScreen.kt` → `TasksState.kt` → `TaskItem.kt`.

`App`이 `remember { TasksState() }`로 상태를 소유하므로 목록과 상세가 같은 데이터를 사용합니다.
`TasksState`는 `mutableStateOf`로 관찰 가능한 값을 제공하고 변경 메서드로 갱신합니다.
컴포넌트는 Compose 함수이며, 필요한 값과 이벤트 콜백만 받습니다. `TaskItem`은 NavController를 모릅니다.
화면 이동은 `AppNavHost`로 모았고, `TaskDetailScreen`은 라우터 없이도 UI를 구성할 수 있습니다.

이 예제는 간단한 State Holder이며 Lifecycle ViewModel을 사용하지 않습니다. 상태 수명은 App의 Composition에 따릅니다.
프로세스 종료나 Android 구성 변경을 넘는 보존, DB 저장은 별도 설계가 필요합니다.
상태를 읽고 변경하는 공통 상위에서 관리하는 이유는 [Compose 상태 소유 가이드](https://developer.android.com/develop/ui/compose/state-hoisting)를 참고하세요.

각 플랫폼의 시작점은 기존처럼 유지됩니다:

- Android: `androidApp/src/main/.../MainActivity.kt`
- Desktop: `desktopApp/src/main/.../main.kt`
- Web: `webApp/src/webMain/.../main.kt`
- iOS: `shared/src/iosMain/.../MainViewController.kt`

## 새 기능을 추가하는 예

가령 메모 기능을 추가한다면 `features/notes/`를 만들고 모델·화면·컴포넌트·상태를 그 안에 둡니다.
먼저 해당 기능에서 사용하고, 여러 기능에서 실제로 필요해진 UI만 `shared/` 또는 `core/`로 옮깁니다.
서버나 DB를 연결할 때는 API/저장 접근을 담당하는 data/repository를 추가하고, 화면에서 직접 호출하지 않도록 확장할 수 있습니다.

## 검증

- RN: 타입 검사와 ESLint, Expo 웹 번들 생성
- Flutter: 정적 분석, 목록/상세 왕복 테스트, Controller 단위 테스트
- KMP: Android 빌드, Desktop·JS·Wasm 컴파일, 공통 모델/상태 테스트

실행 방법은 [README](README.md)를 참고하세요. 데이터는 여전히 메모리에만 저장합니다.

## 유튜브 기능의 플랫폼별 WebView 분리

새 기능은 기존 할 일 폴더와 나란히 `features/youtube/`에 추가했습니다.

- RN: `app/youtube.tsx` → `features/youtube/screens/youtube-screen.tsx` → `components/youtube-browser.tsx`. 웹에서는 `.web.tsx` 구현을 자동으로 선택합니다.
- Flutter: `features/youtube/pages/youtube_page.dart`가 페이지를 조립하고 `widgets/youtube_browser.dart`가 플러그인 Controller와 WebView를 관리합니다. 라우트 생성은 `navigation/task_routes.dart`에 있습니다.
- KMP: `commonMain/.../features/youtube/screens/YoutubeScreen.kt`는 공통 페이지입니다. `components/YoutubeBrowser.kt`에 `expect` 선언을 두고 각 플랫폼 source set에 같은 패키지의 `actual` 구현을 넣었습니다.

```text
shared/src/
  commonMain/.../features/youtube/
    screens/YoutubeScreen.kt
    components/YoutubeBrowser.kt        # expect 선언
    components/BrowserFallback.kt       # 브라우저 열기 공통 UI
  androidMain/.../features/youtube/components/YoutubeBrowser.android.kt
  iosMain/.../features/youtube/components/YoutubeBrowser.ios.kt
  jvmMain/.../features/youtube/components/YoutubeBrowser.desktop.kt
  jsMain/.../features/youtube/components/YoutubeBrowser.js.kt
  wasmJsMain/.../features/youtube/components/YoutubeBrowser.wasm.kt
```

KMP Android/iOS는 각각 플랫폼 기본 WebView를 사용하며, 화면에서 벗어나면 로딩을 중지합니다. Android WebView는 해제 시 destroy합니다.
Desktop/Web actual은 공통 BrowserFallback을 호출합니다. 따라서 공통 화면은 Android WebView 또는 WKWebView 타입을 참조하지 않습니다.
Flutter 공식 WebView 플러그인이 지원하는 Android/iOS/macOS 이외의 대상도 브라우저로 엽니다.
플랫폼 지원 표와 실행 주의점은 [README](README.md)의 유튜브 절을 참고하세요.

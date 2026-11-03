# 같은 기능으로 비교하는 예시 앱

React Native(Expo), Flutter, Kotlin Multiplatform에 같은 ‘오늘의 할 일’ 앱을 구현했습니다.
보라색 진행률 카드, 입력란, 필터, 체크 목록과 상세 화면으로 구성되어 있습니다.

## 공통 동작
- 초기 할 일 3개, 완료 1개
- 할 일 추가: 앞뒤 공백 제거, 빈 입력 무시, 최대 100자
- 완료 상태 전환과 삭제
- 전체 / 진행 중 / 완료 필터
- 전체 목록 기준 완료 개수와 진행률, 빈 목록 안내

데이터는 메모리에 보관하며 앱을 다시 시작하면 초기 목록으로 돌아갑니다.
별도 서버나 API 키는 필요하지 않습니다.

## 실행

| 프로젝트 | 폴더에서 실행할 명령 | 주요 구현 |
| --- | --- | --- |
| React Native | `npm run start` | `react-native-expo/src/features/tasks/screens/` |
| Flutter | `flutter run` | `flutter-test/lib/features/tasks/pages/` |
| KMP Android | Android Studio에서 `androidApp` 실행 | `kmptest/shared/src/commonMain/kotlin/com/example/kmp_test/features/tasks/` |
| KMP Desktop | `.\gradlew.bat :desktopApp:run` | 같은 공통 UI |
| KMP Web | `.\gradlew.bat :webApp:wasmJsBrowserDevelopmentRun` | 같은 공통 UI |

명령은 각 프로젝트 폴더에서 실행합니다. Expo는 실행 후 `a`(Android), `w`(웹)를 사용하거나 Expo Go에서 QR 코드를 스캔합니다.
KMP iOS는 macOS에서 `kmptest/iosApp/iosApp.xcodeproj`를 Xcode로 열어 실행합니다.

## 검증 명령

- React Native: `node node_modules/typescript/bin/tsc --noEmit`, `node node_modules/eslint/bin/eslint.js .`
- Flutter: `flutter analyze`, `flutter test`
- KMP: `.\gradlew.bat :shared:jvmTest :desktopApp:compileKotlin :androidApp:assembleDebug`

## 라우팅 예제

목록에서 할 일 제목을 누르면 상세 화면으로 이동합니다. 체크박스는 완료 전환 전용입니다.
상세에서 완료 상태를 바꾼 뒤 돌아오면 목록의 상태와 진행률이 갱신됩니다.
상세에서 삭제하면 목록으로 돌아옵니다. 목록의 필터와 입력 중인 내용은 화면 왕복 시 유지됩니다.

| 구현 | 목록 → 상세 |
| --- | --- |
| Expo Router | `/` → `/tasks/[id]`, 루트의 TaskProvider로 상태 공유 |
| Flutter Navigator | `/` → `/tasks/:id`, MaterialPageRoute와 공유 TaskController 사용 |
| KMP Navigation Compose | `tasks` → `tasks/{id}`, NavHost와 NavController 사용 |

KMP 웹은 브라우저 히스토리와 연결하여 주소의 해시 경로 및 뒤로/앞으로 가기를 지원합니다.
Expo와 KMP에서 존재하지 않는 할 일 ID를 열면 안내 문구와 목록으로 돌아가는 버튼이 표시됩니다.
메모리 데이터이므로 새로고침/재실행 후에는 초기 목록만 존재합니다.

## 파일 아키텍처

프레임워크별 폴더 트리, 파일 역할과 데이터 흐름은 [ARCHITECTURE.md](ARCHITECTURE.md)를 참고하세요.

## 유튜브 WebView 예제

목록 오른쪽의 **유튜브 열기 ›**를 누르면 새 `/youtube` 페이지로 이동합니다.
상단의 목록으로/뒤로 가기는 앱 목록으로 돌아오고, WebView 안의 **웹 뒤로**는 웹 방문 기록을 이동합니다.
모바일 WebView에는 새로고침을 제공합니다. 로그인이나 동영상 자동 재생은 추가로 구현하지 않았습니다.

| 대상 | React Native | Flutter | KMP |
| --- | --- | --- | --- |
| Android | react-native-webview | webview_flutter | AndroidView + Android WebView |
| iOS | react-native-webview | webview_flutter | UIKitView + WKWebView |
| Web | 외부 브라우저로 열기 | 외부 브라우저로 열기 | 외부 브라우저로 열기 |
| Windows / Linux | 해당 앱 실행 대상 아님 | 외부 브라우저로 열기 | 외부 브라우저로 열기 |
| macOS | 해당 앱 실행 대상 아님 | webview_flutter | Desktop은 외부 브라우저로 열기 |

모바일은 `https://m.youtube.com`, 브라우저 열기는 `https://www.youtube.com`을 사용합니다.
실제 사이트/동영상 이용에는 네트워크 연결이 필요합니다. 빌드와 라우팅 테스트를 수행했으며 iOS 기기에서의 WebView/영상 재생은 검증하지 않았습니다.

새 패키지 설치 후 Expo/Flutter 앱은 개발 서버/앱을 완전히 다시 시작해 주세요.
Flutter를 Windows에서 처음 설치할 때 심볼릭 링크 관련 오류가 나오면 **Windows 설정 → 시스템 → 개발자용 → 개발자 모드**를 켜고 `flutter pub get`을 다시 실행합니다.
새 네이티브 플러그인을 추가한 뒤에는 `flutter pub get`이 오류 없이 완료되어야 합니다. 설치 후처리가 실패하면 플러그인 등록이 누락될 수 있으므로, 오류를 해결한 뒤 `flutter pub get`을 다시 실행하고 앱을 완전히 종료/재실행하세요.

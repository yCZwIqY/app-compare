# 같은 기능으로 비교하는 예시 앱

React Native(Expo), Flutter, Kotlin Multiplatform에 같은 ‘오늘의 할 일’ 앱을 구현했습니다.
보라색 진행률 카드, 입력란, 필터, 체크 목록으로 구성된 단일 화면입니다.

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
| React Native | `npm run start` | `react-native-expo/src/app/index.tsx` |
| Flutter | `flutter run` | `flutter-test/lib/main.dart` |
| KMP Android | Android Studio에서 `androidApp` 실행 | `kmptest/shared/src/commonMain/kotlin/com/example/kmp_test/App.kt` |
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
| Flutter Navigator | `/` → `/tasks/:id`, MaterialPageRoute와 pop 결과 사용 |
| KMP Navigation Compose | `tasks` → `tasks/{id}`, NavHost와 NavController 사용 |

KMP 웹은 브라우저 히스토리와 연결하여 주소의 해시 경로 및 뒤로/앞으로 가기를 지원합니다.
Expo와 KMP에서 존재하지 않는 할 일 ID를 열면 안내 문구와 목록으로 돌아가는 버튼이 표시됩니다.
메모리 데이터이므로 새로고침/재실행 후에는 초기 목록만 존재합니다.

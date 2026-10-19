# Implementation Plan - Fix `cannot find symbol variable raw` Build Error

The project fails to build because several Java files reference resources in `R.raw` (specifically Lottie animations), but the `res/raw` directory and its contents are missing.

## User Review Required

> [!IMPORTANT]
> The missing resources are Lottie animation files. I will create placeholder JSON files so the project compiles. You should replace these placeholders with your actual Lottie animation files later.

## Proposed Changes

### Resources

#### [NEW] [score_popup.json](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/src/main/res/raw/score_popup.json)
#### [NEW] [correct.json](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/src/main/res/raw/correct.json)
#### [NEW] [wrong.json](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/src/main/res/raw/wrong.json)
#### [NEW] [confetti.json](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/src/main/res/raw/confetti.json)
#### [NEW] [level_up.json](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/src/main/res/raw/level_up.json)

Creation of the `app/src/main/res/raw` directory and adding minimal valid Lottie JSON files for each missing resource.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:compileDebugJavaWithJavac` to verify the build error is resolved.

### Manual Verification
- Verify that `BaseGameActivity.java`, `AchievementUnlockedView.java`, and `LevelUpView.java` no longer show compilation errors in the IDE.

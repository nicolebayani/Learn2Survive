# Fix "cannot find symbol class CircleImageView" Build Error

The project fails to build because `CircleImageView` is used in layout files (`fragment_home.xml`, `fragment_profile.xml`) but the corresponding dependency is not declared in the project's build configuration.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/gradle/libs.versions.toml)
- Add `circleimageview = "3.1.0"` to the `[versions]` section.
- Add `circleimageview = { group = "de.hdodenhof", name = "circleimageview", version.ref = "circleimageview" }` to the `[libraries]` section.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/build.gradle.kts)
- Add `implementation(libs.circleimageview)` to the `dependencies` block.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify that the project builds successfully without the "cannot find symbol" error.

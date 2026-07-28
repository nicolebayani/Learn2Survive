# Fix "cannot find symbol class CircleImageView" Build Error

The project fails to build because `CircleImageView` is used in `fragment_home.xml` but the corresponding library dependency is missing from the project configuration.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/gradle/libs.versions.toml)
- Add the `circleimageview` version and library definition.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/Nicole/AndroidStudioProjects/Learn2Survive/app/build.gradle.kts)
- Add `libs.circleimageview` to the dependencies block.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure the project builds successfully.
- Run `./gradlew :app:compileDebugJavaWithJavac` (the specific task that failed) to verify the fix.

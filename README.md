# Learn2Survive

An educational mobile game about Disaster Preparedness and Awareness built with modern Android development practices.

## 🎯 Project Overview

Learn2Survive is a premium mobile application designed to educate users about disaster preparedness through interactive learning modules. The app features a modern, glassmorphism-inspired UI with smooth animations and a clean architecture foundation.

## 🏗️ Architecture

This project follows **Clean Architecture** principles with the following layers:

### Package Structure
```
android.bignerdranch.learn2survive/
├── data/
│   ├── local/          # Local data sources
│   ├── model/          # Data models
│   ├── remote/         # Remote data sources (Firebase)
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic use cases
├── ui/
│   ├── auth/           # Authentication screens
│   ├── base/           # Base classes (Activity, ViewModel)
│   ├── home/           # Home screen with fragments
│   ├── onboarding/     # Onboarding screens
│   ├── profile/        # Profile screen
│   ├── settings/       # Settings screen
│   ├── splash/         # Splash screen
│   └── adapter/        # RecyclerView adapters
├── di/                 # Dependency injection
└── utils/              # Utility classes
```

## 🎨 Design System

### Colors
- **Primary**: `#1565C0` (Blue)
- **Secondary**: `#2E7D32` (Green)
- **Accent**: `#F9A825` (Amber)
- **Background**: `#F5F7FA` (Light Gray)
- **Surface**: `#FFFFFF` (White)

### Dark Mode
The app fully supports dark mode with appropriate color schemes defined in `values-night/`.

### Glassmorphism
Custom glassmorphism effects are implemented through:
- Semi-transparent backgrounds
- Subtle borders
- Rounded corners
- Blur effects

## 🛠️ Technology Stack

- **Language**: Java (No Kotlin)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **UI Framework**: Material Design 3
- **Architecture**: MVVM with Clean Architecture
- **Navigation**: Navigation Component
- **Firebase Services**:
  - Authentication
  - Firestore
  - Storage
  - Analytics
- **Animations**: Lottie
- **Image Loading**: Glide
- **Build System**: Gradle with Kotlin DSL

## 📱 Features Implemented

### ✅ Authentication Flow
- **Splash Screen**: Animated entry point with MotionLayout
- **Onboarding**: 3-screen animated onboarding with Lottie
- **Login**: Email/password authentication with validation
- **Register**: User registration with form validation
- **Forgot Password**: Password reset via email

### ✅ Navigation
- **Bottom Navigation**: 4-tab navigation (Home, Learn, Profile, Settings)
- **Navigation Drawer**: Side drawer with user info and menu items
- **Navigation Graph**: Centralized navigation configuration

### ✅ UI Components
- **Glassmorphism Cards**: Reusable card components with glass effect
- **Custom Buttons**: Primary, secondary, and accent button styles
- **Text Inputs**: Material Design 3 input fields with validation
- **Gradient Backgrounds**: Premium gradient backgrounds
- **Page Indicators**: Custom dot indicators for onboarding

### ✅ Architecture Components
- **Repository Pattern**: Clean separation of data access
- **ViewModel**: MVVM pattern with LiveData
- **Base Classes**: Reusable base activity and view model
- **Dependency Injection**: Simple DI provider for dependencies

## 🔧 Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- Java 11 or higher
- Firebase account

### Firebase Setup

1. **Create Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project named "Learn2Survive"

2. **Add Android App**
   - Package name: `android.bignerdranch.learn2survive`
   - Download `google-services.json`
   - Replace the placeholder in `app/google-services.json`

3. **Enable Authentication**
   - Go to Authentication → Sign-in method
   - Enable Email/Password provider

4. **Enable Firestore**
   - Go to Firestore Database
   - Create database in test mode
   - Set up security rules for production

5. **Enable Storage** (for future use)
   - Go to Storage
   - Set up rules for file uploads

### Build the Project

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Replace `google-services.json` with your Firebase config
5. Build and run

## 📦 Dependencies

Key dependencies are managed in `gradle/libs.versions.toml`:

```toml
[versions]
agp = "9.3.1"
material = "1.12.0"
navigation = "2.7.7"
lottie = "6.4.0"
firebaseBom = "32.8.0"
lifecycle = "2.7.0"
```

## 🎯 Current Status

### Completed
- ✅ Project structure and dependencies
- ✅ Clean Architecture setup
- ✅ Firebase configuration
- ✅ Material Design 3 theme with glassmorphism
- ✅ Reusable UI components
- ✅ Firebase Authentication (Repository, ViewModel)
- ✅ Navigation Component setup
- ✅ Splash Screen with animations
- ✅ Animated Onboarding with Lottie
- ✅ Login Screen with validation
- ✅ Register Screen with validation
- ✅ Forgot Password Screen
- ✅ Home Screen with Bottom Navigation
- ✅ Navigation Drawer
- ✅ Profile Placeholder
- ✅ Settings Placeholder
- ✅ Lottie animation files

### Pending (Future Implementation)
- 🔄 Disaster learning modules
- 🔄 Profile functionality
- 🔄 Settings functionality
- 🔄 Firebase Storage integration
- 🔄 Advanced animations
- 🔄 Push notifications

## 🚀 Running the App

1. **Development Build**
   ```bash
   ./gradlew assembleDebug
   ```

2. **Install on Device**
   ```bash
   ./gradlew installDebug
   ```

3. **Run Tests**
   ```bash
   ./gradlew test
   ```

## 📝 Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise
- Follow Clean Architecture principles

## 🔐 Security Notes

- **Firebase Rules**: Update Firestore rules for production
- **API Keys**: Never commit real API keys
- **User Data**: All user data is stored securely in Firebase
- **Authentication**: Firebase Auth handles user authentication

## 🐛 Known Issues

- Lottie animations are placeholders - replace with custom animations
- Profile and Settings screens are placeholders
- Learn module not yet implemented

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is created for educational purposes.

## 👥 Team

- **Developer**: Nicole Bayani
- **Project**: Learn2Survive - Disaster Preparedness App

## 📞 Support

For issues or questions, please create an issue in the repository.

---

**Note**: This is the foundation phase of the project. Disaster modules and advanced features will be implemented in future iterations.

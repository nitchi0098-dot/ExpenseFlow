# ExpenseFlow - Personal Expense Management App

## Overview
ExpenseFlow is a modern Android application for personal expense management built with Kotlin, MVVM architecture, Room database, and Material 3 design system.

## Features
- 📊 Dashboard with expense analytics
- 💰 Track expenses by category
- 📈 Visual charts and statistics
- 🏷️ Categorized expense management
- 📅 Monthly income tracking
- 🎨 Modern Material 3 UI with glassmorphism
- 🌓 Dark mode support
- 🔍 Search and filter capabilities

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM
- **Database**: Room
- **UI Framework**: Material 3
- **Navigation**: Jetpack Navigation
- **State Management**: StateFlow
- **Charts**: MPAndroidChart
- **Build System**: Gradle

## Project Structure
```
app/src/main/
├── java/com/expenseflow/app/
│   ├── data/
│   │   ├── dao/
│   │   ├── database/
│   │   ├── entity/
│   │   └── repository/
│   ├── ui/
│   │   ├── activity/
│   │   ├── adapter/
│   │   ├── fragment/
│   │   └── viewmodel/
│   ├── utils/
│   └── ExpenseFlowApp.kt
├── res/
│   ├── drawable/
│   ├── layout/
│   ├── navigation/
│   ├── values/
│   └── xml/
└── AndroidManifest.xml
```

## Getting Started

### Prerequisites
- Android Studio (Latest)
- JDK 11+
- Android SDK 26+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device

## Building
```bash
./gradlew build
./gradlew installDebug
```

## Testing
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Features in Detail

### Dashboard
- Real-time income and expense tracking
- Visual representation with pie charts
- Quick overview of pending and paid expenses
- Remaining balance calculation

### Expense Management
- Add expenses with title, amount, category, and description
- Mark expenses as paid
- Search and filter expenses
- Delete expenses

### Categories
- Predefined categories (Food, Transportation, Entertainment, etc.)
- Add custom categories
- Category-wise expense breakdown

### Analytics
- Monthly income input
- Category-wise spending visualization
- Expense trends and statistics

## UI/UX Features
- Glassmorphism design elements
- Smooth animations and transitions
- Responsive layouts
- Material 3 color system
- Dark mode with system preference detection
- Accessibility support

## Contributing
Contributions are welcome! Please follow the code style and create pull requests to the development branch.

## License
MIT License - See LICENSE file for details

## Author
Developed by nitchi0098

## Support
For issues and feature requests, please open an issue on GitHub.

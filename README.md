# Scottish Power Android Tech Test

## 📌 Project Overview
This project is an Android application built using MVVM architecture with **Jetpack Compose**, 
**Hilt Dependency Injection**, **Room Database**, and **Navigation Component**. 
It displays a list of comments fetched from an API, supports offline caching, 
and allows users to view comment details.

---

## 📂 Project Structure
📂 app/
├── 📂 data/              # Data layer (API, Room Database)
│   ├── 📂 local/         # Room Database implementation
│   ├── 📂 network/       # Retrofit API implementation
│   ├── 📂 repository/    # Repository for data handling
├── 📂 domain/            # Business logic (UseCases, Models, Repository interfaces)
├── 📂 presentation/      # UI & ViewModel layer
│   ├── 📂 navigation/    # Jetpack Compose Navigation
│   ├── 📂 ui/            # UI components (Jetpack Compose)
│   ├── 📂 viewmodel/     # ViewModels using Hilt DI

---

## ✨ Features
✅ **Jetpack Compose UI**  
✅ **Navigation Component**  
✅ **Hilt for Dependency Injection**  
✅ **Room Database Caching**  
✅ **Unit Tests & UI Tests (Compose UI Testing, MockK, Coroutine Test)**  
✅ **Offline Support**  

---

## 🚀 Getting Started

### **📌 Prerequisites**
- Android Studio **Giraffe or newer**
- **Gradle 8+**
- **Kotlin 1.8+**
- **Internet connection for API calls**

### **📌 Clone the Repository**
git clone https://github.com/your-repo/ScottishPowerAndroidTest.git
cd ScottishPowerAndroidTest

📌 Build & Run the Project
	1.	Open in Android Studio.
	2.	Sync Gradle (File → Sync Project with Gradle Files).
	3.	Run the app using Emulator or Physical Device.

⸻

🏗️ Architecture

This project follows Clean Architecture with:
	1.	Presentation Layer → Jetpack Compose UI + ViewModels.
	2.	Domain Layer → Use Cases & Repository Interfaces.
	3.	Data Layer → Retrofit API + Room Database.

⸻

✅ Testing

This project includes:
	•	Unit Tests for ViewModels, Use Cases, and Repository (mockk, coroutines-test).
	•	UI Tests for Compose UI using compose-ui-test.
Run tests:
./gradlew testDebugUnitTest
./gradlew connectedAndroidTest


⸻

## 📌 Future Improvements
1. **Enhanced Error Handling** – Improve error messages and retry mechanisms for API failures.
2. **Pagination for Comments List** – Implement pagination to load comments efficiently.
3. **Better Offline Support** – Use WorkManager to sync data in the background.
4. **Improved UI Transitions** – Use Motion Compose for more fluid animations.
5. **Write Android Test Cases for `NavGraph`** – Add UI tests for navigation between screens.

⸻

🛠 Tech Stack
	•	Language: Kotlin
	•	UI: Jetpack Compose
	•	Networking: Retrofit, OkHttp
	•	Dependency Injection: Hilt
	•	Database: Room
	•	Navigation: Jetpack Navigation
	•	Testing: JUnit, MockK, Compose UI Test

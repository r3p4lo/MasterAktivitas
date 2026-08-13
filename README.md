Berikut versi yang sudah disesuaikan dengan spesifikasi asli **MASTER AKTIVITAS**. Saya mempertahankan gaya profesional, visual berwarna, badge logo teknologi, tetapi mengganti seluruh bagian PHP/MySQL/XAMPP menjadi **Android + Kotlin + Jetpack Compose + Room + MVVM + Gradle**.

<div align="center">

# ⚡ MASTER AKTIVITAS

### Personal Operating System • LifeOS • Offline-First

<p>
  <strong>Plan • Execute • Track • Analyze • Improve</strong>
</p>

<p>
  <a href="https://github.com/r3p4lo/MasterAktivitas">
    <img src="https://img.shields.io/github/stars/r3p4lo/MasterAktivitas?style=for-the-badge&logo=github" alt="Stars">
  </a>
  <a href="https://github.com/r3p4lo/MasterAktivitas">
    <img src="https://img.shields.io/github/forks/r3p4lo/MasterAktivitas?style=for-the-badge&logo=github" alt="Forks">
  </a>
  <img src="https://img.shields.io/github/last-commit/r3p4lo/MasterAktivitas?style=for-the-badge&logo=git" alt="Last Commit">
</p>

<p>
  <img src="https://img.shields.io/badge/Version-1.0.0-2962FF?style=for-the-badge">
  <img src="https://img.shields.io/badge/Status-Active-00C853?style=for-the-badge">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android">
  <img src="https://img.shields.io/badge/Offline--First-Enabled-7C4DFF?style=for-the-badge">
</p>

<br>

<img src="docs/images/dashboard.png" width="850" alt="Master Aktivitas Dashboard">

</div>

---

# 🧠 About

**MASTER AKTIVITAS** adalah aplikasi **Personal Operating System (LifeOS)** berbasis Android yang dirancang untuk mengelola berbagai aspek kehidupan dalam satu sistem terintegrasi.

Aplikasi ini menggabungkan:

* 🎯 Goal & Task Management
* 📊 Progress Tracking
* 💰 Finance Tracking
* 📚 Learning Tracking
* 🔥 Habit Tracking
* 📅 Calendar
* 🔔 Notification
* 💾 Backup & Restore
* 🧪 R&D Management
* 💻 Technology Tracking
* 🇯🇵 Career / Japanese Progress

MASTER AKTIVITAS menggunakan pendekatan **offline-first**, sehingga data utama dapat digunakan tanpa koneksi internet.

---

# 🎯 Philosophy

MASTER AKTIVITAS dibangun berdasarkan siklus:

<div align="center">

```text
        ┌───────────────┐
        │      PLAN     │
        └───────┬───────┘
                ↓
        ┌───────────────┐
        │    EXECUTE    │
        └───────┬───────┘
                ↓
        ┌───────────────┐
        │     TRACK     │
        └───────┬───────┘
                ↓
        ┌───────────────┐
        │    ANALYZE    │
        └───────┬───────┘
                ↓
        ┌───────────────┐
        │    IMPROVE    │
        └───────┬───────┘
                │
                └──────────────→ PLAN
```

</div>

Tujuannya bukan sekadar mencatat aktivitas, tetapi membangun sistem untuk **mengukur dan mengevaluasi eksekusi kehidupan sehari-hari**.

---

# 📱 Application Information

| Property                  | Value                              |
| ------------------------- | ---------------------------------- |
| **Application Name**      | MASTER AKTIVITAS                   |
| **Package Name**          | `id.masteraktivitas`               |
| **Version**               | `1.0.0`                            |
| **Min SDK**               | Android 8.0 — API 26               |
| **Target SDK**            | Android 14 — API 34                |
| **Language**              | Kotlin                             |
| **UI Framework**          | Jetpack Compose                    |
| **Design System**         | Material 3                         |
| **Database**              | SQLite via Room                    |
| **Architecture**          | MVVM                               |
| **Application Type**      | Personal Operating System / LifeOS |
| **Network Model**         | Offline-First                      |
| **Build System**          | Gradle                             |
| **Gradle Version**        | 8.4                                |
| **Android Gradle Plugin** | 8.2.2                              |
| **Kotlin Compiler**       | 1.9.22                             |
| **JDK**                   | Java 17                            |

---

# 🧩 Tech Stack

<div align="center">

### Mobile Development

<img src="https://skillicons.dev/icons?i=kotlin,androidstudio" height="75">

### Architecture & UI

<img src="https://skillicons.dev/icons?i=android" height="75">

<br>

**Kotlin** • **Jetpack Compose** • **Material 3** • **MVVM**

### Database

<img src="https://skillicons.dev/icons?i=sqlite" height="75">

**SQLite + Room**

### Build & Development

<img src="https://skillicons.dev/icons?i=gradle,git,github,linux" height="75">

**Gradle 8.4** • **Git** • **GitHub** • **Ubuntu**

</div>

---

# ✨ Core Features

## 1. 📊 Dashboard Utama

Dashboard berfungsi sebagai pusat kontrol seluruh aktivitas.

Menampilkan:

* Progress harian
* Ringkasan task
* Income hari ini
* Learning time
* R&D aktif
* Technology aktif
* Progress karier
* Progress Japanese
* Top priority hari ini

Contoh struktur informasi:

```text
┌─────────────────────────────────────┐
│          MASTER AKTIVITAS           │
├─────────────────────────────────────┤
│                                     │
│  Daily Progress             72%     │
│  ███████████████░░░░░               │
│                                     │
│  Tasks                             │
│  ✓ Done              8              │
│  ◉ In Progress       3              │
│  ⚠ Blocked           1              │
│                                     │
│  💰 Income Today     Rp XXX.XXX     │
│  📚 Learning Time    02h 30m        │
│  🧪 Active R&D       3              │
│  💻 Active Tech      2              │
│                                     │
│  ⭐ TOP PRIORITY                   │
│  Complete Project X                │
└─────────────────────────────────────┘
```

---

# 2. ✅ Task Management

Sistem task menyediakan operasi CRUD:

* Create
* Read
* Update
* Delete

### Task Status

```text
TODO
  ↓
IN PROGRESS
  ↓
DONE
```

Task juga dapat memiliki status:

```text
BLOCKED
```

### Priority

```text
🔴 HIGH
🟡 MEDIUM
🟢 LOW
```

### Task Data

Setiap task dapat memiliki:

* Title
* Description
* Status
* Priority
* Deadline
* Progress
* Project
* Category
* Subcategory

---

# 3. 🚀 Project Management

Project digunakan untuk mengelompokkan pekerjaan yang memiliki tujuan tertentu.

### Project Status

```text
IDEA
 ↓
PLANNED
 ↓
IN_DEVELOPMENT
 ↓
TESTING
 ↓
DONE
```

Project juga dapat berada pada kondisi:

```text
BLOCKED
PAUSED
```

### Progress

Project mendukung:

* Manual progress
* Automatic progress
* Deadline tracking
* Category relationship

---

# 4. 💰 Finance Tracker

Finance Tracker digunakan untuk mencatat kondisi keuangan.

### Income

```text
Income
 ├── Source
 ├── Amount
 ├── Date
 └── Description
```

### Expense

```text
Expense
 ├── Category
 ├── Amount
 ├── Date
 └── Description
```

### Financial Analytics

Sistem menghitung:

```text
Total Income
      ↓
Total Expense
      ↓
Net Income
```

> MASTER AKTIVITAS hanya berfungsi sebagai pencatatan dan analisis keuangan. Aplikasi **tidak melakukan eksekusi transaksi otomatis**.

---

# 5. 📚 Learning Tracker

Learning Tracker digunakan untuk mencatat aktivitas belajar.

Data yang dapat dicatat:

* Topik
* Durasi belajar
* Tanggal
* Catatan
* Total learning time

Contoh:

```text
Today's Learning

🇯🇵 Japanese        60 min
💻 Programming      90 min
🧠 Neuroscience     30 min

──────────────────────
TOTAL              180 min
```

---

# 6. 🔥 Habit Tracker

Habit Tracker digunakan untuk membangun konsistensi aktivitas.

Fitur:

* Create habit
* Update habit
* Delete habit
* Daily checklist
* Streak counter

Contoh:

```text
Habit

☑ Japanese Study
☑ Read
☐ Exercise
☑ Research

Current Streak
🔥 14 Days
```

---

# 7. 📅 Calendar

Calendar menggabungkan aktivitas berdasarkan waktu.

Menampilkan:

* Upcoming events
* Task deadline
* Calendar events
* Event dalam 30 hari ke depan

Alur:

```text
Task
 │
 ├── Deadline
 │
 └──────────────┐
                ↓
             Calendar
                ↑
                │
Event ──────────┘
```

---

# 8. 💾 Backup & Restore

MASTER AKTIVITAS mendukung backup database.

### Export

```text
Database
    ↓
Backup Repository
    ↓
JSON
    ↓
File Backup
```

### Import

```text
JSON Backup
    ↓
Validation
    ↓
Database
    ↓
MASTER AKTIVITAS
```

Fitur:

* Export JSON
* Import JSON
* Backup seluruh database
* Restore data

---

# 9. 🔔 Notification System

Sistem notification menggunakan local scheduler.

Jenis notification:

### Daily Reminder

Pengingat aktivitas harian.

### Task Deadline

Notifikasi ketika deadline task mendekat.

### Local Alarm

Scheduler alarm lokal tanpa membutuhkan server eksternal.

---

# 🏛️ Architecture

MASTER AKTIVITAS menggunakan **MVVM — Model View ViewModel**.

```text
┌─────────────────────────────────────┐
│               UI                    │
│         Jetpack Compose             │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│            ViewModel                │
│        State + Business Logic       │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│             Repository              │
│          AppRepository              │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│             Room DAO                │
└──────────────────┬──────────────────┘
                   │
                   ▼
┌─────────────────────────────────────┐
│          SQLite Database            │
└─────────────────────────────────────┘
```

### Architecture Flow

```text
User
 ↓
Compose UI
 ↓
ViewModel
 ↓
Repository
 ↓
DAO
 ↓
Room
 ↓
SQLite
```

---

# 📂 Project Structure

```text
MasterAktivitas/
│
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
│
├── gradlew
├── gradlew.bat
│
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
└── app/
    │
    ├── build.gradle.kts
    │
    └── src/
        └── main/
            │
            ├── AndroidManifest.xml
            │
            └── java/
                └── id/
                    └── masteraktivitas/
                        │
                        ├── MasterApplication.kt
                        ├── MainActivity.kt
                        ├── ServiceLocator.kt
                        │
                        ├── data/
                        │   │
                        │   ├── local/
                        │   │   ├── Entities.kt
                        │   │   ├── Converters.kt
                        │   │   ├── Daos.kt
                        │   │   └── AppDatabase.kt
                        │   │
                        │   ├── backup/
                        │   │   └── BackupRepository.kt
                        │   │
                        │   └── repository/
                        │       └── AppRepository.kt
                        │
                        ├── domain/
                        │   └── UiStates.kt
                        │
                        ├── notifications/
                        │   └── Notifications.kt
                        │
                        ├── util/
                        │   ├── DateUtils.kt
                        │   └── Extensions.kt
                        │
                        └── ui/
                            │
                            ├── theme/
                            │   └── Theme.kt
                            │
                            ├── components/
                            │   └── Common.kt
                            │
                            ├── viewmodels/
                            │   └── ViewModels.kt
                            │
                            └── screens/
                                └── Screens.kt
```

---

# 🛠️ Development Environment

## Requirements

Untuk melakukan build dari source code:

| Requirement     | Minimum       |
| --------------- | ------------- |
| Ubuntu          | 22.04 / 24.04 |
| RAM             | 8 GB          |
| Recommended RAM | 16 GB         |
| Storage         | 30 GB+        |
| JDK             | 17            |
| Android SDK     | API 34        |
| Gradle          | 8.4           |
| Kotlin          | 1.9.22        |
| AGP             | 8.2.2         |

Koneksi internet diperlukan ketika pertama kali melakukan setup untuk mengunduh dependencies.

Setelah dependencies tersedia, aplikasi dirancang untuk berjalan **offline-first**.

---

# 🐧 Build di Ubuntu

## Step 1 — Install JDK 17

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

Verifikasi:

```bash
java -version
```

Pastikan Java 17 terdeteksi.

---

# Step 2 — Setup Android SDK

Buat direktori SDK:

```bash
mkdir -p ~/Android/Sdk/cmdline-tools
```

Setelah Android Command Line Tools tersedia:

```bash
cd ~/Downloads

unzip commandlinetools-linux-*_latest.zip \
  -d ~/Android/Sdk/cmdline-tools
```

Kemudian:

```bash
cd ~/Android/Sdk/cmdline-tools
mv cmdline-tools latest
```

---

# Step 3 — Configure Environment Variables

Edit:

```bash
nano ~/.bashrc
```

Tambahkan:

```bash
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

Kemudian:

```bash
source ~/.bashrc
```

Verifikasi:

```bash
echo $ANDROID_HOME
```

---

# Step 4 — Install Android SDK Components

```bash
sdkmanager "platform-tools"
```

Install Android API 34:

```bash
sdkmanager "platforms;android-34"
```

Install Build Tools:

```bash
sdkmanager "build-tools;34.0.0"
```

Accept licenses:

```bash
sdkmanager --licenses
```

---

# Step 5 — Clone Repository

```bash
git clone https://github.com/r3p4lo/MasterAktivitas.git
```

Masuk ke project:

```bash
cd MasterAktivitas
```

---

# Step 6 — Check Gradle Wrapper

Pastikan Gradle Wrapper dapat dijalankan:

```bash
chmod +x gradlew
```

Kemudian:

```bash
./gradlew --version
```

Project menggunakan:

```text
Gradle 8.4
```

---

# Step 7 — Build Debug APK

Jalankan:

```bash
./gradlew assembleDebug
```

Jika build berhasil, APK akan berada di:

```text
app/build/outputs/apk/debug/app-debug.apk
```

---

# 📱 Step 8 — Install APK ke Android

Hubungkan Android melalui ADB.

Verifikasi:

```bash
adb devices
```

Kemudian:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

Jika muncul:

```text
Success
```

APK berhasil di-install.

---

# ⚡ Quick Build

Jika seluruh environment sudah dikonfigurasi:

```bash
git clone https://github.com/r3p4lo/MasterAktivitas.git

cd MasterAktivitas

chmod +x gradlew

./gradlew assembleDebug
```

APK:

```text
app/build/outputs/apk/debug/app-debug.apk
```

---

# 🔄 Development Workflow

Workflow pengembangan:

```text
┌──────────────┐
│   Edit Code  │
└──────┬───────┘
       ↓
┌──────────────┐
│   Build APK  │
└──────┬───────┘
       ↓
┌──────────────┐
│ Install ADB  │
└──────┬───────┘
       ↓
┌──────────────┐
│    Testing   │
└──────┬───────┘
       ↓
┌──────────────┐
│    Commit    │
└──────┬───────┘
       ↓
┌──────────────┐
│     Push     │
└──────────────┘
```

Contoh:

```bash
git add .

git commit -m "feat: improve dashboard"

git push origin main
```

---

# 🧪 Testing Strategy

Pengujian dapat dilakukan pada beberapa lapisan:

```text
UI
 ↓
ViewModel
 ↓
Repository
 ↓
DAO
 ↓
Database
```

Area yang perlu diuji:

* Task CRUD
* Project CRUD
* Finance calculation
* Learning duration
* Habit streak
* Calendar events
* Notification scheduler
* Backup JSON
* Restore JSON
* Database persistence

---

# 🔐 Data & Privacy

MASTER AKTIVITAS menggunakan pendekatan **offline-first**.

Data utama disimpan secara lokal melalui:

```text
Room
 ↓
SQLite
 ↓
Local Device
```

Tidak diperlukan koneksi internet untuk operasi utama aplikasi.

> Jangan memasukkan API key, credential, password, atau data sensitif ke dalam repository GitHub.

---

# 🗺️ Roadmap

## Version 1.x

### Core

* [x] Dashboard
* [x] Task Management
* [x] Project Management
* [x] Finance Tracker
* [x] Learning Tracker
* [x] Habit Tracker
* [x] Calendar
* [x] Backup & Restore
* [x] Notification System

### Productivity

* [ ] Advanced Focus Mode
* [ ] Pomodoro
* [ ] Habit Analytics
* [ ] Advanced Statistics
* [ ] Productivity Score

### Intelligence

* [ ] AI Activity Analysis
* [ ] AI Task Prioritization
* [ ] Smart Scheduling
* [ ] Goal Prediction
* [ ] Personal AI Assistant

### Platform

* [ ] Improved responsive UI
* [ ] Widget
* [ ] Wear OS integration
* [ ] Cloud synchronization
* [ ] Multi-device synchronization

---

# 🤖 Future AI Architecture

Salah satu arah pengembangan MASTER AKTIVITAS adalah mengubah aplikasi dari sekadar **activity tracker** menjadi **personal intelligence system**.

Konsep:

```text
                    MASTER AKTIVITAS
                           │
                           ▼
                    ┌─────────────┐
                    │ User Data   │
                    └──────┬──────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
       Tasks           Finance          Learning
          │                │                │
          └────────────────┼────────────────┘
                           ▼
                    ┌─────────────┐
                    │ AI Analysis │
                    └──────┬──────┘
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
         Recommend     Predict       Analyze
              │            │            │
              └────────────┼────────────┘
                           ▼
                    ┌─────────────┐
                    │   User      │
                    │  Decision   │
                    └─────────────┘
```

Tujuan akhirnya:

> **Data → Information → Analysis → Decision → Action → Feedback**

---

# 🧠 Design Principle

MASTER AKTIVITAS bukan hanya aplikasi untuk membuat checklist.

Konsep utamanya adalah membangun **Personal Operating System** yang mampu menghubungkan:

```text
                 LIFEOS
                   │
       ┌───────────┼───────────┐
       │           │           │
     WORK       LEARNING     FINANCE
       │           │           │
       └───────────┼───────────┘
                   │
                HABITS
                   │
                GOALS
                   │
              PERFORMANCE
```

Setiap aktivitas menghasilkan data.

Data tersebut kemudian dapat digunakan untuk:

```text
Track
 ↓
Measure
 ↓
Analyze
 ↓
Optimize
```

---

# 🤝 Contributing

Contribution dapat dilakukan melalui workflow GitHub:

```text
Fork
 ↓
Create Branch
 ↓
Develop
 ↓
Test
 ↓
Commit
 ↓
Push
 ↓
Pull Request
```

Contoh:

```bash
git checkout -b feature/new-feature

git add .

git commit -m "feat: add new feature"

git push origin feature/new-feature
```

---

# 📄 License

Project ini dibuat untuk penggunaan pribadi.

```text
Copyright (c) 2026 Master Aktivitas
```

---

<div align="center">

# ⚡ MASTER AKTIVITAS

### Your Life. Your Data. Your System.

**Plan • Execute • Track • Analyze • Improve**

<br>

<a href="https://github.com/r3p4lo/MasterAktivitas">
<img src="https://img.shields.io/badge/View_on-GitHub-181717?style=for-the-badge&logo=github">
</a>

<br><br>

<img src="https://img.shields.io/badge/Kotlin-1.9.22-7F52FF?style=flat-square&logo=kotlin">
<img src="https://img.shields.io/badge/Android-API%2026%20--%2034-3DDC84?style=flat-square&logo=android">
<img src="https://img.shields.io/badge/Compose-Material%203-4285F4?style=flat-square">
<img src="https://img.shields.io/badge/Room-SQLite-003B57?style=flat-square">
<img src="https://img.shields.io/badge/MVVM-Architecture-FF6F00?style=flat-square">

</div>

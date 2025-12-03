# FlowList - Smart Task & Goal Management

A multi-modular Android application for managing recurring tasks, standalone to-dos, and project-based goals with comprehensive statistics tracking.

## ✨ Features

### 🔄 Recurring Tasks
- Create and manage repeating tasks with custom schedules
- **Individual statistics per task** with completion percentage tracking
- **Weekly pattern analysis** - visual bar charts showing completed vs. incomplete tasks for each day of the week
- Identify which days you're most productive with your recurring habits
- **Reliable reminders** using Android AlarmManager - works even when app is closed
- All notifications are automatically rescheduled after device reboot, ensuring no reminders are missed.

### ✅ Standalone Tasks
- Quick one-time task management
- **Unified statistics dashboard** tracking all standalone tasks together
- Overall completion rate monitoring
- **Alarm-based reminders** for important deadlines

### 📊 Projects
- Organize large goals into projects with multiple subtasks
- **Per-project statistics** to monitor individual project progress
- **General statistics view** aggregating all projects
- Switch between individual and overall project insights
- **Task reminders** for project deadlines

### 🔔   Notifications & Reminders

- Reliable reminders using Android AlarmManager
- All notifications are automatically rescheduled after device reboot, ensuring no reminders are missed
- Applies to recurring tasks, standalone tasks, and project deadlines
- Supports actionable notifications (Done, Skip, Delay) with consistent database updates

### 🔐 Security

- Biometric authentication for app access
- Fingerprint and face unlock support

###   🌗 Appearance & Language

- Dark and Light Mode that follows your system settings automatically
- Option to manually select your preferred theme
- Language selection: switch between Polish and English
- All strings are fully localized in strings.xml, ensuring a consistent experience in both languages

## 🏗️ Architecture

FlowList follows **Clean Architecture** principles with a **multi-modular** structure, ensuring separation of concerns, testability, and maintainability.
```
┌─────────────────────────────────────────────────────────────────┐
│                         app module                              │
│                  (MainActivity, entry point)                    │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ depends on
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Feature Modules                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐           │
│  │   feature/   │  │   feature/   │  │   feature/   │           │
│  │  recurring   │  │     task     │  │   project    │           │
│  └──────────────┘  └──────────────┘  └──────────────┘           │
│  ┌──────────────┐  ┌──────────────┐                             │
│  │   feature/   │  │   security   │                             │
│  │   settings   │  │  (biometric) │                             │
│  └──────────────┘  └──────────────┘                             │
│              (Jetpack Compose UI Screens)                       │
│  * feature/security depends only on core/resources              │
│  * Other UI features depend on core/ui and core/viewmodel       │    
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ depends on
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Core Modules                             │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────────────┐      │
│  │   core/ui    │  │ core/viewModel│ │core/notification  │      │
│  │              │  │               │ │(internal via Hilt)│      │
│  │(shared UI)   │  │(HiltViewModel)│ │(AlarmManager)     │      │
│  └──────────────┘  └──────────────┘  └───────────────────┘      │
│  ┌──────────────────────────────┐                               │
│  │ core/resources               │                               │
│  │ (strings, colors, drawables) │                               │
│  └──────────────────────────────┘                               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ depends on
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       domain module                             │
│                                                                 │
│  • Repository Interfaces                                        │
│  • Use Case Contracts                                           │
│  • Domain Models                                                │
│  • Coordinator Interfaces                                       │
└─────────────────────────────────────────────────────────────────┘
          ▲                                           ▲
          │ implements                                │ implements
          │                                           │
┌─────────────────────────┐              ┌────────────────────────┐
│   core-impl module      │              │     data module        │
│  (internal visibility)  │              │                        │
│                         │              │  • Room Database       │
│  • Dagger Hilt Modules  │              │  • DAOs                │
│  • Use Case Impls       │              │  • Entity Models       │
│  • Coordinator Impls    │              │  • Repository Impls    │
│                         │              │  • Mappers             │
│                         │              │  • DI Modules          │
└─────────────────────────┘              └────────────────────────┘
```

## 🎯 Architecture Highlights

### Multi-Module Design
- **Feature modules** are independent and contain UI screens for specific functionality
- **Core modules** provide shared utilities and base components
- **Domain module** defines contracts and business models (pure Kotlin, no Android dependencies)
- **Core-impl module** bridges domain contracts with use case implementations (internal visibility)
- **Data module** handles persistence with Room and implements repository interfaces

### Dependency Flow
- Features depend on core modules and domain
- Core-impl implements use cases and coordinators from domain
- Data module implements repositories from domain independently
- Only the app module imports core-impl, keeping implementations hidden
- Domain module has no dependencies (pure business logic)

### Key Patterns

**Use Case Pattern with Action Groups**
- Use cases are grouped into action classes
- Each action group contains related operations (add, get, update, remove)
- Injected as single dependencies, reducing boilerplate

**Coordinator Pattern for Notifications**
- Coordinators handle notification logic and actions (Done, Skip, Delay)
- Interface-based design allows UI and notifications to use the same logic
- Actions update both the database and completion history seamlessly

**Repository Pattern**
- Clean separation between domain contracts and data implementation
- Flow-based reactive data streams
- Entity-to-Domain mapping keeps Room details isolated
- Repository implementations live in data module with DI bindings

**Internal Implementation Hiding**
- All implementations are marked `internal`
- Only domain interfaces are exposed to feature modules
- Enforces dependency inversion and prevents implementation leakage

## 🚀 Benefits

FlowList’s architecture is designed for **scalability, maintainability, and reliability**. Key benefits include:

* **Modular & Scalable:** Independent feature modules allow faster builds, parallel development, and easier future expansion or dynamic feature integration.
* **Clean Separation of Concerns:** Domain layer is pure Kotlin with no Android dependencies. Features only depend on contracts, preventing tight coupling and improving testability.
* **Reactive & Responsive:** Repository pattern with Flow ensures real-time updates, smooth UI, and consistent data handling across tasks and projects.
* **Centralized Logic:** Coordinators manage notifications and task actions consistently, reducing duplication and simplifying maintenance.
* **Modern & Reliable Tech Stack:** Kotlin, Jetpack Compose, Coroutines, Hilt, AlarmManager, and Biometric API provide a secure, efficient, and modern development experience.


## 🛠️ Technologies

- **Kotlin** - 100% Kotlin codebase
- **Jetpack Compose** - Modern declarative UI
- **Compose Navigation** - Type-safe navigation between feature screens
- **Room Database** - Local data persistence with Flow support
- **Dagger Hilt** - Compile-time dependency injection
    - HiltViewModel for ViewModel injection
    - HiltWorker for background tasks
    - Custom modules for domain bindings
- **Coroutines & Flow** - Asynchronous programming and reactive streams
- **AlarmManager** - Reliable task reminders independent of push notifications
- **Biometric API** - Secure authentication with fingerprint/face unlock
- **Multi-Module Architecture** - Feature-based 

## License
MIT License - Feel free to use this code for learning or inspiration!
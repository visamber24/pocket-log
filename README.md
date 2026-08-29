# PocketLog 💰

PocketLog is a native Android expense-tracking app that lets users manage multiple accounts, categorize income/expenses, and — its standout feature — **scan a physical receipt and have AI auto-fill the transaction** instead of typing it in manually.

## Overview

The app is built around a simple mental model: a user has one or more **Accounts** (cash, debit card, credit card, UPI, etc.), and every **Transaction** (credit, debit, or transfer) is linked to an account and a **Category** (food, travel, bills, etc.). On top of that CRUD foundation sits an AI-assisted capture flow: point the camera at a receipt, and the app extracts the amount, category, and description for you.

## Core Features

- **Authentication** — email/password signup, login, OTP-based verification, and password reset, backed by Firebase Auth
- **Accounts** — create, edit, and track balances across multiple account types (Cash, Debit Card, Credit Card, UPI, UPI Lite)
- **Transactions** — log income/expenses/transfers with amount, account, category, note, and description; edit or view details later
- **Categories** — custom categories with icons, each tagged as income or expense
- **Dashboard & Stats** — an overview screen summarizing balances and spending, plus a dedicated stats screen
- **AI Receipt Scanning** (the highlight feature):
  1. Capture a receipt photo using **CameraX**
  2. Run **ML Kit Text Recognition** on the image to extract raw text (OCR)
  3. Send that text to **Firebase AI (Gemini)** with a structured prompt asking it to return clean JSON — amount, category, note, description, and a matching icon
  4. Pre-fill the "Add Transaction" screen with the AI's response so the user just needs to confirm and save
- **Offline-first** — all app data lives in a local Room database, so the app works without a network connection (only login and the AI scan feature need connectivity)

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM (ViewModel + UI State + Repository pattern) |
| Dependency Injection | Koin |
| Local Database | Room (with a separate Gradle module) |
| Authentication | Firebase Auth |
| Camera | CameraX |
| OCR | Google ML Kit — Text Recognition |
| AI / Receipt Parsing | Firebase AI (Gemini model) |
| Navigation | Navigation Compose |
| Image Loading | Coil |
| Serialization | Kotlinx Serialization |

## Architecture

The app follows an **MVVM** pattern with a repository layer sitting between the ViewModels and Room:

```
UI (Compose Screens)
   ↓ observes
ViewModel (per screen — holds UI state, handles user actions)
   ↓ calls
Repository (interface + Offline/Room-backed implementation)
   ↓ queries
DAO (Room) ──→ SQLite (local, on-device)
```

Koin wires up the database, DAOs, repositories, and ViewModels as singletons/factories in a single `AppModule`, so each screen's ViewModel receives its dependencies via constructor injection rather than instantiating them itself.

### Data Model

| Entity | Purpose |
|---|---|
| `User` | Local user record, linked to Firebase UID |
| `Account` | A wallet/bank/card the user tracks, with a type and running balance |
| `Category1` | A named, iconized category, tagged `INCOME` or `EXPENSE` |
| `Transaction` | A single credit/debit/transfer entry linked to an account + category, with amount, note, description, and timestamp |

### AI Extraction Flow

The `GeminiModel` class sends the OCR'd receipt text to a Gemini model with a strict prompt: return **only** valid JSON (no markdown, no explanation) containing the amount, category, note, description, and a category icon chosen from a fixed set. This keeps the AI's output easy to parse directly into the app's data classes and pre-fill the transaction form reliably.

## Project Structure

```
pocket-log/
├── app/                     # Main application module
│   ├── data/                # Room entities (Account, Category, Transaction, User)
│   ├── database/            # DAOs and repository implementations
│   ├── di/                  # Koin dependency injection setup
│   └── ui/
│       ├── screen/
│       │   ├── authentication/   # Login, signup, OTP, password reset
│       │   ├── home/              # Dashboard, accounts, categories, stats, profile
│       │   ├── contentscreen/     # Add/edit account, category, transaction screens
│       │   └── other/             # Camera capture, ML Kit OCR, Gemini AI, settings
│       └── theme/            # Compose theming
└── database/                 # Standalone Room database Gradle module
```

## Getting Started

### Prerequisites

- Android Studio (latest stable)
- A Firebase project with **Authentication** and **Firebase AI (Gemini)** enabled

### Setup

1. Clone the repository.
2. Create a Firebase project and download its `google-services.json` file.
3. Place `google-services.json` inside the `app/` directory (this file is required for both login and the AI receipt-scanning feature — it is not committed to the repo).
4. Open the project in Android Studio and let Gradle sync.
5. Run the app on an emulator or physical device (camera access is required to test the receipt-scanning feature).

## Why This Project

PocketLog started as a straightforward expense tracker and became a way to explore how on-device camera input, OCR, and generative AI can be chained together to remove manual data entry — turning "type out every expense" into "snap a photo and confirm." It also served as a deep dive into structuring a multi-module Android app with Room, Koin, and Jetpack Compose end-to-end.

# Adivan — Tribal Scholarships

Monorepo for the **Adivan** prototype: Android app (tribal scholarship management) and the **JAGO** OpenAI proxy backend.

| Component | Path | Description |
|-----------|------|-------------|
| Android app | Repository root (`app/`, Gradle files) | Kotlin, Jetpack Compose, Material 3 |
| JAGO backend | `jago-backend/` | Node.js API for the in-app chatbot |

**Application ID:** `com.example.adivan`

## Project structure

```
Adivan_Scholarship/          # clone root (open this folder in Android Studio)
├── app/                     # Android application module
├── gradle/
├── jago-backend/            # JAGO chat API (Express + OpenAI)
│   ├── server.js
│   ├── package.json
│   └── README.md            # Backend-only docs
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── README.md                # This file
```

## Clone the repository

```bash
git clone https://github.com/TaranjitSingh05/Adivan_Scholarship.git
cd Adivan_Scholarship
```

## Run the Android app

1. Install **Android Studio** (JDK 11+).
2. **File → Open** → select the **repository root** (the folder that contains `app/` and `jago-backend/`).
3. Wait for Gradle sync.
4. Use an emulator (API 24+) or a physical device.
5. Run the **app** configuration.

From the command line (with `JAVA_HOME` set):

```bash
./gradlew assembleDebug
```

Debug APK: `app/build/outputs/apk/debug/`

### JAGO API URL on a physical device

The app talks to the backend over HTTP. For local development, point the app at your machine’s LAN IP or a deployed backend (see `jago-backend/README.md` and Android `JagoEndpoint` / Gradle `jago.dev.host` in `local.properties`).

## Run the JAGO backend

```bash
cd jago-backend
cp .env.example .env
# Edit .env and set OPENAI_API_KEY (never commit .env)
npm install
npm start
```

- Health: `GET http://localhost:3000/health`
- Chat: `POST http://localhost:3000/api/chat`

Details: [jago-backend/README.md](jago-backend/README.md)

## Security

- **Do not commit** `local.properties`, `.env`, or API keys.
- The OpenAI key belongs **only** on the server (`OPENAI_API_KEY` in `jago-backend/.env` or your host’s environment).

## Tech stack (Android)

| Area | Choice |
|------|--------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation Compose |
| Min / target SDK | 24 / 37 |

## Team workflow

1. Clone once from GitHub.
2. Open the **repo root** in Android Studio for app work.
3. Run `jago-backend` separately when testing JAGO chat (local or deployed URL).
4. Keep secrets in `.env` / `local.properties` (gitignored).

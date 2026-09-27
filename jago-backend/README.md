# Adivan JAGO Backend

OpenAI proxy for the **JAGO** scholarship assistant in the Adivan Android app. The mobile client calls this service; the OpenAI API key stays on the server only.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/health` | Health check (`{ "status": "ok" }`) |
| `POST` | `/api/chat` | Chat completion (see below) |

### `POST /api/chat`

**Request**

```json
{
  "message": "What documents do I need?",
  "conversation": [
    { "role": "user", "content": "Hello" },
    { "role": "assistant", "content": "Hi! How can I help?" }
  ],
  "appContext": {}
}
```

**Response**

```json
{
  "reply": "..."
}
```

## Local setup

1. Install [Node.js](https://nodejs.org/) 18 or newer.
2. Copy the example env file and set your key:

   ```bash
   cp .env.example .env
   ```

   Edit `.env` and set `OPENAI_API_KEY`. **Do not commit `.env`.**

3. Install dependencies and start:

   ```bash
   npm install
   npm start
   ```

4. Verify:

   ```bash
   curl http://localhost:3000/health
   ```

The server listens on `0.0.0.0` and uses `process.env.PORT || 3000`.

## Environment variables

| Variable | Required | Description |
|----------|----------|-------------|
| `OPENAI_API_KEY` | Yes | OpenAI API key (server only) |
| `PORT` | No | HTTP port (default `3000`; Render sets this automatically) |

## Deploy on Render (Web Service)

1. Push this repository to GitHub.
2. In [Render](https://render.com/), create a **Web Service** connected to the repo.
3. **Build command:** `npm install`
4. **Start command:** `npm start`
5. Add environment variable `OPENAI_API_KEY` in the Render dashboard (not in the repo).
6. After deploy, use the Render URL as the Android app’s JAGO base URL (e.g. `https://your-service.onrender.com`).

## Security

- Never put `OPENAI_API_KEY` in source code, Git, or the Android app.
- `.env` is listed in `.gitignore` and must not be pushed.

require("dotenv").config();

const express = require("express");
const cors = require("cors");
const OpenAI = require("openai");

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json({ limit: "64kb" }));

app.use((err, _req, res, next) => {
  if (err instanceof SyntaxError && err.status === 400 && "body" in err) {
    return res.status(400).json({ error: "Invalid JSON" });
  }
  return next(err);
});

const MAX_CONVERSATION_MESSAGES = 20;
const MAX_MESSAGE_LENGTH = 2000;

function buildSystemPrompt(appContext) {
  const ctx = appContext && typeof appContext === "object" ? appContext : {};
  const screen = ctx.screenContext || "home";

  return `You are JAGO, the scholarship assistant inside the Adivan mobile application.

Help tribal/ST students understand:
- Scholarship schemes
- Eligibility
- Required documents
- Application status
- Verification stages
- Sanction
- Disbursement
- General scholarship guidance

Be concise, friendly and easy to understand.

Do not invent scholarship policies, deadlines, amounts or government decisions.

Use the student's application context when provided. If a follow-up uses words like "it" or "its", resolve them from the conversation and this context.

The student is currently on the "${screen}" screen.

Application context from the app (source of truth for this prototype):
${JSON.stringify(ctx, null, 2)}

If asked about information that is not in this context, say that the information is not currently available in the app.`;
}

function validateChatRequest(body) {
  if (!body || typeof body !== "object") {
    return "Invalid request body";
  }
  const { message, conversation } = body;
  if (!message || typeof message !== "string" || !message.trim()) {
    return "message is required";
  }
  if (message.length > MAX_MESSAGE_LENGTH) {
    return "message is too long";
  }
  if (conversation !== undefined) {
    if (!Array.isArray(conversation)) {
      return "conversation must be an array";
    }
    for (const item of conversation) {
      if (
        !item ||
        typeof item !== "object" ||
        (item.role !== "user" && item.role !== "assistant") ||
        typeof item.content !== "string"
      ) {
        return "invalid conversation entry";
      }
    }
  }
  return null;
}

app.get("/health", (_req, res) => {
  res.json({ status: "ok" });
});

app.post("/api/chat", async (req, res) => {
  const validationError = validateChatRequest(req.body);
  if (validationError) {
    return res.status(400).json({ error: validationError });
  }

  const apiKey = process.env.OPENAI_API_KEY;
  if (!apiKey) {
    return res.status(503).json({
      error: "Chat service is not configured. Set OPENAI_API_KEY on the server.",
    });
  }

  const { message, conversation = [], appContext } = req.body;
  const trimmedMessage = message.trim();
  console.log(`POST /api/chat chars=${trimmedMessage.length}`);

  const recentConversation = conversation
    .slice(-MAX_CONVERSATION_MESSAGES)
    .map((m) => ({
      role: m.role,
      content: m.content.slice(0, MAX_MESSAGE_LENGTH),
    }));

  const messages = [
    { role: "system", content: buildSystemPrompt(appContext) },
    ...recentConversation,
    { role: "user", content: trimmedMessage },
  ];

  try {
    const openai = new OpenAI({ apiKey });
    const completion = await openai.chat.completions.create({
      model: "gpt-4o-mini",
      messages,
      max_tokens: 500,
      temperature: 0.4,
    });

    const reply = completion.choices[0]?.message?.content?.trim();
    if (!reply) {
      return res.status(502).json({ error: "Empty response from AI service" });
    }

    return res.json({ reply });
  } catch (err) {
    const status = err.status || err.response?.status;
    if (status === 401) {
      return res.status(503).json({ error: "AI service authentication failed" });
    }
    if (status === 429) {
      return res.status(503).json({ error: "AI service is busy. Please try again." });
    }
    const reason = err?.error?.message || err.message || "unknown error";
    console.error("OpenAI request failed:", reason);
    return res.status(502).json({
      error: "JAGO is temporarily unavailable. Please try again.",
    });
  }
});

app.listen(PORT, "0.0.0.0", () => {
  console.log(`JAGO backend listening on http://0.0.0.0:${PORT}`);
});

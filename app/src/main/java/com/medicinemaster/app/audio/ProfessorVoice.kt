package com.medicinemaster.app.audio

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

/** User preferences for Android's installed text-to-speech engine. */
data class VoicePreferences(
    val enabled: Boolean = true,
    val autoReadQuestions: Boolean = true,
    val readFeedback: Boolean = true,
    val speechRate: Float = 0.92f,
    val pitch: Float = 0.90f
)

object VoicePreferencesStore {
    private const val PREFS = "medicine_master_voice"

    fun load(context: Context): VoicePreferences {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return VoicePreferences(
            enabled = prefs.getBoolean("enabled", true),
            autoReadQuestions = prefs.getBoolean("auto_read_questions", true),
            readFeedback = prefs.getBoolean("read_feedback", true),
            speechRate = prefs.getFloat("speech_rate", 0.92f).coerceIn(0.75f, 1.25f),
            pitch = prefs.getFloat("pitch", 0.90f).coerceIn(0.75f, 1.25f)
        )
    }

    fun save(context: Context, preferences: VoicePreferences) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putBoolean("enabled", preferences.enabled)
            .putBoolean("auto_read_questions", preferences.autoReadQuestions)
            .putBoolean("read_feedback", preferences.readFeedback)
            .putFloat("speech_rate", preferences.speechRate.coerceIn(0.75f, 1.25f))
            .putFloat("pitch", preferences.pitch.coerceIn(0.75f, 1.25f))
            .apply()
    }
}

/** Thin lifecycle-aware wrapper around the TextToSpeech engine already installed on Android. */
class ProfessorVoice(
    context: Context,
    private val onStatusChanged: (ready: Boolean, message: String) -> Unit
) : TextToSpeech.OnInitListener {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val utteranceIds = AtomicInteger(0)
    private var engine: TextToSpeech? = null
    private var closed = false

    @Volatile
    var isReady: Boolean = false
        private set

    init {
        engine = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        val currentEngine = engine
        if (status != TextToSpeech.SUCCESS || currentEngine == null) {
            report(false, "No se pudo iniciar el motor de voz de Android.")
            return
        }

        val languageStatus = currentEngine.setLanguage(Locale("es"))
        if (languageStatus == TextToSpeech.LANG_MISSING_DATA ||
            languageStatus == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            report(false, "El motor de voz no tiene español instalado. Actívalo en los ajustes de Texto a voz de Android.")
            return
        }

        isReady = true
        report(true, "Motor de voz de Android listo · español disponible.")
    }

    fun speak(text: String, preferences: VoicePreferences): Boolean {
        val currentEngine = engine ?: return false
        if (!isReady || closed || text.isBlank()) return false

        currentEngine.setLanguage(Locale("es"))
        currentEngine.setSpeechRate(preferences.speechRate.coerceIn(0.75f, 1.25f))
        currentEngine.setPitch(preferences.pitch.coerceIn(0.75f, 1.25f))
        val result = currentEngine.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            Bundle(),
            "medicine_master_${utteranceIds.incrementAndGet()}"
        )
        return result != TextToSpeech.ERROR
    }

    fun stop() {
        engine?.stop()
    }

    fun shutdown() {
        if (closed) return
        closed = true
        isReady = false
        engine?.stop()
        engine?.shutdown()
        engine = null
    }

    private fun report(ready: Boolean, message: String) {
        mainHandler.post {
            if (!closed) onStatusChanged(ready, message)
        }
    }
}

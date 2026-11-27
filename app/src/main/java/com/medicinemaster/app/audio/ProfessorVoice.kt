package com.medicinemaster.app.audio

import android.content.Context
import android.media.AudioAttributes
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

/** Preferencias para el motor de voz instalado y configurado en Android. */
data class VoicePreferences(
    val enabled: Boolean = true,
    val autoReadQuestions: Boolean = true,
    val readFeedback: Boolean = true,
    val speechRate: Float = 1.0f,
    val pitch: Float = 1.0f
)

object VoicePreferencesStore {
    private const val PREFS = "medicine_master_voice"
    private const val PROFILE_VERSION = 2

    fun load(context: Context): VoicePreferences {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val previousProfile = prefs.getInt("voice_profile_version", 0)
        // La versión anterior guardaba velocidad y tono por debajo del nivel natural.
        val rate = if (previousProfile < PROFILE_VERSION) 1.0f else prefs.getFloat("speech_rate", 1.0f)
        val pitch = if (previousProfile < PROFILE_VERSION) 1.0f else prefs.getFloat("pitch", 1.0f)
        prefs.edit().putInt("voice_profile_version", PROFILE_VERSION)
            .putFloat("speech_rate", rate.coerceIn(0.85f, 1.15f))
            .putFloat("pitch", pitch.coerceIn(0.95f, 1.05f))
            .apply()
        return VoicePreferences(
            enabled = prefs.getBoolean("enabled", true),
            autoReadQuestions = prefs.getBoolean("auto_read_questions", true),
            readFeedback = prefs.getBoolean("read_feedback", true),
            speechRate = rate.coerceIn(0.85f, 1.15f),
            pitch = pitch.coerceIn(0.95f, 1.05f)
        )
    }

    fun save(context: Context, preferences: VoicePreferences) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putInt("voice_profile_version", PROFILE_VERSION)
            .putBoolean("enabled", preferences.enabled)
            .putBoolean("auto_read_questions", preferences.autoReadQuestions)
            .putBoolean("read_feedback", preferences.readFeedback)
            .putFloat("speech_rate", preferences.speechRate.coerceIn(0.85f, 1.15f))
            .putFloat("pitch", preferences.pitch.coerceIn(0.95f, 1.05f))
            .apply()
    }
}

/** Wrapper lifecycle-aware para Android TextToSpeech. No incorpora voces ni servicios externos. */
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

        currentEngine.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )

        val languageStatus = currentEngine.setLanguage(Locale("es"))
        if (languageStatus == TextToSpeech.LANG_MISSING_DATA ||
            languageStatus == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            report(false, "El motor de voz no tiene español instalado. Instala una voz española en los ajustes de Texto a voz de Android.")
            return
        }

        // Cuando el motor ofrece varias voces españolas, prefiere una voz local de mayor calidad.
        chooseBestSpanishVoice(currentEngine)
        isReady = true
        report(true, "Motor de voz Android listo · español. Se utiliza la mejor voz española disponible en el motor.")
    }

    private fun chooseBestSpanishVoice(currentEngine: TextToSpeech) {
        val bestVoice: Voice? = runCatching {
            currentEngine.voices
                ?.filter { it.locale.language.equals("es", ignoreCase = true) }
                ?.maxWithOrNull(
                    compareBy<Voice> { it.quality }
                        .thenBy { if (it.isNetworkConnectionRequired) 0 else 1 }
                )
        }.getOrNull()
        if (bestVoice != null) runCatching { currentEngine.voice = bestVoice }
    }

    fun speak(text: String, preferences: VoicePreferences): Boolean {
        val currentEngine = engine ?: return false
        if (!isReady || closed || text.isBlank()) return false

        currentEngine.setSpeechRate(preferences.speechRate.coerceIn(0.85f, 1.15f))
        currentEngine.setPitch(preferences.pitch.coerceIn(0.95f, 1.05f))
        val polishedText = text
            .replace("ADN", "A D N")
            .replace("ARN", "A R N")
            .replace("ATP", "A T P")
            .replace("ADH", "A D H")
            .replace("CO₂", "dióxido de carbono")
            .replace("Na⁺", "sodio")
            .replace("K⁺", "potasio")
            .replace(Regex("\\s+"), " ")
            .trim()
        val result = currentEngine.speak(
            polishedText,
            TextToSpeech.QUEUE_FLUSH,
            Bundle(),
            "medicine_master_${utteranceIds.incrementAndGet()}"
        )
        return result != TextToSpeech.ERROR
    }

    fun stop() { engine?.stop() }

    fun shutdown() {
        if (closed) return
        closed = true
        isReady = false
        engine?.stop()
        engine?.shutdown()
        engine = null
    }

    private fun report(ready: Boolean, message: String) {
        mainHandler.post { if (!closed) onStatusChanged(ready, message) }
    }
}

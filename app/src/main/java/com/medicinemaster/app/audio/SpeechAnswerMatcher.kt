package com.medicinemaster.app.audio

import java.text.Normalizer
import java.util.Locale

/** Converts Android speech-recognition text into a multiple-choice option index. */
object SpeechAnswerMatcher {
    private val letterWords = mapOf(
        "a" to 0, "be" to 1, "ce" to 2, "de" to 3
    )
    private val ordinalWords = mapOf(
        "primera" to 0, "primero" to 0, "uno" to 0, "una" to 0, "1" to 0, "1a" to 0, "1ra" to 0,
        "segunda" to 1, "segundo" to 1, "dos" to 1, "2" to 1, "2a" to 1, "2da" to 1,
        "tercera" to 2, "tercero" to 2, "tres" to 2, "3" to 2, "3a" to 2, "3ra" to 2,
        "cuarta" to 3, "cuarto" to 3, "cuatro" to 3, "4" to 3, "4a" to 3, "4ta" to 3
    )

    fun match(transcript: String, options: List<String>): Int? {
        if (transcript.isBlank() || options.isEmpty()) return null
        val text = normalize(transcript)
        if (text.isBlank()) return null

        // Explicit references are the safest way to identify an option.
        val explicitLetter = Regex("\\b(?:opcion|respuesta|alternativa|letra)\\s+(?:numero\\s+)?(a|be|ve|b|ce|c|d)\\b")
            .find(text)?.groupValues?.getOrNull(1)
        if (explicitLetter != null) {
            val index = when (explicitLetter) {
                "a" -> 0
                "be", "ve", "b" -> 1
                "ce", "c" -> 2
                else -> 3
            }
            return index.takeIf { it in options.indices }
        }
        if (Regex("\\bletra\\s+de\\b").containsMatchIn(text)) {
            return 3.takeIf { it in options.indices }
        }

        val explicitOrdinal = Regex(
            "\\b(?:opcion|respuesta|alternativa)\\s+(?:numero\\s+)?(primera?|primero|segunda?|segundo|tercera?|tercero|cuarta?|cuarto|uno|una|dos|tres|cuatro|1(?:a|ra)?|2(?:a|da)?|3(?:a|ra)?|4(?:a|ta)?)\\b"
        ).find(text)?.groupValues?.getOrNull(1)
        if (explicitOrdinal != null) {
            return ordinalWords[explicitOrdinal]?.takeIf { it in options.indices }
        }

        // Accept a bare letter/ordinal only when it is essentially the whole utterance.
        val shortAnswer = text.removePrefix("la ").trim()
        if (shortAnswer in letterWords && shortAnswer.length <= 2) {
            return letterWords[shortAnswer]?.takeIf { it in options.indices }
        }
        if (shortAnswer in ordinalWords) {
            return ordinalWords[shortAnswer]?.takeIf { it in options.indices }
        }

        val normalizedOptions = options.map(::normalize)
        normalizedOptions.forEachIndexed { index, option ->
            if (option.isNotBlank() && (text == option || text.contains(option))) return index
        }

        // For a spoken paraphrase, require a strong and clearly leading token overlap.
        val ignored = setOf("la", "el", "los", "las", "un", "una", "de", "del", "que", "es", "y", "o", "por", "para", "con", "a", "se", "en", "respuesta", "opcion")
        val spokenTokens = text.split(' ').filter { it.length > 2 && it !in ignored }.toSet()
        if (spokenTokens.size < 2) return null

        val scores = normalizedOptions.map { option ->
            val optionTokens = option.split(' ').filter { it.length > 2 && it !in ignored }.toSet()
            if (optionTokens.isEmpty()) 0f else {
                val overlap = spokenTokens.intersect(optionTokens).size.toFloat()
                overlap / optionTokens.size.toFloat()
            }
        }
        val best = scores.indices.maxByOrNull { scores[it] } ?: return null
        val bestScore = scores[best]
        val secondScore = scores.indices.filter { it != best }.maxOfOrNull { scores[it] } ?: 0f
        return best.takeIf { bestScore >= 0.80f && bestScore - secondScore >= 0.15f }
    }

    private fun normalize(value: String): String = Normalizer
        .normalize(value.lowercase(Locale.ROOT), Normalizer.Form.NFD)
        .replace(Regex("\\p{Mn}+"), "")
        .replace(Regex("[^a-z0-9 ]"), " ")
        .replace(Regex("\\s+"), " ")
        .trim()
}

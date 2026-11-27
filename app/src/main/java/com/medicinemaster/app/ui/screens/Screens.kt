package com.medicinemaster.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SettingsVoice
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicinemaster.app.audio.ProfessorVoice
import com.medicinemaster.app.audio.ProfessorDialogue
import com.medicinemaster.app.audio.VoicePreferences
import com.medicinemaster.app.audio.VoicePreferencesStore
import com.medicinemaster.app.data.ProgressStore
import com.medicinemaster.app.data.QuestionBank
import com.medicinemaster.app.data.StudyQuestion
import com.medicinemaster.app.data.StudySubject
import com.medicinemaster.app.data.StudySubjects

private val CardShape = RoundedCornerShape(22.dp)

@Composable
private fun ScreenFrame(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        content = content
    )
}

@Composable
private fun ScreenTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SectionLabel(text: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        if (action != null && onAction != null) {
            Text(
                action,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.clickable(onClick = onAction)
            )
        }
    }
}

@Composable
private fun IconTile(icon: ImageVector) {
    Surface(shape = RoundedCornerShape(15.dp), color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.size(48.dp)) {
        Box(contentAlignment = Alignment.Center) {
            androidx.compose.material3.Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
private fun ActionTile(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            IconTile(icon)
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
            androidx.compose.material3.Icon(Icons.Outlined.ChevronRight, contentDescription = "Abrir", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun HomeScreen(
    onOpenSubjects: () -> Unit,
    onOpenStudy: () -> Unit,
    onOpenProgress: () -> Unit,
    onOpenSettings: () -> Unit
) {
    ScreenFrame {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.primary) {
                androidx.compose.material3.Icon(
                    Icons.Outlined.MonitorHeart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(10.dp).size(25.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("MEDICINE MASTER", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.2.sp, fontSize = 15.sp)
                Text("Tu entrenamiento médico", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
            }
            Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.clickable(onClick = onOpenSettings)) {
                androidx.compose.material3.Icon(Icons.Outlined.SettingsVoice, contentDescription = "Configuración", modifier = Modifier.padding(11.dp))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("Aprende. Recuerda.\nDemuestra lo que sabes.", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, lineHeight = 36.sp)
            Text("126 preguntas con explicaciones, fuentes y narración del profesor.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Card(shape = RoundedCornerShape(26.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)) {
                        androidx.compose.material3.Icon(Icons.Outlined.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(Modifier.width(9.dp))
                    Text("TU PRÓXIMA SESIÓN", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Text("Empieza por lo esencial", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Selecciona una materia, un tema y una dificultad. La aplicación corregirá cada respuesta y explicará el concepto.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                Button(onClick = onOpenStudy, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(15.dp)) {
                    androidx.compose.material3.Icon(Icons.Outlined.Book, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Comenzar a estudiar", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    androidx.compose.material3.Icon(Icons.Outlined.ArrowForward, contentDescription = null)
                }
            }
        }

        SectionLabel("Explora la aplicación")
        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            ActionTile(Icons.Outlined.MenuBook, "Materias", "Seis asignaturas con banco ampliado", onOpenSubjects)
            ActionTile(Icons.Outlined.Quiz, "Sesión de estudio", "Practica y recibe explicaciones", onOpenStudy)
            ActionTile(Icons.Outlined.TrendingUp, "Mi progreso", "Consulta tus sesiones y aciertos", onOpenProgress)
        }

        Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(18.dp)) {
            Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Spacer(Modifier.width(10.dp))
                Text(
                    "Contenido formativo inicial. Contrasta siempre el material con la bibliografía de tu programa académico.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SubjectsScreen(onStartSubject: (String) -> Unit) {
    ScreenFrame {
        ScreenTitle("Materias", "Elige el área que quieres dominar.")
        Surface(shape = CardShape, color = MaterialTheme.colorScheme.surfaceVariant) {
            Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.Icon(Icons.Outlined.MenuBook, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(25.dp), contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Biblioteca de estudio", fontWeight = FontWeight.SemiBold)
                    Text("Cada pregunta incluye respuesta, explicación y referencia.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        StudySubjects.all.forEach { subject ->
            SubjectCard(subject = subject, onClick = { onStartSubject(subject.id) })
        }
    }
}

@Composable
private fun SubjectCard(subject: StudySubject, onClick: () -> Unit) {
    val icon = when (subject.id) {
        StudySubjects.ANATOMY -> Icons.Outlined.MonitorHeart
        StudySubjects.PHYSIOLOGY -> Icons.Outlined.FavoriteBorder
        StudySubjects.PHARMACOLOGY -> Icons.Outlined.Science
        StudySubjects.PATHOLOGY -> Icons.Outlined.Biotech
        StudySubjects.MICROBIOLOGY -> Icons.Outlined.Psychology
        else -> Icons.Outlined.School
    }
    val count = QuestionBank.subjectCount(subject.id)
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconTile(icon)
            Spacer(Modifier.width(13.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(subject.name, fontWeight = FontWeight.Bold)
                Text(subject.description, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                Text("$count preguntas disponibles", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
            }
            androidx.compose.material3.Icon(Icons.Outlined.ChevronRight, contentDescription = "Estudiar ${subject.name}", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun StudyScreen(initialSubjectId: String = "all") {
    val context = LocalContext.current
    var voicePreferences by remember(context) { mutableStateOf(VoicePreferencesStore.load(context)) }
    var voiceReady by remember { mutableStateOf(false) }
    var voiceStatus by remember { mutableStateOf("Preparando el motor de voz de Android…") }
    val professorVoice = remember(context) {
        ProfessorVoice(context) { ready, message ->
            voiceReady = ready
            voiceStatus = message
        }
    }
    DisposableEffect(professorVoice) {
        onDispose { professorVoice.shutdown() }
    }
    var selectedSubject by rememberSaveable(initialSubjectId) { mutableStateOf(initialSubjectId) }
    var selectedTopic by rememberSaveable(initialSubjectId) { mutableStateOf("Todos los temas") }
    var selectedDifficulty by rememberSaveable { mutableStateOf("Todas") }
    var selectedCount by rememberSaveable { mutableStateOf(10) }
    var sessionQuestions by remember { mutableStateOf<List<StudyQuestion>>(emptyList()) }
    var questionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var submitted by rememberSaveable { mutableStateOf(false) }
    var correctCount by rememberSaveable { mutableStateOf(0) }
    var completed by rememberSaveable { mutableStateOf(false) }
    var statsSaved by rememberSaveable { mutableStateOf(false) }

    val matchingQuestions = QuestionBank.forFilters(selectedSubject, selectedTopic, selectedDifficulty)
    val availableTopics = listOf("Todos los temas") + QuestionBank.topicsFor(selectedSubject)
    val questionBeingPresented = sessionQuestions.getOrNull(questionIndex)

    LaunchedEffect(
        questionBeingPresented?.id,
        voiceReady,
        voicePreferences.enabled,
        voicePreferences.autoReadQuestions,
        submitted
    ) {
        if (questionBeingPresented != null && !submitted && voiceReady &&
            voicePreferences.enabled && voicePreferences.autoReadQuestions
        ) {
            professorVoice.speak(questionNarration(questionBeingPresented), voicePreferences)
        }
    }

    fun resetToSetup() {
        professorVoice.stop()
        sessionQuestions = emptyList()
        questionIndex = 0
        selectedOption = null
        submitted = false
        correctCount = 0
        completed = false
        statsSaved = false
    }

    ScreenFrame {
        if (sessionQuestions.isEmpty()) {
            ScreenTitle("Preparar sesión", "Personaliza tu práctica antes de empezar.")
            SectionLabel("1. Asignatura")
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = selectedSubject == "all", onClick = { selectedSubject = "all"; selectedTopic = "Todos los temas" }, label = { Text("Todas") })
                StudySubjects.all.forEach { subject ->
                    FilterChip(selected = selectedSubject == subject.id, onClick = { selectedSubject = subject.id; selectedTopic = "Todos los temas" }, label = { Text(subject.name) })
                }
            }

            SectionLabel("2. Tema")
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                availableTopics.forEach { topic ->
                    FilterChip(selected = selectedTopic == topic, onClick = { selectedTopic = topic }, label = { Text(topic) })
                }
            }

            SectionLabel("3. Dificultad")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todas", "Básica", "Intermedia", "Avanzada").forEach { difficulty ->
                    FilterChip(selected = selectedDifficulty == difficulty, onClick = { selectedDifficulty = difficulty }, label = { Text(difficulty) })
                }
            }

            SectionLabel("4. Número de preguntas")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(5, 10, 20, 50, 100, 150).forEach { amount ->
                    FilterChip(selected = selectedCount == amount, onClick = { selectedCount = amount }, label = { Text(amount.toString()) })
                }
            }

            Card(shape = CardShape, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.padding(17.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Preguntas disponibles: ${matchingQuestions.size}", fontWeight = FontWeight.Bold)
                    Text(
                        "La sesión elegirá hasta ${minOf(selectedCount, matchingQuestions.size)} preguntas sin repetirlas dentro de esta sesión.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = {
                            sessionQuestions = matchingQuestions.shuffled().take(selectedCount.coerceAtMost(matchingQuestions.size))
                            questionIndex = 0
                            selectedOption = null
                            submitted = false
                            correctCount = 0
                            completed = false
                            statsSaved = false
                        },
                        enabled = matchingQuestions.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        androidx.compose.material3.Icon(Icons.Outlined.Book, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Iniciar sesión", fontWeight = FontWeight.Bold)
                    }
                }
            }
            if (matchingQuestions.isEmpty()) {
                Text("No hay preguntas para esta combinación. Cambia el tema o la dificultad.", color = MaterialTheme.colorScheme.error)
            }
        } else if (completed) {
            val total = sessionQuestions.size
            val percent = if (total == 0) 0 else (correctCount * 100 / total)
            ScreenTitle("Sesión completada", "Terminaste las preguntas seleccionadas.")
            Card(shape = RoundedCornerShape(26.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    androidx.compose.material3.Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                    Text("$percent%", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.ExtraBold)
                    Text("$correctCount aciertos de $total preguntas", fontWeight = FontWeight.SemiBold)
                    Text("${total - correctCount} respuestas incorrectas", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    LinearProgressIndicator(progress = if (total == 0) 0f else correctCount.toFloat() / total, modifier = Modifier.fillMaxWidth().height(8.dp))
                }
            }
            if (statsSaved) {
                Text("Resultado guardado en este dispositivo.", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { resetToSetup() }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(14.dp)) {
                Text("Preparar otra sesión")
            }
        } else {
            val question = sessionQuestions[questionIndex]
            val total = sessionQuestions.size
            ScreenTitle("Sesión de estudio", "${question.subjectName} · ${question.topic}")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("PREGUNTA ${questionIndex + 1} DE $total", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text("Aciertos: $correctCount", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
            }
            LinearProgressIndicator(progress = (questionIndex + 1).toFloat() / total, modifier = Modifier.fillMaxWidth().height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = { professorVoice.speak(questionNarration(question), voicePreferences) },
                    enabled = voiceReady && voicePreferences.enabled,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    androidx.compose.material3.Icon(Icons.Outlined.Headphones, contentDescription = null)
                    Spacer(Modifier.width(7.dp))
                    Text("Escuchar pregunta")
                }
                OutlinedButton(
                    onClick = { professorVoice.stop() },
                    enabled = voiceReady,
                    shape = RoundedCornerShape(13.dp)
                ) { Text("Detener") }
            }
            if (!voiceReady) {
                Text(voiceStatus, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
            }

            Card(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = RoundedCornerShape(30.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)) {
                            Text(question.difficulty.uppercase(), modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                        Text(question.topic, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                    }
                    Text(question.prompt, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    question.options.forEachIndexed { index, answer ->
                        val isSelected = selectedOption == index
                        val isCorrectOption = submitted && index == question.correctIndex
                        val isWrongSelection = submitted && isSelected && index != question.correctIndex
                        val bg = when {
                            isCorrectOption -> MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                            isWrongSelection -> MaterialTheme.colorScheme.error.copy(alpha = 0.14f)
                            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                        val border = when {
                            isCorrectOption -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            isWrongSelection -> BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                            isSelected -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            else -> null
                        }
                        Surface(
                            modifier = Modifier.fillMaxWidth().clickable(enabled = !submitted) { selectedOption = index },
                            shape = RoundedCornerShape(14.dp),
                            color = bg,
                            border = border
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text("${'A' + index}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(13.dp))
                                Text(answer, modifier = Modifier.weight(1f), fontWeight = if (isSelected || isCorrectOption) FontWeight.Bold else FontWeight.Normal)
                                if (isCorrectOption) Text("✓", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                                else if (isWrongSelection) Text("✕", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }

                    if (submitted) {
                        val answerWasCorrect = selectedOption == question.correctIndex
                        Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
                            Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    if (answerWasCorrect) "RESPUESTA CORRECTA" else "RESPUESTA INCORRECTA",
                                    color = if (answerWasCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Respuesta: ${question.options[question.correctIndex]}", fontWeight = FontWeight.Bold)
                                Text(question.explanation, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                                OutlinedButton(
                                    onClick = { professorVoice.speak(explanationNarration(question, answerWasCorrect), voicePreferences) },
                                    enabled = voiceReady && voicePreferences.enabled,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    androidx.compose.material3.Icon(Icons.Outlined.Headphones, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Escuchar explicación del profesor")
                                }
                                Text(
                                    "Fuente: ${question.sourceLabel} · abrir referencia",
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.clickable {
                                        runCatching {
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(question.sourceUrl)))
                                        }
                                    }
                                )
                            }
                        }
                    }

                    if (!submitted) {
                        Button(
                            onClick = {
                                submitted = true
                                val answerWasCorrect = selectedOption == question.correctIndex
                                if (answerWasCorrect) correctCount += 1
                                if (voiceReady && voicePreferences.enabled && voicePreferences.readFeedback) {
                                    professorVoice.speak(feedbackNarration(question, answerWasCorrect), voicePreferences)
                                } else {
                                    professorVoice.stop()
                                }
                            },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) { Text("Comprobar respuesta") }
                    } else {
                        Button(
                            onClick = {
                                if (questionIndex < total - 1) {
                                    questionIndex += 1
                                    selectedOption = null
                                    submitted = false
                                } else {
                                    professorVoice.stop()
                                    completed = true
                                    if (!statsSaved) {
                                        ProgressStore.recordSession(context, total, correctCount)
                                        statsSaved = true
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) { Text(if (questionIndex < total - 1) "Siguiente pregunta" else "Ver resultados") }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressScreen() {
    val context = LocalContext.current
    val stats = ProgressStore.load(context)

    ScreenFrame {
        ScreenTitle("Mi progreso", "Resultados guardados en este dispositivo.")
        Card(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.material3.Icon(Icons.Outlined.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(38.dp))
                Text("${stats.accuracyPercent}%", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
                Text("Porcentaje global de aciertos", fontWeight = FontWeight.SemiBold)
                Text("Se calcula con todas las respuestas de sesiones terminadas.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(stats.sessions.toString(), "Sesiones", Icons.Outlined.MenuBook, Modifier.weight(1f))
            StatCard(stats.answered.toString(), "Preguntas", Icons.Outlined.Quiz, Modifier.weight(1f))
            StatCard(stats.correct.toString(), "Aciertos", Icons.Outlined.CheckCircle, Modifier.weight(1f))
        }
        if (stats.answered == 0) {
            Surface(color = MaterialTheme.colorScheme.surface, shape = CardShape) {
                Text("Todavía no hay resultados. Completa una sesión y tus estadísticas aparecerán aquí.", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            androidx.compose.material3.Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    var voicePreferences by remember(context) { mutableStateOf(VoicePreferencesStore.load(context)) }
    var voiceReady by remember { mutableStateOf(false) }
    var voiceStatus by remember { mutableStateOf("Preparando el motor de voz de Android…") }
    val professorVoice = remember(context) {
        ProfessorVoice(context) { ready, message ->
            voiceReady = ready
            voiceStatus = message
        }
    }
    DisposableEffect(professorVoice) {
        onDispose { professorVoice.shutdown() }
    }

    fun updateVoicePreferences(updated: VoicePreferences) {
        voicePreferences = updated
        VoicePreferencesStore.save(context, updated)
    }

    ScreenFrame {
        ScreenTitle("Configuración", "Ajusta la voz y la experiencia de estudio.")
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconTile(Icons.Outlined.Headphones)
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text("El profesor", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                        Text("Narración local con el motor de voz instalado en Android.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text("Narración activada", fontWeight = FontWeight.SemiBold)
                        Text("Permite que el profesor hable durante el estudio.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = voicePreferences.enabled, onCheckedChange = {
                        updateVoicePreferences(voicePreferences.copy(enabled = it))
                        if (!it) professorVoice.stop()
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text("Leer preguntas automáticamente", fontWeight = FontWeight.SemiBold)
                        Text("Lee el enunciado y sus cuatro opciones al aparecer.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = voicePreferences.autoReadQuestions,
                        onCheckedChange = { updateVoicePreferences(voicePreferences.copy(autoReadQuestions = it)) },
                        enabled = voicePreferences.enabled
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text("Leer correcciones", fontWeight = FontWeight.SemiBold)
                        Text("Pronuncia el veredicto y la explicación después de responder.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = voicePreferences.readFeedback,
                        onCheckedChange = { updateVoicePreferences(voicePreferences.copy(readFeedback = it)) },
                        enabled = voicePreferences.enabled
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Velocidad · ${String.format(java.util.Locale.getDefault(), "%.2f", voicePreferences.speechRate)}×", fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = voicePreferences.speechRate,
                        onValueChange = { updateVoicePreferences(voicePreferences.copy(speechRate = it)) },
                        valueRange = 0.85f..1.15f,
                        enabled = voicePreferences.enabled
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Tono · ${String.format(java.util.Locale.getDefault(), "%.2f", voicePreferences.pitch)}", fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = voicePreferences.pitch,
                        onValueChange = { updateVoicePreferences(voicePreferences.copy(pitch = it)) },
                        valueRange = 0.95f..1.05f,
                        enabled = voicePreferences.enabled
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Button(
                        onClick = {
                            professorVoice.speak(
                                ProfessorDialogue.settingsTest(),
                                voicePreferences
                            )
                        },
                        enabled = voiceReady && voicePreferences.enabled,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(13.dp)
                    ) { Text("Probar al profesor") }
                    OutlinedButton(onClick = { professorVoice.stop() }, enabled = voiceReady, shape = RoundedCornerShape(13.dp)) {
                        Text("Detener")
                    }
                }
                Text(
                    voiceStatus,
                    color = if (voiceReady) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    "La aplicación utiliza el motor instalado en Android y selecciona la mejor voz española disponible. Para mejorar el sonido, instala una voz española de alta calidad en Ajustes → Administración general → Texto a voz.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        SectionLabel("Próximas funciones")
        SettingsRow(Icons.Outlined.Mic, "Reconocimiento de voz", "En pausa; esta versión se centra en el banco de preguntas y la narración")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.AutoAwesome, "Respuestas abiertas", "Evaluación de respuestas habladas: Fase 5")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.School, "Personalidad del profesor", "Textos gruñones y sarcásticos integrados en la narración; sin IA externa")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.Info, "Acerca de", "Medicine Master · versión 0.5.0 · banco ampliado y profesor sarcástico")
        Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = CardShape) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Importante", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                Text("Esta aplicación es una herramienta educativa. El contenido inicial incluye referencias bibliográficas para revisión; no sustituye la formación clínica ni el criterio profesional.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

private fun questionNarration(question: StudyQuestion): String = ProfessorDialogue.question(question)

private fun feedbackNarration(question: StudyQuestion, correct: Boolean): String =
    ProfessorDialogue.feedback(question, correct)

private fun explanationNarration(question: StudyQuestion, correct: Boolean): String =
    ProfessorDialogue.explanation(question, correct)

@Composable
private fun SettingsRow(icon: ImageVector, title: String, detail: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        IconTile(icon)
        Spacer(Modifier.width(13.dp))
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(detail, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        }
    }
}

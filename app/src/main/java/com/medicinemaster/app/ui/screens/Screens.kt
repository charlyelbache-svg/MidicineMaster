package com.medicinemaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.SettingsVoice
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
private fun ScreenTitle(
    title: String,
    subtitle: String,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        trailing?.invoke()
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
private fun IconTile(icon: ImageVector, tint: Color = MaterialTheme.colorScheme.primary) {
    Surface(
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.size(48.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
private fun ActionTile(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            Icon(Icons.Outlined.ChevronRight, contentDescription = "Abrir", tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                Icon(
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
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable(onClick = onOpenSettings)
            ) {
                Icon(Icons.Outlined.SettingsVoice, contentDescription = "Configuración", modifier = Modifier.padding(11.dp))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("Aprende. Recuerda.\nDemuestra lo que sabes.", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, lineHeight = 36.sp)
            Text("Medicina explicada sin rodeos.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)) {
                        Icon(Icons.Outlined.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(8.dp))
                    }
                    Spacer(Modifier.width(9.dp))
                    Text("TU PRÓXIMA SESIÓN", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Text("Empieza por lo esencial", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Elige una materia y pon a prueba tus conocimientos con preguntas médicas.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                Button(
                    onClick = onOpenStudy,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Icon(Icons.Outlined.Book, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Comenzar a estudiar", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Outlined.ArrowForward, contentDescription = null)
                }
            }
        }

        SectionLabel("Explora la aplicación")
        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            ActionTile(Icons.Outlined.MenuBook, "Materias", "Anatomía, fisiología, farmacología y más", onOpenSubjects)
            ActionTile(Icons.Outlined.Quiz, "Modo examen", "Simulaciones y evaluación por temas", onOpenStudy)
            ActionTile(Icons.Outlined.TrendingUp, "Mi progreso", "Consulta tus resultados y evolución", onOpenProgress)
        }

        Surface(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(18.dp)) {
            Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Spacer(Modifier.width(10.dp))
                Text(
                    "Versión inicial: estamos preparando el banco de preguntas, la voz y el evaluador inteligente.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private data class SubjectItem(
    val title: String,
    val detail: String,
    val icon: ImageVector,
    val topics: String
)

@Composable
fun SubjectsScreen(onStartSubject: () -> Unit) {
    val subjects = listOf(
        SubjectItem("Anatomía", "Estructuras y órganos del cuerpo", Icons.Outlined.MonitorHeart, "12 temas iniciales"),
        SubjectItem("Fisiología", "Funciones y mecanismos del organismo", Icons.Outlined.FavoriteBorder, "10 temas iniciales"),
        SubjectItem("Farmacología", "Medicamentos y sus mecanismos", Icons.Outlined.Science, "Próximamente"),
        SubjectItem("Patología", "Alteraciones y procesos de enfermedad", Icons.Outlined.Biotech, "Próximamente"),
        SubjectItem("Microbiología", "Bacterias, virus, hongos y parásitos", Icons.Outlined.Psychology, "Próximamente"),
        SubjectItem("Bioquímica", "Procesos químicos de la vida", Icons.Outlined.School, "Próximamente")
    )

    ScreenFrame {
        ScreenTitle("Materias", "Elige el área que quieres dominar.")
        Surface(shape = CardShape, color = MaterialTheme.colorScheme.surfaceVariant) {
            Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.MenuBook, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(25.dp), contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Biblioteca de estudio", fontWeight = FontWeight.SemiBold)
                    Text("Las asignaturas se irán ampliando por bloques revisados.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        subjects.forEach { subject ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable(onClick = onStartSubject),
                shape = CardShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconTile(subject.icon)
                    Spacer(Modifier.width(13.dp))
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(subject.title, fontWeight = FontWeight.Bold)
                        Text(subject.detail, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        Text(subject.topics, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
                    }
                    Icon(Icons.Outlined.ChevronRight, contentDescription = "Abrir materia", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun StudyScreen() {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showPreview by remember { mutableStateOf(false) }
    val answers = listOf("Hígado", "Vesícula biliar", "Páncreas", "Estómago")

    ScreenFrame {
        ScreenTitle("Sesión de estudio", "Anatomía · Sistema digestivo")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(30.dp)) {
                Text("PREGUNTA DE EJEMPLO", modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
            Text("01 / 10", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
        }
        Card(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text("¿Qué órgano produce la bilis?", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Selecciona una respuesta para visualizar el diseño del cuestionario.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                answers.forEachIndexed { index, answer ->
                    val isSelected = selectedAnswer == answer
                    Surface(
                        modifier = Modifier.fillMaxWidth().clickable {
                            selectedAnswer = answer
                            showPreview = false
                        },
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("${'A' + index}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(13.dp))
                            Text(answer, modifier = Modifier.weight(1f), fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            if (isSelected) Icon(Icons.Outlined.CheckCircle, contentDescription = "Seleccionada", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                Button(
                    onClick = { showPreview = true },
                    enabled = selectedAnswer != null,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Ver ejemplo de corrección")
                }
                if (showPreview) {
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                            Text("VISTA PREVIA · NO ES EL EVALUADOR REAL", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            Text("Respuesta correcta: hígado", fontWeight = FontWeight.Bold)
                            Text(
                                if (selectedAnswer == "Hígado") "¡Correcto! El hígado produce la bilis. La vesícula se encarga de almacenarla." else "No exactamente. El hígado produce la bilis; la vesícula biliar la almacena.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text("La evaluación automática y los comentarios del profesor se incorporarán en fases posteriores.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        Card(shape = CardShape, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)) {
                    Icon(Icons.Outlined.Mic, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(11.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Responde con tu voz", fontWeight = FontWeight.SemiBold)
                    Text("El micrófono y la narración se activarán en las fases 3 y 4.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        OutlinedButton(onClick = { selectedAnswer = null; showPreview = false }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
            Text("Reiniciar ejemplo")
        }
    }
}

@Composable
fun ProgressScreen() {
    ScreenFrame {
        ScreenTitle("Mi progreso", "Tu evolución, basada en sesiones reales.")
        Card(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(38.dp))
                Text("Tu historial comienza aquí", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Todavía no hay sesiones registradas. Cuando el sistema de estudio esté conectado, aquí verás tus resultados reales.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatPlaceholder("0", "Sesiones", Icons.Outlined.MenuBook, Modifier.weight(1f))
            StatPlaceholder("0%", "Aciertos", Icons.Outlined.CheckCircle, Modifier.weight(1f))
            StatPlaceholder("0", "Racha", Icons.Outlined.Timer, Modifier.weight(1f))
        }
        SectionLabel("Próximamente")
        listOf("Dominio por asignatura", "Errores más frecuentes", "Tiempo de estudio", "Evolución semanal").forEach {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(19.dp))
                Spacer(Modifier.width(10.dp))
                Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun StatPlaceholder(value: String, label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun SettingsScreen() {
    ScreenFrame {
        ScreenTitle("Configuración", "Prepara tu experiencia de estudio.")
        SettingsRow(Icons.Outlined.Headphones, "Narración de preguntas", "Voces y velocidad: disponibles en Fase 3")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.Mic, "Reconocimiento de voz", "Entrada hablada: disponible en Fase 4")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.AutoAwesome, "Personalidad del profesor", "Nivel de humor y sarcasmo: disponible en Fase 6")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.School, "Nivel académico", "Se configurará con el sistema de materias")
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        SettingsRow(Icons.Outlined.Info, "Acerca de", "Medicine Master · versión 0.1.0")
        Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = CardShape) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Importante", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                Text("Esta aplicación es una herramienta educativa. Su contenido médico deberá revisarse con bibliografía fiable y no sustituye la formación clínica ni el criterio profesional.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

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

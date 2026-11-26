# Medicine Master — Fase 2

Aplicación Android educativa para estudiar medicina. Esta entrega parte de la estructura funcional de la Fase 1 y añade un motor de preguntas de opción múltiple, selección de materias/temas/dificultad, sesiones de estudio, explicaciones y estadísticas locales.

## Incluido en Fase 2

- Seis asignaturas: Anatomía, Fisiología, Farmacología, Patología, Microbiología y Bioquímica.
- 36 preguntas iniciales, seis por asignatura.
- Cuatro opciones por pregunta, respuesta correcta y explicación.
- Dificultades Básica, Intermedia y Avanzada.
- Filtros por asignatura, tema y dificultad.
- Sesiones de 5, 10, 15 o 20 preguntas, sin repetir preguntas dentro de una sesión.
- Corrección inmediata y explicación después de responder.
- Pantalla de resultados al finalizar.
- Estadísticas globales persistentes en el dispositivo mediante SharedPreferences.
- Referencia bibliográfica asociada a cada pregunta y listado en `REFERENCIAS_MEDICAS.md`.
- Proyecto Android, navegación y workflow de GitHub Actions para compilar una APK debug.

## Todavía no incluido

- Narración Text-to-Speech (Fase 3).
- Reconocimiento de voz (Fase 4).
- Evaluación de respuestas habladas abiertas y sinónimos (Fase 5).
- Profesor con comentarios dinámicos y humor contextual (Fase 6).
- IA conversacional (Fase 7).
- Repetición espaciada y plan adaptativo avanzado (Fase 8).

## Tecnologías

- Kotlin y Jetpack Compose.
- Android Gradle Plugin y Compose compiler plugin configurados en los archivos Gradle.
- Java 17.
- `minSdk`: 26.
- `compileSdk`: 35.
- `targetSdk`: 35.
- Estadísticas locales con Android SharedPreferences; no requiere permisos ni conexión para estudiar.

## Cómo actualizar el repositorio desde el teléfono

1. Descarga y descomprime el ZIP de Fase 2.
2. Abre tu repositorio `MedicineMaster` en GitHub.
3. Actualiza los archivos siguiendo la ruta indicada en el ZIP (conserva las carpetas).
4. Verifica especialmente que existe `app/src/main/java/com/medicinemaster/app/data/StudyData.kt`.
5. En **Actions**, ejecuta `Build Medicine Master APK`.
6. Cuando el workflow termine en verde, descarga `MedicineMaster-debug-apk` e instala `app-debug.apk`.

El `versionCode` se incrementó a 2 y `versionName` a 0.2.0 para que Android identifique esta entrega como una actualización de la Fase 1.

## Importante sobre el contenido

Las preguntas son de nivel introductorio y tienen fuentes identificadas, pero deben contrastarse con la bibliografía y el plan de estudios de la facultad antes de usarse como material oficial. La aplicación es educativa y no ofrece diagnóstico ni tratamiento.

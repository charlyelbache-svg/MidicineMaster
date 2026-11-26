# Medicine Master — Fase 1

Aplicación Android educativa para estudiar medicina. Esta entrega contiene el esqueleto real del proyecto, la navegación principal y las primeras pantallas de interfaz en Kotlin + Jetpack Compose.

## Estado de esta entrega

**Incluido**
- Proyecto Android con Kotlin y Jetpack Compose.
- Navegación funcional entre Inicio, Materias, Estudiar, Progreso y Ajustes.
- Diseño inicial oscuro con componentes reutilizables.
- Pantalla de materias con asignaturas iniciales.
- Pantalla de estudio con una pregunta de demostración y una previsualización de corrección.
- Pantallas de progreso y configuración preparadas para las próximas fases.
- Workflow de GitHub Actions para compilar y publicar el APK debug como artefacto.

**Todavía no incluido**
- Narración real de preguntas (Fase 3).
- Reconocimiento de voz (Fase 4).
- Motor definitivo de corrección y sinónimos (Fase 5).
- Profesor con humor contextual e IA (Fases 6 y 7).
- Base de datos y estadísticas persistentes (se incorporarán cuando se implemente el motor de aprendizaje).

La pregunta de bilis que aparece en la pantalla de estudio es una demostración de interfaz, no una evaluación médica automatizada.

## Tecnologías y versiones

- Android Gradle Plugin: 9.5.0
- Gradle: 9.6.0
- Kotlin: 2.4.21 (AGP built-in Kotlin; Compose compiler plugin pins the KGP version)
- Jetpack Compose BOM: 2026.11.01
- Navigation Compose: 2.10.2
- Java: 17
- `minSdk`: 26
- `compileSdk`: 37
- `targetSdk`: 36

## Cómo obtener el APK desde el teléfono usando GitHub

1. Descarga y descomprime `MedicineMaster-Fase1.zip`.
2. En GitHub, crea un repositorio nuevo llamado `MedicineMaster`.
3. Sube **el contenido de esta carpeta** a la raíz del repositorio. Deben quedar visibles `app`, `.github`, `build.gradle.kts` y `settings.gradle.kts` en la raíz.
4. Abre la pestaña **Actions** del repositorio y habilita los workflows si GitHub lo solicita.
5. Entra en **Build Medicine Master APK** y pulsa **Run workflow**. También se ejecuta automáticamente al subir cambios a `main` o `master`.
6. Cuando termine en verde, abre la ejecución y descarga el artefacto `MedicineMaster-debug-apk`.
7. Descomprime el artefacto y encontrarás `app-debug.apk`.

Si la compilación falla, abre el paso rojo de Actions y copia el primer error real (no solo el mensaje final). Ese error será el punto de partida para corregirlo.

## Abrir en Android Studio

Abre la carpeta del proyecto desde Android Studio y deja que sincronice Gradle. El workflow incluido instala Gradle 9.6.0 por su cuenta, por lo que no depende de un `gradlew` generado localmente.

## Próximo paso

Fase 2: definir el modelo de datos y crear el banco inicial de preguntas por asignatura, tema, dificultad, respuesta aceptada, explicación y fuente bibliográfica.

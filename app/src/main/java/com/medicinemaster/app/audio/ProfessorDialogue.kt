package com.medicinemaster.app.audio

import com.medicinemaster.app.data.StudyQuestion

/** Frases del profesor. El carácter se expresa en el texto; Android aporta la voz. */
object ProfessorDialogue {
    private val openings = listOf(
        "Atención, desastre con bata. Vamos a comprobar si hoy aparece alguna neurona.",
        "Escucha, lumbrera de saldo. La medicina no se aprende por ósmosis.",
        "Silencio, criatura académicamente inquieta. Empieza la clase.",
        "A ver, genio del despiste. Usa esa cabeza para algo más que sostener el pelo.",
        "Abre bien los oídos, campeón del casi. Ahí va la pregunta.",
        "Concéntrate, espécimen de laboratorio. No pienso repetirlo veinte veces.",
        "Vamos, cerebro en modo avión. Es hora de aterrizar en los conocimientos.",
        "Presta atención, fenómeno de la confusión. Esto es medicina, no una tómbola.",
        "Deja de mirar la pantalla como si fuera a darte la respuesta por pena.",
        "Una pregunta sencilla y un estudiante bajo sospecha. Qué combinación tan peligrosa.",
        "Respira, piensa y luego toca. En ese orden, por favor.",
        "La pregunta está delante de ti. No muerde; tu ignorancia, quizá.",
        "Vamos a examinar esa materia gris. Espero que haya llegado puntual.",
        "Atiende, futura eminencia. De momento, la eminencia soy yo.",
        "Menos impulsos con el dedo y más actividad cerebral. Escucha.",
        "A ver, cabeza hueca con aspiraciones clínicas: demuestra que estudiaste.",
        "No me hagas perder la mañana. Bueno, no me hagas perder otros diez segundos.",
        "Siéntate mentalmente, recoge tus neuronas y escucha la pregunta.",
        "Aquí viene otra oportunidad de no hacer el ridículo. Aprovecha, por una vez.",
        "Quiero precisión, no una respuesta sacada de una tómbola, zoquete.",
        "Mírame... bueno, escucha. El conocimiento no va a entrar por telepatía.",
        "Vamos a ver si detrás de esa confianza hay algo más que aire acondicionado.",
        "Prepara el cerebro, que lo tienes de adorno desde hace varios minutos.",
        "Concéntrate, idiota académico. Una pregunta no debería derrotarte tan pronto.",
        "A ver si hoy estudiaste o vienes a insultar mi inteligencia con tus respuestas.",
        "Deja de funcionar como un florero y empieza a razonar, pedazo de zoquete.",
        "No necesito un milagro; necesito que leas la pregunta, inútil."
    )

    private val correct = listOf(
        "Correcto. Mira tú: había actividad neuronal después de todo.",
        "Bien. Una respuesta decente. No te emociones, que ha sido una sola.",
        "Acertaste. El profesor reconoce el mérito, aunque le cueste admitirlo.",
        "Correcto. Por fin una neurona ha fichado en horario laboral.",
        "Exacto. Me sorprende, pero no pienso retirar mi superioridad.",
        "Muy bien. Guarda ese momento; no sabemos cuándo se repetirá.",
        "Correcto. Puedes estar orgulloso durante unos cuatro segundos.",
        "Eso es. Un acierto limpio. Casi pareces una persona estudiada.",
        "Bien respondido. No era magia: se llamaba estudiar.",
        "Correcto. Puedes sonreír durante tres segundos. Se acabó el recreo.",
        "Vaya, has acertado. Tendré que revisar mis prejuicios, qué fastidio.",
        "Bien hecho. No ha sido brillante, pero tampoco ha sido una catástrofe.",
        "Exacto. Anota la fecha; hoy tu cerebro ha venido a trabajar.",
        "Correcto. No te acostumbres a que te felicite, pedazo de alumno.",
        "Bien, condenado desastre. Por una vez has hecho algo útil con esa materia gris.",
        "Acertaste. No sé quién te ayudó, pero dile que siga haciendo su trabajo.",
        "Correcto. Me niego a admitir que estoy orgulloso, pero toma el punto."
    )

    private val incorrect = listOf(
        "Incorrecto. Qué elección tan creativa y tan equivocada, desastre con patas.",
        "No. Esa respuesta salió de paseo sin llevarse al cerebro.",
        "Fallo. Tu seguridad es admirable; tu precisión, una tragedia.",
        "Incorrecto. Ni una calculadora distraída habría defendido eso.",
        "No, criatura. Has confundido medicina con ruleta académica.",
        "Eso está mal. Tu dedo ha votado y tu cerebro se ha abstenido.",
        "Incorrecto. Necesitamos menos intuición salvaje y más estudio, campeón.",
        "No. La respuesta correcta estaba ahí, mirándote con decepción.",
        "Fallo monumental. Hasta el esqueleto del aula está negando con la cabeza.",
        "Incorrecto. Hoy has venido a poner a prueba mi paciencia, no tu conocimiento.",
        "No, lumbrera. Has elegido una opción que ni siquiera se esfuerza por tener sentido.",
        "Error. Tu razonamiento necesita una revisión completa y quizá una siesta.",
        "Incorrecto. Qué manera tan eficiente de desperdiciar una buena pregunta.",
        "No. La medicina exige precisión; tú has traído entusiasmo y poco más.",
        "Fallo. Vuelve a escuchar la explicación antes de volver a improvisar.",
        "Por todos los libros de anatomía, ¿de dónde has sacado semejante disparate?",
        "No, zoquete. Esa respuesta no aguanta ni dos segundos bajo un microscopio.",
        "Incorrecto. Has disparado al azar y has conseguido fallar hasta el blanco.",
        "Qué barbaridad. Un estetoscopio tiene más criterio que esa elección.",
        "No. Tu respuesta necesita primeros auxilios y tu método de estudio también.",
        "Falso, pedazo de desastre académico. Vamos a reparar ese agujero de conocimiento.",
        "Incorrecto. Si la confianza diera créditos, te graduabas; por desgracia, esto es medicina.",
        "No me hagas repetirlo, cabeza de chorlito. La evidencia está por encima de tus corazonadas.",
        "Eso no. Ni con bata, ni con suerte, ni mirando al techo se convierte en correcto.",
        "Fallaste. Tu cerebro acaba de presentar una renuncia y no pienso aceptarla.",
        "Incorrecto. Has elegido la opción equivocada con una convicción verdaderamente conmovedora.",
        "No, criatura. Antes de tocar una respuesta, prueba a pensar. Es gratis.",
        "Vaya desastre. Hasta mi paciencia, que es legendariamente escasa, se ha agotado.",
        "No, imbécil. La respuesta no se elige por simpatía, se elige porque es correcta.",
        "Qué respuesta tan absurda. Has conseguido que un profesor tenga que respirar hondo.",
        "Incorrecto, inútil. Vuelve al concepto básico antes de hacer más daño a la ciencia.",
        "No. Esa ocurrencia merece una autopsia académica para descubrir qué salió mal.",
        "Mal. Tu razonamiento está más perdido que una bacteria en una clase de anatomía.",
        "Fallo. Deja de improvisar, idiota, y empieza a relacionar los conceptos."
    )

    private val explanationOpeners = listOf(
        "Escucha y grábate esto, que la memoria no se entrena sola.",
        "Te lo explicaré despacio. No porque sea difícil, sino por prudencia.",
        "Aquí tienes la explicación. Intenta que esta vez se quede dentro.",
        "Toma nota mental, criatura. Esta es la parte que sí importa.",
        "Atiende. Mi explicación vale más que ese disparo al azar que acabas de hacer.",
        "Vamos a corregir esa laguna antes de que se convierta en océano.",
        "Aprende el concepto, no memorices a ciegas como un loro con estetoscopio.",
        "Fíjate bien. La diferencia es sencilla cuando uno se toma la molestia de pensar.",
        "Ahora viene la parte educativa. Haz el esfuerzo sobrehumano de escuchar.",
        "Grábate el mecanismo, no solo la letra correcta. No seas vago.",
        "Vamos por partes, que el conocimiento entra mejor cuando se deja de improvisar.",
        "Esta explicación te puede salvar de repetir el mismo disparate en la próxima pregunta.",
        "Escucha con atención. Hasta yo puedo simplificarlo para que lo entiendas.",
        "Memoriza la relación entre concepto y mecanismo. Eso sí sirve en un examen.",
        "Vamos, que no pienso tatuarte la explicación en la frente.",
        "Ahora repite mentalmente el mecanismo y procura no volver a meter la pata.",
        "La medicina se entiende con relaciones causa y efecto, no con apuestas a ciegas."
    )

    fun question(question: StudyQuestion): String {
        val options = question.options.mapIndexed { index, option ->
            "Opción ${'A' + index}: $option"
        }.joinToString(separator = ". ")
        return "${openings.random()} ${question.prompt}. $options. Piensa antes de responder."
    }

    fun feedback(question: StudyQuestion, isCorrect: Boolean): String {
        val lead = if (isCorrect) correct.random() else incorrect.random()
        return "$lead La respuesta correcta es: ${question.options[question.correctIndex]}. ${question.explanation}"
    }

    fun explanation(question: StudyQuestion, isCorrect: Boolean): String {
        val opener = explanationOpeners.random()
        val verdict = if (isCorrect) "Tu respuesta fue correcta." else "Tu respuesta no fue correcta."
        return "$opener $verdict La respuesta correcta es: ${question.options[question.correctIndex]}. ${question.explanation}"
    }

    fun settingsTest(): String = openings.random() + " " + correct.random()
}

package com.medicinemaster.app.data

import android.content.Context

/** Educational question model. The source is kept with every item so content can be reviewed. */
data class StudyQuestion(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val topic: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficulty: String,
    val explanation: String,
    val sourceLabel: String,
    val sourceUrl: String
)

data class StudySubject(
    val id: String,
    val name: String,
    val description: String
)

data class ProgressSnapshot(
    val sessions: Int,
    val answered: Int,
    val correct: Int
) {
    val accuracyPercent: Int
        get() = if (answered == 0) 0 else ((correct * 100f) / answered).toInt()
}

object StudySubjects {
    const val ANATOMY = "anatomy"
    const val PHYSIOLOGY = "physiology"
    const val PHARMACOLOGY = "pharmacology"
    const val PATHOLOGY = "pathology"
    const val MICROBIOLOGY = "microbiology"
    const val BIOCHEMISTRY = "biochemistry"

    val all = listOf(
        StudySubject(ANATOMY, "Anatomía", "Estructuras y órganos del cuerpo"),
        StudySubject(PHYSIOLOGY, "Fisiología", "Funciones y mecanismos del organismo"),
        StudySubject(PHARMACOLOGY, "Farmacología", "Principios generales de los medicamentos"),
        StudySubject(PATHOLOGY, "Patología", "Lesión celular, inflamación y enfermedad"),
        StudySubject(MICROBIOLOGY, "Microbiología", "Bacterias, virus, hongos y parásitos"),
        StudySubject(BIOCHEMISTRY, "Bioquímica", "Moléculas y procesos químicos de la vida")
    )
}

object QuestionBank {
    private const val OPENSTAX_AP = "OpenStax · Anatomy & Physiology 2e"
    private const val OPENSTAX_MICRO = "OpenStax · Microbiology"
    private const val OPENSTAX_BIO = "OpenStax · Biology 2e"
    private const val NCBI_PHARM = "NCBI Bookshelf · Pharmacodynamics / Pharmacokinetics"

    private const val DIGESTION = "https://openstax.org/books/anatomy-and-physiology-2e/pages/23-6-accessory-organs-in-digestion-the-liver-pancreas-and-gallbladder"
    private const val DIGESTION_PROCESSES = "https://openstax.org/books/anatomy-and-physiology-2e/pages/23-2-digestive-system-processes-and-regulation"
    private const val KIDNEY = "https://openstax.org/books/anatomy-and-physiology-2e/pages/25-introduction"
    private const val RESPIRATORY = "https://openstax.org/books/anatomy-and-physiology-2e/pages/22-introduction"
    private const val HEART = "https://openstax.org/books/anatomy-and-physiology-2e/pages/19-introduction"
    private const val HOMEOSTASIS = "https://openstax.org/books/anatomy-and-physiology-2e/pages/1-5-homeostasis"
    private const val ENDOCRINE = "https://openstax.org/books/anatomy-and-physiology-2e/pages/17-introduction"
    private const val BLOOD = "https://openstax.org/books/anatomy-and-physiology-2e/pages/18-introduction"
    private const val TISSUE_INJURY = "https://openstax.org/books/anatomy-and-physiology-2e/pages/4-6-tissue-injury-and-aging"
    private const val PHARMACOLOGY = "https://www.ncbi.nlm.nih.gov/books/NBK507791/"
    private const val ANTIBIOTICS = "https://www.cdc.gov/antibiotic-use/about/"
    private const val MICRO_TYPES = "https://openstax.org/books/microbiology/pages/1-3-types-of-microorganisms"
    private const val PROKARYOTES = "https://openstax.org/books/biology-2e/pages/4-2-prokaryotic-cells"
    private const val ENZYMES = "https://openstax.org/books/biology-2e/pages/6-5-enzymes"
    private const val ATP = "https://openstax.org/books/biology-2e/pages/6-4-atp-adenosine-triphosphate"
    private const val GLYCOLYSIS = "https://openstax.org/books/biology-2e/pages/7-2-glycolysis"
    private const val METABOLISM = "https://openstax.org/books/biology-2e/pages/6-1-energy-and-metabolism"
    private const val PROTEINS = "https://openstax.org/books/biology-2e/pages/3-4-proteins"
    private const val NUCLEIC_ACIDS = "https://openstax.org/books/biology-2e/pages/3-5-nucleic-acids"

    val questions: List<StudyQuestion> = listOf(
        // ANATOMÍA
        q("ana-001", StudySubjects.ANATOMY, "Anatomía", "Sistema digestivo", "¿Qué órgano produce la bilis?", listOf("Hígado", "Vesícula biliar", "Páncreas", "Estómago"), 0, "Básica", "La bilis se produce en el hígado. La vesícula biliar la almacena, la concentra y la libera al intestino delgado.", OPENSTAX_AP, DIGESTION),
        q("ana-002", StudySubjects.ANATOMY, "Anatomía", "Sistema digestivo", "¿Cuál es la función principal de la vesícula biliar?", listOf("Producir insulina", "Almacenar y concentrar bilis", "Producir enzimas pancreáticas", "Absorber la mayoría de los nutrientes"), 1, "Básica", "La vesícula almacena y concentra la bilis producida por el hígado y la libera cuando se necesita en el intestino delgado.", OPENSTAX_AP, DIGESTION),
        q("ana-003", StudySubjects.ANATOMY, "Anatomía", "Sistema urinario", "¿Cuál es la unidad estructural y funcional del riñón?", listOf("Alvéolo", "Nefrona", "Neurona", "Hepatocito"), 1, "Básica", "La nefrona es la unidad funcional renal: filtra la sangre y modifica el filtrado mediante procesos de reabsorción y secreción.", OPENSTAX_AP, KIDNEY),
        q("ana-004", StudySubjects.ANATOMY, "Anatomía", "Sistema respiratorio", "¿En qué estructura pulmonar ocurre principalmente el intercambio gaseoso?", listOf("Tráquea", "Bronquio principal", "Alvéolos", "Pleura"), 2, "Básica", "Los alvéolos ofrecen una superficie extensa y paredes delgadas junto a capilares, lo que facilita el intercambio de oxígeno y dióxido de carbono.", OPENSTAX_AP, RESPIRATORY),
        q("ana-005", StudySubjects.ANATOMY, "Anatomía", "Sistema cardiovascular", "¿Cuántas cavidades tiene el corazón humano?", listOf("Dos", "Tres", "Cuatro", "Cinco"), 2, "Básica", "El corazón tiene dos aurículas y dos ventrículos: cuatro cavidades en total.", OPENSTAX_AP, HEART),
        q("ana-006", StudySubjects.ANATOMY, "Anatomía", "Sistema digestivo", "¿En qué órgano ocurre la mayor parte de la absorción de nutrientes?", listOf("Esófago", "Estómago", "Intestino delgado", "Intestino grueso"), 2, "Intermedia", "El intestino delgado es el principal sitio de digestión química y absorción de la mayoría de los nutrientes.", OPENSTAX_AP, DIGESTION_PROCESSES),

        // FISIOLOGÍA
        q("fis-001", StudySubjects.PHYSIOLOGY, "Fisiología", "Sistema cardiovascular", "¿Qué estructura inicia normalmente el impulso eléctrico del corazón?", listOf("Nodo sinoauricular", "Nodo auriculoventricular", "Haz de His", "Fibras de Purkinje"), 0, "Intermedia", "El nodo sinoauricular actúa normalmente como marcapasos fisiológico porque genera espontáneamente impulsos que inician cada ciclo cardíaco.", OPENSTAX_AP, HEART),
        q("fis-002", StudySubjects.PHYSIOLOGY, "Fisiología", "Sistema endocrino", "¿Qué efecto general tiene la insulina sobre la glucosa sanguínea?", listOf("La aumenta", "La disminuye", "No tiene efecto", "La convierte directamente en oxígeno"), 1, "Básica", "La insulina favorece la captación y almacenamiento de nutrientes en varios tejidos y, en términos generales, disminuye la glucemia.", OPENSTAX_AP, ENDOCRINE),
        q("fis-003", StudySubjects.PHYSIOLOGY, "Fisiología", "Homeostasis", "¿Qué caracteriza a un mecanismo de retroalimentación negativa?", listOf("Amplifica el cambio inicial", "Contrarresta la desviación y ayuda a recuperar el equilibrio", "Detiene toda actividad celular", "Solo aparece durante el sueño"), 1, "Intermedia", "La retroalimentación negativa produce una respuesta que se opone al cambio inicial y contribuye a mantener variables internas dentro de rangos compatibles con la vida.", OPENSTAX_AP, HOMEOSTASIS),
        q("fis-004", StudySubjects.PHYSIOLOGY, "Fisiología", "Sistema respiratorio", "¿Qué mecanismo permite que el oxígeno pase desde el alvéolo hacia la sangre?", listOf("Transporte activo", "Difusión siguiendo un gradiente de presión parcial", "Contracción muscular del alvéolo", "Filtración glomerular"), 1, "Intermedia", "Los gases atraviesan la membrana respiratoria por difusión, siguiendo sus gradientes de presión parcial.", OPENSTAX_AP, RESPIRATORY),
        q("fis-005", StudySubjects.PHYSIOLOGY, "Fisiología", "Sangre", "¿Qué proteína de los eritrocitos transporta la mayor parte del oxígeno?", listOf("Albúmina", "Fibrinógeno", "Hemoglobina", "Insulina"), 2, "Básica", "La hemoglobina, contenida en los eritrocitos, se une reversiblemente al oxígeno y permite transportar la mayor parte del oxígeno sanguíneo.", OPENSTAX_AP, BLOOD),
        q("fis-006", StudySubjects.PHYSIOLOGY, "Fisiología", "Sistema urinario", "¿Cuál es un efecto importante de la hormona antidiurética (ADH)?", listOf("Disminuir la reabsorción de agua", "Aumentar la reabsorción de agua en los riñones", "Estimular la producción de bilis", "Destruir eritrocitos"), 1, "Avanzada", "La ADH aumenta la permeabilidad al agua de segmentos del túbulo distal y de los conductos colectores, favoreciendo la conservación de agua.", OPENSTAX_AP, KIDNEY),

        // FARMACOLOGÍA
        q("far-001", StudySubjects.PHARMACOLOGY, "Farmacología", "Principios generales", "¿Qué estudia principalmente la farmacodinámica?", listOf("Lo que el organismo hace al fármaco", "Los efectos y mecanismos de acción del fármaco sobre el organismo", "La anatomía del sistema digestivo", "La clasificación de microorganismos"), 1, "Básica", "La farmacodinámica estudia los efectos de los medicamentos y sus mecanismos de acción en el organismo. La farmacocinética estudia cómo el organismo procesa el fármaco.", NCBI_PHARM, PHARMACOLOGY),
        q("far-002", StudySubjects.PHARMACOLOGY, "Farmacología", "Principios generales", "¿Qué describe la farmacocinética?", listOf("Cómo el organismo absorbe, distribuye, metaboliza y elimina un fármaco", "Solo el efecto de un fármaco en un receptor", "La respuesta inmunitaria frente a bacterias", "La producción de hormonas pancreáticas"), 0, "Intermedia", "La farmacocinética estudia el recorrido del fármaco por el organismo, habitualmente descrito mediante absorción, distribución, metabolismo y excreción.", NCBI_PHARM, PHARMACOLOGY),
        q("far-003", StudySubjects.PHARMACOLOGY, "Farmacología", "Receptores", "¿Qué hace un agonista cuando se une a su receptor?", listOf("Lo activa y produce una respuesta", "Lo bloquea sin activarlo", "Destruye el receptor", "Impide siempre la absorción intestinal"), 0, "Intermedia", "Un agonista se une a un receptor y lo activa para producir una respuesta. La magnitud de esa respuesta depende de la interacción y del sistema biológico.", NCBI_PHARM, PHARMACOLOGY),
        q("far-004", StudySubjects.PHARMACOLOGY, "Farmacología", "Receptores", "¿Qué caracteriza a un antagonista receptor?", listOf("Activa el receptor al máximo", "Se une e impide o reduce la acción de un agonista", "Aumenta siempre la concentración del fármaco", "Transforma el fármaco en una hormona"), 1, "Intermedia", "Un antagonista se une al receptor sin activarlo y bloquea o reduce la acción de otros ligandos, según el tipo de antagonismo.", NCBI_PHARM, PHARMACOLOGY),
        q("far-005", StudySubjects.PHARMACOLOGY, "Farmacología", "Antimicrobianos", "¿Los antibióticos sirven para tratar infecciones causadas por virus?", listOf("Sí, todos los virus", "Sí, si hay fiebre", "No; actúan frente a ciertas infecciones bacterianas, no frente a virus", "Solo durante las primeras 24 horas"), 2, "Básica", "Los antibióticos tratan ciertas infecciones bacterianas. No son eficaces contra virus como los que causan resfriado o gripe.", "CDC · Antibiotic Use", ANTIBIOTICS),
        q("far-006", StudySubjects.PHARMACOLOGY, "Farmacología", "Seguridad de medicamentos", "¿Qué es un efecto adverso de un medicamento?", listOf("Un efecto no deseado y potencialmente perjudicial asociado al uso del medicamento", "La respuesta terapéutica buscada en todos los pacientes", "La absorción del fármaco", "La unión normal del fármaco a proteínas plasmáticas"), 0, "Avanzada", "Un efecto adverso es una respuesta no deseada y potencialmente dañina que puede presentarse con el uso de un medicamento.", NCBI_PHARM, PHARMACOLOGY),

        // PATOLOGÍA
        q("pat-001", StudySubjects.PATHOLOGY, "Patología", "Inflamación", "¿Cuáles son cuatro signos cardinales clásicos de la inflamación?", listOf("Fiebre, tos, vómitos y diarrea", "Rubor, calor, tumor y dolor", "Cianosis, ictericia, prurito y síncope", "Hipotensión, taquicardia, disnea y coma"), 1, "Básica", "Los cuatro signos clásicos son enrojecimiento, calor, hinchazón y dolor. A veces se añade la pérdida de función como quinto signo.", OPENSTAX_AP, TISSUE_INJURY),
        q("pat-002", StudySubjects.PATHOLOGY, "Patología", "Lesión celular", "¿Qué significa necrosis?", listOf("Muerte celular accidental asociada a lesión", "División celular normal", "Maduración de un eritrocito", "Formación de anticuerpos"), 0, "Intermedia", "La necrosis es una forma de muerte celular relacionada con daño; suele acompañarse de inflamación en el tejido circundante.", OPENSTAX_AP, TISSUE_INJURY),
        q("pat-003", StudySubjects.PATHOLOGY, "Patología", "Lesión celular", "¿Qué caracteriza a la apoptosis?", listOf("Muerte celular programada y regulada", "Multiplicación bacteriana", "Acumulación de bilis", "Contracción del músculo cardíaco"), 0, "Intermedia", "La apoptosis es un proceso regulado de muerte celular que participa en el desarrollo y en el mantenimiento de los tejidos.", OPENSTAX_AP, TISSUE_INJURY),
        q("pat-004", StudySubjects.PATHOLOGY, "Patología", "Terminología", "En terminología médica, el sufijo «-itis» suele indicar:", listOf("Tumor benigno", "Inflamación", "Disminución de glucosa", "Muerte de un órgano"), 1, "Básica", "El sufijo -itis se utiliza para señalar inflamación, por ejemplo, gastritis o peritonitis.", OPENSTAX_AP, TISSUE_INJURY),
        q("pat-005", StudySubjects.PATHOLOGY, "Patología", "Inflamación", "¿Qué cambio vascular contribuye al enrojecimiento y al calor local durante la inflamación?", listOf("Vasoconstricción intensa", "Vasodilatación local", "Cierre de todos los capilares", "Disminución completa del flujo sanguíneo"), 1, "Avanzada", "La vasodilatación local aumenta el flujo sanguíneo en la zona inflamada y contribuye al enrojecimiento y al calor.", OPENSTAX_AP, TISSUE_INJURY),
        q("pat-006", StudySubjects.PATHOLOGY, "Patología", "Lesión celular", "¿Qué diferencia general existe entre apoptosis y necrosis?", listOf("Son exactamente el mismo proceso", "La apoptosis es regulada; la necrosis suele estar asociada a daño celular", "La necrosis solo ocurre en bacterias", "La apoptosis siempre produce pus"), 1, "Avanzada", "La apoptosis es una forma regulada de muerte celular. La necrosis se asocia a lesión celular y con frecuencia desencadena una respuesta inflamatoria.", OPENSTAX_AP, TISSUE_INJURY),

        // MICROBIOLOGÍA
        q("mic-001", StudySubjects.MICROBIOLOGY, "Microbiología", "Biología microbiana", "¿Qué característica comparten las bacterias y las arqueas?", listOf("Son eucariotas", "Son procariotas y carecen de núcleo rodeado por membrana", "Son virus acelulares", "Todas son pluricelulares"), 1, "Básica", "Bacterias y arqueas son procariotas: sus células no tienen un núcleo delimitado por una envoltura nuclear.", OPENSTAX_MICRO, PROKARYOTES),
        q("mic-002", StudySubjects.MICROBIOLOGY, "Microbiología", "Virus", "¿Cómo se clasifican los virus respecto a la organización celular?", listOf("Procariotas", "Eucariotas", "Acelulares", "Hongos unicelulares"), 2, "Básica", "Los virus son entidades acelulares: no están formados por células y dependen de una célula huésped para multiplicarse.", OPENSTAX_MICRO, MICRO_TYPES),
        q("mic-003", StudySubjects.MICROBIOLOGY, "Microbiología", "Virus", "¿Por qué los virus necesitan una célula huésped para multiplicarse?", listOf("Porque no tienen material genético", "Porque dependen de la maquinaria celular del huésped para producir nuevos virus", "Porque son bacterias pequeñas", "Porque realizan fotosíntesis"), 1, "Intermedia", "Los virus no cuentan por sí solos con toda la maquinaria necesaria para reproducirse; utilizan componentes de la célula huésped.", OPENSTAX_MICRO, MICRO_TYPES),
        q("mic-004", StudySubjects.MICROBIOLOGY, "Microbiología", "Hongos", "¿Los hongos tienen células procariotas o eucariotas?", listOf("Procariotas", "Eucariotas", "No tienen células", "Depende exclusivamente de su tamaño"), 1, "Básica", "Los hongos pertenecen al dominio Eukarya y sus células poseen núcleo delimitado por membrana.", OPENSTAX_MICRO, MICRO_TYPES),
        q("mic-005", StudySubjects.MICROBIOLOGY, "Microbiología", "Microorganismos", "¿Cuál de estas opciones describe mejor a un protozoo típico?", listOf("Organismo unicelular eucariota", "Virus con cápside", "Bacteria sin membrana plasmática", "Animal vertebrado microscópico"), 0, "Intermedia", "Los protozoos son microorganismos eucariotas unicelulares; muchos presentan estructuras especializadas y pueden ser móviles.", OPENSTAX_MICRO, MICRO_TYPES),
        q("mic-006", StudySubjects.MICROBIOLOGY, "Microbiología", "Parasitología", "¿Por qué los helmintos se estudian en microbiología aunque sean multicelulares?", listOf("Porque no poseen ADN", "Porque sus huevos y larvas pueden ser microscópicos", "Porque son bacterias filamentosas", "Porque todos viven dentro de las células"), 1, "Avanzada", "Los helmintos son gusanos parásitos multicelulares. Sus huevos y larvas suelen ser microscópicos, por lo que forman parte del estudio microbiológico.", OPENSTAX_MICRO, MICRO_TYPES),

        // BIOQUÍMICA
        q("bio-001", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Bioenergética", "¿Qué molécula se conoce como principal moneda energética inmediata de la célula?", listOf("ADN", "ATP", "Colágeno", "Bilirrubina"), 1, "Básica", "El ATP almacena y transfiere energía utilizable para numerosos procesos celulares.", OPENSTAX_BIO, ATP),
        q("bio-002", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Enzimas", "¿Cómo aceleran las enzimas las reacciones químicas?", listOf("Aumentando la energía de activación", "Disminuyendo la energía de activación", "Cambiando siempre el equilibrio final", "Consumiendo todo el sustrato antes de reaccionar"), 1, "Intermedia", "Las enzimas actúan como catalizadores biológicos y reducen la energía de activación necesaria para que ocurra una reacción.", OPENSTAX_BIO, ENZYMES),
        q("bio-003", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Metabolismo", "¿Cuál es el producto final principal de la glucólisis por cada molécula de glucosa?", listOf("Dos moléculas de piruvato", "Dos moléculas de urea", "Una molécula de ADN", "Ácidos biliares"), 0, "Intermedia", "La glucólisis transforma una molécula de glucosa en dos moléculas de piruvato y produce una ganancia neta de dos ATP y dos NADH.", OPENSTAX_BIO, GLYCOLYSIS),
        q("bio-004", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Metabolismo", "¿Qué describe una ruta anabólica?", listOf("La degradación de moléculas complejas", "La síntesis de moléculas complejas a partir de otras más simples", "La destrucción de todos los ATP", "La difusión de gases"), 1, "Avanzada", "El anabolismo construye moléculas complejas desde componentes más simples y requiere aporte de energía.", OPENSTAX_BIO, METABOLISM),
        q("bio-005", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Proteínas", "¿Cuáles son las unidades básicas que forman las proteínas?", listOf("Ácidos grasos", "Aminoácidos", "Monosacáridos", "Nucleótidos"), 1, "Básica", "Las proteínas son polímeros cuyos monómeros son aminoácidos unidos mediante enlaces peptídicos.", OPENSTAX_BIO, PROTEINS),
        q("bio-006", StudySubjects.BIOCHEMISTRY, "Bioquímica", "Ácidos nucleicos", "¿Cuál es la unidad estructural básica de los ácidos nucleicos?", listOf("Aminoácido", "Nucleótido", "Triglicérido", "Glucógeno"), 1, "Intermedia", "El ADN y el ARN son polímeros construidos a partir de nucleótidos.", OPENSTAX_BIO, NUCLEIC_ACIDS)
    ) + ExpandedQuestionBank.questions

    fun forFilters(subjectId: String, topic: String, difficulty: String): List<StudyQuestion> {
        return questions.filter { question ->
            (subjectId == "all" || question.subjectId == subjectId) &&
                (topic == "Todos los temas" || question.topic == topic) &&
                (difficulty == "Todas" || question.difficulty == difficulty)
        }
    }

    fun topicsFor(subjectId: String): List<String> = questions
        .filter { subjectId == "all" || it.subjectId == subjectId }
        .map { it.topic }
        .distinct()
        .sorted()

    fun subjectCount(subjectId: String): Int = questions.count { it.subjectId == subjectId }

    private fun q(
        id: String,
        subjectId: String,
        subjectName: String,
        topic: String,
        prompt: String,
        options: List<String>,
        correctIndex: Int,
        difficulty: String,
        explanation: String,
        sourceLabel: String,
        sourceUrl: String
    ) = StudyQuestion(
        id, subjectId, subjectName, topic, prompt, options, correctIndex,
        difficulty, explanation, sourceLabel, sourceUrl
    )
}

/** Small local store for real session totals. It uses Android SharedPreferences, so no network is needed. */
object ProgressStore {
    private const val PREFS_NAME = "medicine_master_study_progress"
    private const val KEY_SESSIONS = "sessions"
    private const val KEY_ANSWERED = "answered"
    private const val KEY_CORRECT = "correct"

    fun recordSession(context: Context, answered: Int, correct: Int) {
        if (answered <= 0) return
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_SESSIONS, prefs.getInt(KEY_SESSIONS, 0) + 1)
            .putInt(KEY_ANSWERED, prefs.getInt(KEY_ANSWERED, 0) + answered)
            .putInt(KEY_CORRECT, prefs.getInt(KEY_CORRECT, 0) + correct.coerceIn(0, answered))
            .apply()
    }

    fun load(context: Context): ProgressSnapshot {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return ProgressSnapshot(
            sessions = prefs.getInt(KEY_SESSIONS, 0),
            answered = prefs.getInt(KEY_ANSWERED, 0),
            correct = prefs.getInt(KEY_CORRECT, 0)
        )
    }

    fun reset(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}

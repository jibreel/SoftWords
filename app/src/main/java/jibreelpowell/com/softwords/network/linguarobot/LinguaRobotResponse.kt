package jibreelpowell.com.softwords.network.linguarobot

data class LinguaRobotResponse(
    val entries: List<Entry>
) {
    data class Entry(
        val entry: String,
        val lexemes: List<Lexeme>
    ) {
        data class Lexeme(
            val lemma: String,
            val partOfSpeech: String,
            val senses: List<Sense>,
            val forms: List<Form>
        ) {
            data class Sense(
                val definition: String,
            )
            data class Form(
                val form: String,
                val grammar: List<Grammar>,
                val labels: List<String>?
            ) {
                data class Grammar(
                    val person: List<String>?,
                    val number: List<String>?,
                    val tense: List<String>?,
                    val mood: List<String>?,
                    val case: List<String>?
                )
            }
        }
    }
}

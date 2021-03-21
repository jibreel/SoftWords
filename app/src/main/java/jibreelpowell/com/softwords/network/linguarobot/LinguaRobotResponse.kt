package jibreelpowell.com.softwords.network.linguarobot

data class LinguaRobotResponse(
    val entries: List<Entry>
) {

    data class Entry(
        val entry: String,
        val lexemes: List<Lexeme>
    ) {

        data class Lexeme(
            val senses: List<Sense>
        ) {

            data class Sense(
                val lemma: String,
                val definition: String
            )
        }
    }
}

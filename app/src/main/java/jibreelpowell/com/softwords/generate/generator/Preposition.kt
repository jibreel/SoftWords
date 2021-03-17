package jibreelpowell.com.softwords.generate.generator

class Preposition(name: String): Word() {
    val name: String = name.toLowerCase()

    override fun toString() = name

    companion object {
        private val prepositions = listOf(
                Preposition("in"),
                Preposition("on"),
                Preposition("from"),
                Preposition("below"),
                Preposition("under"),
                Preposition("inside of"),
                Preposition("between"),
                Preposition("like"),
                Preposition("because of"),
                Preposition("except")
        )

        fun random() = prepositions.random()
    }
}
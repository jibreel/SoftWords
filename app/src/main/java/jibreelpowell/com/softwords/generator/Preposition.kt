package jibreelpowell.com.softwords.generator

class Preposition(name: String): Word() {
    val name: String = name.toLowerCase()

    override fun asString() = name

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
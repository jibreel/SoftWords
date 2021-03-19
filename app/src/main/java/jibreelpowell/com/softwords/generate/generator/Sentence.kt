package jibreelpowell.com.softwords.generate.generator

class Sentence(wordsList: List<Word>) {
    constructor(vararg words: Word) : this(ArrayList(words.asList()))

    private val words: List<Word> = ArrayList(wordsList)

    operator fun get(i: Int): Word {
        return words[i]
    }

    fun asList() = ArrayList(words)

    override fun toString(): String {
        val string =  words.fold(
               ""
            ) { acc, word ->
                if (word.toString() != "") {
                    "$acc$word "
                } else {
                    acc
                }
            }
        return "${string.trim()}."
    }
}
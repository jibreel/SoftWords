package jibreelpowell.com.softwords.generate.generator

class Article: Word() {
    private val THE = "the"
    private val A = "a"
    private val AN = "an"
    private val EMPTY = ""

    var type: ArticleType = ArticleType.DEFINITE
    var number: GrammaticalNumber = GrammaticalNumber.SINGULAR
    private var beforeVowel = false

    fun precedes(word: Word) {
        beforeVowel = word.asString()[0].isVowel()
    }

    override fun asString(): String =
            when (type) {
                ArticleType.INDEFINITE ->
                    when (number) {
                        GrammaticalNumber.SINGULAR ->
                            if (beforeVowel) {
                                AN
                            } else {
                                A
                            }
                        GrammaticalNumber.PLURAL -> EMPTY
                    }
                ArticleType.DEFINITE -> THE
            }
}